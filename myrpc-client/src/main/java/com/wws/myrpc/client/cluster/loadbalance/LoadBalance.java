package com.wws.myrpc.client.cluster.loadbalance;

import com.wws.myrpc.registry.ServerInfo;

import java.util.List;

/**
 * LoadBalance
 * 负载均衡器
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public interface LoadBalance {

    /**
     * 根据负载均衡策略选择服务
     *
     * @param list
     * @return
     */
    ServerInfo select(List<ServerInfo> list);

}
