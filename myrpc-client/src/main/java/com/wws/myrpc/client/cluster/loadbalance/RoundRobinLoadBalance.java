package com.wws.myrpc.client.cluster.loadbalance;

import com.wws.myrpc.registry.ServerInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * RoundRobinLoadBalance
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-28
 */
public class RoundRobinLoadBalance extends AbstractLoadBalance {

    private final Map<String, Map<String, AtomicInteger>> roundRobinMap = new ConcurrentHashMap<>();

    public RoundRobinLoadBalance(LoadBalanceProperties properties) {
        super(properties);
    }

    @Override
    protected ServerInfo doSelect(List<ServerInfo> list) {
        String serverName = list.get(0).getName();
        Map<String, AtomicInteger> serverRoundRobin = roundRobinMap.computeIfAbsent(serverName, k -> new ConcurrentHashMap<>());
        int max  = Integer.MIN_VALUE;
        int total = 0;
        ServerInfo selected = null;
        for (ServerInfo serverInfo : list) {
            AtomicInteger weight = serverRoundRobin.computeIfAbsent(serverInfo.getUniqueKey(), k -> new AtomicInteger());
            weight.incrementAndGet();
            if(weight.get() > max){
                selected = serverInfo;
            }
            total++;
        }
        if(selected != null){
            AtomicInteger atomicInteger = serverRoundRobin.get(selected.getUniqueKey());
            atomicInteger.addAndGet(-total);
        }
        if(list.size() != serverRoundRobin.size()) {
            List<String> keys = list.stream().map(ServerInfo::getUniqueKey).collect(Collectors.toList());
            for (String key : serverRoundRobin.keySet()) {
                if(!keys.contains(key)){
                    serverRoundRobin.remove(key);
                }
            }
        }
        return selected;
    }
}
