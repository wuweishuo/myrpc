package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.cluster.loadbalance.LoadBalance;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.spi.ExtensionLoaderFactory;

/**
 * ClusterFactory
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-20
 */
public class ClusterFactory {

    public static Cluster getInstance(String name, String serviceName, LoadBalance loadBalance, RegistryService registryService, ClusterProperties properties){
        Cluster cluster = ExtensionLoaderFactory.load(Cluster.class, name);
        cluster.init(serviceName, loadBalance, registryService, properties);
        return cluster;
    }

}
