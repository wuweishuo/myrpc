package com.wws.myrpc.registry.zookeeper;

import com.wws.myrpc.registry.NotifyListener;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.registry.ServerInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ZookeeperRegistryService
 * zookeeper注册中心
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class ZookeeperRegistryService implements RegistryService {

    // 根路径
    private static final String BASE_PATH = "myrpc";

    //服务路径
    private final String SERVER_PATH = "/server";

    private final String url;

    private final CuratorFramework client;

    private Map<String, List<ServerInfo>> instanceMap;

    private final PathChildrenCache cache;

    // 订阅的监听器
    private final List<NotifyListener> notifyListeners;


    public ZookeeperRegistryService(String url) throws Exception {
        this.url = url;
        this.client = CuratorFrameworkFactory.builder()
                .connectString(url)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace(BASE_PATH)
                .build();
        client.start();
        this.cache = new PathChildrenCache(client, SERVER_PATH, true);
        this.cache.getListenable().addListener(new MyRpcPathChildrenCacheListener());
        this.cache.start();
        this.notifyListeners = new ArrayList<>();
    }

    @Override
    public void destroy() throws Exception {
        this.cache.close();
        this.client.close();
    }

    @Override
    public void register(ServerInfo serverInfo) throws Exception {
        String path = toPath(serverInfo);
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(SERVER_PATH + "/" + path);
    }

    @Override
    public void unregister(ServerInfo serverInfo) throws Exception {
        String path = toPath(serverInfo);
        client.delete().forPath(SERVER_PATH + "/" + path);
    }

    @Override
    public void subscribe(String name, NotifyListener notifyListener) {
        List<ServerInfo> serverInfos;
        if (instanceMap != null) {
            serverInfos = instanceMap.get(name);
        } else {
            serverInfos = Collections.emptyList();
        }
        notifyListener.notify(serverInfos);
        notifyListeners.add(notifyListener);
    }

    @Override
    public void unsubscribe(String name, NotifyListener notifyListener) {
        notifyListeners.remove(notifyListener);
    }

    @Override
    public String getUrl() {
        return url;
    }

    /**
     * 生成path，name:ip:port:
     *
     * @param serverInfo
     * @return
     */
    private String toPath(ServerInfo serverInfo) {
        URL url = new URL("myrpc", serverInfo.getIp(), serverInfo.getPort(), serverInfo.getName());
        url.addParameters(serverInfo.getMetaData());
        try {
            return URLEncoder.encode(url.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private ServerInfo toServiceInfo(String path) {
        path = path.substring(path.lastIndexOf("/") + 1);
        try {
            path = URLDecoder.decode(path, "UTF-8");
            URL url = new URL(path);
            ServerInfo serverInfo = new ServerInfo(url.getPath(), url.getIp(), url.getPort());
            for (String parameterKey : url.parameterKeys()) {
                serverInfo.setProperty(parameterKey, url.getParameter(parameterKey));
            }
            return serverInfo;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void refreshMap() {
        List<ChildData> currentDatas = cache.getCurrentData();
        instanceMap = currentDatas.stream()
                .map(ChildData::getPath)
                .map(this::toServiceInfo)
                .collect(Collectors.groupingBy(ServerInfo::getName));
        for (NotifyListener notifyListener : notifyListeners) {
            String name = notifyListener.name();
            notifyListener.notify(instanceMap.getOrDefault(name, Collections.emptyList()));
        }
    }

    private class MyRpcPathChildrenCacheListener implements PathChildrenCacheListener {

        @Override
        public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
            refreshMap();
        }
    }

    private static class URL{

        private Map<String, String> map;

        private String scheme;

        private String ip;

        private int port;

        private String path;

        URL(String scheme, String ip, int port, String path) {
            this.scheme = scheme;
            this.ip = ip;
            this.port = port;
            this.path = path;
            this.map = new HashMap<>();
        }

        URL(String url){
            String[] strs= url.split("\\?");
            load(strs[0]);
            if(strs.length > 1){
                loadParameter(strs[1]);
            }
        }

        private void load(String url){
            String[] strs = url.split("://");
            scheme = strs[0];
            strs = strs[1].split("/");
            path = strs[1];
            strs = strs[0].split(":");
            ip = strs[0];
            port = Integer.parseInt(strs[1]);
        }

        private void loadParameter(String parameters){
            String[] strs = parameters.split("&");
            map = new HashMap<>(strs.length);
            for (String str : strs) {
                String[] arr = str.split("=");
                map.put(arr[0].trim(), arr[1].trim());
            }
        }

        public String getScheme() {
            return scheme;
        }

        public String getIp() {
            return ip;
        }

        public int getPort() {
            return port;
        }

        public String getPath() {
            return path;
        }

        public void addParameters(Map<String, String> map){
            this.map.putAll(map);
        }

        public void addParameter(String key, String value){
            map.put(key, value);
        }

        public String getParameter(String key){
            return map.get(key);
        }

        public Set<String> parameterKeys(){
            return map.keySet();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(scheme).append("://").append(ip).append(":").append(port).append("/").append(path);
            if (map != null && map.size() > 0) {
                sb.append("?");
                for (String key : map.keySet()) {
                    sb.append(key).append("=").append(map.get(key)).append("&");
                }
                sb.delete(sb.length() - 1, sb.length());
            }
            return sb.toString();
        }
    }
}
