package com.wws.myrpc.client.cluster.loadbalance;

import com.wws.myrpc.registry.ServerInfo;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected ServerInfo doSelect(List<ServerInfo> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}
