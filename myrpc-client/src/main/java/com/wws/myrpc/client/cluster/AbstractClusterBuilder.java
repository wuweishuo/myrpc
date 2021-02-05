package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.cluster.loadbalance.LoadBalance;
import com.wws.myrpc.registry.RegistryService;

/**
 * AbstractClusterBuilder
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-05
 */
public abstract class AbstractClusterBuilder implements ClusterBuilder {

    /**
     * 服务名
     */
    String serverName;

    /**
     * 负载均衡器
     */
    LoadBalance loadBalance;

    /**
     * 注册中心
     */
    RegistryService registryService;

    ClusterProperties clusterProperties;

    public AbstractClusterBuilder(ClusterProperties clusterProperties) {
        this.clusterProperties = clusterProperties;
    }

    @Override
    public ClusterBuilder with(LoadBalance loadBalance) {
        this.loadBalance = loadBalance;
        return this;
    }

    @Override
    public ClusterBuilder with(RegistryService registryService) {
        this.registryService = registryService;
        return this;
    }

    @Override
    public ClusterBuilder with(String serverName) {
        this.serverName = serverName;
        return this;
    }
}
