package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.Client;
import com.wws.myrpc.client.cluster.loadbalance.LoadBalance;
import com.wws.myrpc.registry.NotifyListener;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.registry.ServerInfo;

import java.util.Collections;
import java.util.List;

public abstract class AbstractCluster implements Cluster {

    private String name;

    private LoadBalance loadBalance;

    private RegistryService registryService;

    private MyrpcNotifyListener notifyListener;

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
