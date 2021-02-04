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

    private String clusterName;

    private LoadBalanceProperties loadBalanceProperties;

    private RegistryProperties registryProperties;

    public ClusterClientProperties(String name, String clusterName, LoadBalanceProperties loadBalanceProperties, RegistryProperties registryProperties) {
        this.name = name;
        this.clusterName = clusterName;
        this.loadBalanceProperties = loadBalanceProperties;
        this.registryProperties = registryProperties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
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
