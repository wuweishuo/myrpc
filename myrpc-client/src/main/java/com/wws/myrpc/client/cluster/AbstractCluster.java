package com.wws.myrpc.client.cluster;

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

    public AbstractCluster(String name, LoadBalance loadBalance, RegistryService registryService){
        this.loadBalance = loadBalance;
        this.registryService = registryService;
        this.name = name;
        this.notifyListener = new MyrpcNotifyListener();
        registryService.subscribe(name, notifyListener);
    }

    protected List<ServerInfo> listServers(){
        return notifyListener.getList();
    }

    protected LoadBalance getLoadBalance(){
        return loadBalance;
    }

    protected String getName() {
        return name;
    }

    private class MyrpcNotifyListener implements NotifyListener {

        private List<ServerInfo> list;

        @Override
        public void notify(List<ServerInfo> serverInfoList) {
            list = serverInfoList;
        }

        @Override
        public String name() {
            return name;
        }

        public List<ServerInfo> getList(){
            return list == null ? Collections.emptyList() : list;
        }
    }
}
