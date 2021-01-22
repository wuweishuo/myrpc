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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    private CuratorFramework client;

    // 根路径
    private final String BASE_PATH = "myrpc";

    //服务路径
    private final String SERVER_PATH = "/server";

    private Map<String, List<ServerInfo>> instanceMap;

    private PathChildrenCache cache;

    // 订阅的监听器
    private List<NotifyListener> notifyListeners;

    @Override
    public void connect(String addr) throws Exception {
        this.client = CuratorFrameworkFactory.builder()
                .connectString(addr)
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

    /**
     * 生成path，name:ip:port:
     *
     * @param serverInfo
     * @return
     */
    private String toPath(ServerInfo serverInfo) {
        String path = serverInfo.getName() + ":" + serverInfo.getIp() + ":" + serverInfo.getPort() + "?serializerName=" + serverInfo.getSerializerName();
        try {
            return URLEncoder.encode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private ServerInfo toServiceInfo(String path) {
        path = path.substring(path.lastIndexOf("/") + 1);
        try {
            path = URLDecoder.decode(path, "UTF-8");
            String[] strs = path.split("\\?");
            String[] url = strs[0].split(":");
            String name = url[0];
            String ip = url[1];
            Integer port = Integer.parseInt(url[2]);
            String serializerName = null;
            if(strs.length >=2){
                String[] param = strs[1].split("&");
                serializerName = param[0];
            }
            return new ServerInfo(name, ip, port, serializerName);
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
}
