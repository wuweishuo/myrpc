package com.wws.myrpc.client.cluster.loadbalance;

import com.wws.myrpc.registry.ServerInfo;

import java.util.List;

public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public ServerInfo select(List<ServerInfo> list) {
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        return doSelect(list);
    }

    protected abstract ServerInfo doSelect(List<ServerInfo> list);
}
