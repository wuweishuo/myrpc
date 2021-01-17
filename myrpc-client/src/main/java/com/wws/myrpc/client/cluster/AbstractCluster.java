package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.Client;
import com.wws.myrpc.client.cluster.loadbalance.LoadBalance;
import com.wws.myrpc.registry.NotifyListener;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.registry.ServerInfo;

import java.util.Collections;
import java.util.List;

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

    public AbstractCluster(String name, LoadBalance loadBalance, RegistryService registryService) {
        this.loadBalance = loadBalance;
        this.registryService = registryService;
        this.name = name;
        this.notifyListener = new MyrpcNotifyListener();
        this.connectionManager = new ConnectionManagerImpl();
        registryService.subscribe(name, notifyListener);
    }

    @Override
    public void shutdown() {
        registryService.unsubscribe(name, notifyListener);
        connectionManager.shutdown();
    }

    /**
     * 获取所有服务信息
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
