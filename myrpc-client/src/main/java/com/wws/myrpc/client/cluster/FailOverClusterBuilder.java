package com.wws.myrpc.client.cluster;

/**
 * FailBackClusterBuilder
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-05
 */
public class FailOverClusterBuilder extends AbstractClusterBuilder {

    public FailOverClusterBuilder(ClusterProperties clusterProperties) {
        super(clusterProperties);
    }

    @Override
    public Cluster build() {
        return new FailOverCluster(serverName, clusterProperties, loadBalance, registryService);
    }
}
