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
import java.util.concurrent.atomic.AtomicReference;

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
    private String name;

    /**
     * 负载均衡器
     */
    private LoadBalance loadBalance;

    /**
     * 注册中心
     */
    private RegistryService registryService;

    /**
     * 注册中心监听器
     */
    private MyrpcNotifyListener notifyListener;

    /**
     * client连接管理器
     */
    private ConnectionManager connectionManager;

    /**
     * cluster状态
     */
    private final AtomicReference<ClusterStatusEnum> status = new AtomicReference<>(ClusterStatusEnum.INIT);

    @Override
    public void init(String name, LoadBalance loadBalance, RegistryService registryService) {
        this.loadBalance = loadBalance;
        this.registryService = registryService;
        this.name = name;
        this.notifyListener = new MyrpcNotifyListener();
        this.connectionManager = new ConnectionManagerImpl();
        registryService.subscribe(name, notifyListener);
        status.set(ClusterStatusEnum.RUNNING);
    }

    @Override
    public <T> T transport(Method method, Class<T> returnType, Object... args) throws Throwable {
        if (status.get() == ClusterStatusEnum.INIT) {
            throw new IllegalStateException("cluster don't init");
        }
        if (status.get() == ClusterStatusEnum.SHUTDOWN) {
            throw new IllegalStateException("cluster had shutdown");
        }
        List<ServerInfo> serverInfos = listServers();
        ServerInfo serverInfo = getLoadBalance().select(serverInfos);
        if (serverInfo == null) {
            throw new RpcException("server not found:" + getName());
        }
        return doTransport(serverInfo, method, returnType, args);
    }

    abstract <T> T doTransport(ServerInfo serverInfo, Method method, Class<T> returnType, Object... args) throws Throwable;

    @Override
    public void shutdown() {
        status.set(ClusterStatusEnum.SHUTDOWN);
        registryService.unsubscribe(name, notifyListener);
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

    protected String getName() {
        return name;
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
            return name;
        }

        public List<ServerInfo> getList() {
            return list == null ? Collections.emptyList() : list;
        }
    }
}
