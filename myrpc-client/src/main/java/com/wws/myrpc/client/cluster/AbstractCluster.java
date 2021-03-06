package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.Client;
import com.wws.myrpc.client.cluster.loadbalance.LoadBalance;
import com.wws.myrpc.core.exception.RpcException;
import com.wws.myrpc.registry.NotifyListener;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.registry.ServerInfo;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AbstractCluster
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public abstract class AbstractCluster implements Cluster {

    /**
     * 服务名
     */
    private final String serverName;

    /**
     * 负载均衡器
     */
    private final LoadBalance loadBalance;

    /**
     * 注册中心
     */
    private final RegistryService registryService;

    /**
     * 注册中心监听器
     */
    private final MyrpcNotifyListener notifyListener;

    /**
     * client连接管理器
     */
    private final ConnectionManager connectionManager;

    protected final ClusterProperties properties;

    public AbstractCluster(String serverName, ClusterProperties properties, LoadBalance loadBalance, RegistryService registryService) {
        this.serverName = serverName;
        this.properties = properties;
        this.loadBalance = loadBalance;
        this.registryService = registryService;

        this.notifyListener = new MyrpcNotifyListener();
        this.connectionManager = new ConnectionManagerImpl();
        registryService.subscribe(serverName, notifyListener);
    }

    @Override
    public <T> T transport(Method method, Class<T> returnType, Object... args) throws Throwable {
        List<ServerInfo> serverInfos = listServers();
        return doTransport(serverInfos, method, returnType, args);
    }

    abstract <T> T doTransport(List<ServerInfo> serverInfos, Method method, Class<T> returnType, Object... args) throws Throwable;

    public ServerInfo doSelect(List<ServerInfo> serverInfos, List<ServerInfo> selected) throws RpcException {
        ServerInfo serverInfo = getLoadBalance().select(serverInfos);
        if (serverInfo == null) {
            throw new RpcException("server not found:" + getServerName());
        }
        if (serverInfos.size() == selected.size()) {
            return serverInfo;
        }
        if (selected.contains(serverInfo)) {
            List<ServerInfo> reselect = serverInfos.stream().filter(s -> !selected.contains(s)).collect(Collectors.toList());
            ServerInfo reselectInfo = getLoadBalance().select(reselect);
            if (reselectInfo != null) {
                return reselectInfo;
            }
        }
        return serverInfo;
    }

    @Override
    public void shutdown() throws Exception {
        registryService.unsubscribe(serverName, notifyListener);
        registryService.destroy();
        connectionManager.shutdown();
    }

    /**
     * 获取所有服务信息
     *
     * @return
     */
    protected List<ServerInfo> listServers() {
        return notifyListener.getList();
    }

    protected LoadBalance getLoadBalance() {
        return loadBalance;
    }

    protected String getServerName() {
        return serverName;
    }

    protected Client getClient(ServerInfo serverInfo) {
        return connectionManager.getClient(serverInfo);
    }

    private class MyrpcNotifyListener implements NotifyListener {

        private List<ServerInfo> list;

        @Override
        public void notify(List<ServerInfo> serverInfoList) {
            list = serverInfoList;
            connectionManager.refresh(list);
        }

        @Override
        public String name() {
            return serverName;
        }

        public List<ServerInfo> getList() {
            return list == null ? Collections.emptyList() : list;
        }
    }
}
