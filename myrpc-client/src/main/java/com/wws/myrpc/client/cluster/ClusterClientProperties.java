package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.ClientProperties;
import com.wws.myrpc.client.cluster.loadbalance.LoadBalanceProperties;
import com.wws.myrpc.registry.RegistryProperties;

/**
 * ClusterProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-19
 */
public class ClusterClientProperties implements ClientProperties {

    private String name;

    private ClusterProperties clusterProperties;

    private LoadBalanceProperties loadBalanceProperties;

    private RegistryProperties registryProperties;

    public ClusterClientProperties(String name, ClusterProperties clusterProperties, LoadBalanceProperties loadBalanceProperties, RegistryProperties registryProperties) {
        this.name = name;
        this.clusterProperties = clusterProperties;
        this.loadBalanceProperties = loadBalanceProperties;
        this.registryProperties = registryProperties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClusterProperties getClusterProperties() {
        return clusterProperties;
    }

    public void setClusterProperties(ClusterProperties clusterProperties) {
        this.clusterProperties = clusterProperties;
    }

    public LoadBalanceProperties getLoadBalanceProperties() {
        return loadBalanceProperties;
    }

    public void setLoadBalanceProperties(LoadBalanceProperties loadBalanceProperties) {
        this.loadBalanceProperties = loadBalanceProperties;
    }

    public RegistryProperties getRegistryProperties() {
        return registryProperties;
    }

    public void setRegistryProperties(RegistryProperties registryProperties) {
        this.registryProperties = registryProperties;
    }
}
