package com.wws.myrpc.client.cluster.loadbalance;

import com.wws.myrpc.registry.ServerInfo;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * RandomLoadBalance
 * 随机负载均衡
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    public RandomLoadBalance(LoadBalanceProperties properties) {
        super(properties);
    }

    @Override
    protected ServerInfo doSelect(List<ServerInfo> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}
