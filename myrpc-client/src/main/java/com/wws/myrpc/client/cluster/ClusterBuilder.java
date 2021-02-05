package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.cluster.loadbalance.LoadBalance;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.spi.SPI;

/**
 * ClusterBuilder
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-05
 */
@SPI(value = "failFast", properties = ClusterProperties.class)
public interface ClusterBuilder {

    ClusterBuilder with(LoadBalance loadBalance);

    ClusterBuilder with(RegistryService registryService);

    ClusterBuilder with(String serverName);

    Cluster build();

}
