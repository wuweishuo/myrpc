package com.wws.myrpc.client.cluster.loadbalance;

import com.wws.myrpc.registry.ServerInfo;

import java.util.List;

/**
 * AbstractLoadBalance
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    protected LoadBalanceProperties properties;

    public AbstractLoadBalance(LoadBalanceProperties properties) {
        this.properties = properties;
    }

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
