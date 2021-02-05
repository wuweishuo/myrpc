package com.wws.myrpc.client.cluster;

/**
 * FailFastClusterBuilder
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-05
 */
public class FailFastClusterBuilder extends AbstractClusterBuilder {

    public FailFastClusterBuilder(ClusterProperties clusterProperties) {
        super(clusterProperties);
    }

    @Override
    public Cluster build() {
        return new FailFastCluster(serverName, clusterProperties, loadBalance, registryService);
    }
}
