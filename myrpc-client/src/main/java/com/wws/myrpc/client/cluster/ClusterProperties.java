package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.ClientProperties;
import com.wws.myrpc.registry.RegistryProperties;

/**
 * ClusterProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-19
 */
public class ClusterProperties implements ClientProperties {

    private String name;

    private String clusterName;

    private String loadBalanceName;

    private String registryName;

    private RegistryProperties registryProperties;

    public ClusterProperties(String name, String clusterName, String loadBalanceName, String registryName, RegistryProperties registryProperties) {
        this.name = name;
        this.clusterName = clusterName;
        this.loadBalanceName = loadBalanceName;
        this.registryName = registryName;
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

    public String getLoadBalanceName() {
        return loadBalanceName;
    }

    public void setLoadBalanceName(String loadBalanceName) {
        this.loadBalanceName = loadBalanceName;
    }

    public String getRegistryName() {
        return registryName;
    }

    public void setRegistryName(String registryName) {
        this.registryName = registryName;
    }

    public RegistryProperties getRegistryProperties() {
        return registryProperties;
    }

    public void setRegistryProperties(RegistryProperties registryProperties) {
        this.registryProperties = registryProperties;
    }
}
