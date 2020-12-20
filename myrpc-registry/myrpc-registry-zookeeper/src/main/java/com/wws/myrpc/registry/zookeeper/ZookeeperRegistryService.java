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

import java.util.*;
import java.util.stream.Collectors;

public class ZookeeperRegistryService implements RegistryService {

    private CuratorFramework client;

    private final String BASE_PATH = "myrpc";

    private final String SERVER_PATH = "/server";

    private Map<String, List<ServerInfo>> instanceMap;

    private PathChildrenCache cache;

    private List<NotifyListener> notifyListeners;

    @Override
    public void connect(String addr) throws Exception {
        this.client = CuratorFrameworkFactory.builder()
                .connectString(addr)
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
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
        if(instanceMap != null) {
            serverInfos = instanceMap.get(name);
        }else{
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
     * @param serverInfo
     * @return
     */
    private String toPath(ServerInfo serverInfo){
        return serverInfo.getName() + ":" +serverInfo.getIp() + ":" + serverInfo.getPort();
    }

    private ServerInfo toServiceInfo(String path){
        path = path.substring(path.lastIndexOf("/") + 1);
        String[] strs = path.split(":");
        String name = strs[0];
        String ip = strs[1];
        Integer port = Integer.parseInt(strs[2]);
        ServerInfo serverInfo = new ServerInfo(name, ip, port);
        return serverInfo;
    }

    private void refreshMap(){
        List<ChildData> currentDatas = cache.getCurrentData();
        Map<String, List<ServerInfo>> map = currentDatas.stream()
                .map(ChildData::getPath)
                .map(ZookeeperRegistryService.this::toServiceInfo)
                .collect(Collectors.groupingBy(ServerInfo::getName));
        instanceMap = map;
        for (NotifyListener notifyListener : notifyListeners) {
            String name = notifyListener.name();
            notifyListener.notify(instanceMap.getOrDefault(name, Collections.emptyList()));
        }
    }

    private class MyRpcPathChildrenCacheListener implements PathChildrenCacheListener{

        @Override
        public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
            refreshMap();
        }
    }
}
