package com.wws.myrpc.client;

import com.wws.myrpc.RpcClusterProperties;
import com.wws.myrpc.RpcLoadBalanceProperties;
import com.wws.myrpc.RpcRegistryProperties;
import com.wws.myrpc.RpcSerializerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * GlobalProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-05
 */
@ConfigurationProperties(prefix = "myrpc.client.global")
public class GlobalClientProperties {

    @NestedConfigurationProperty
    private RpcRegistryProperties registry;

    @NestedConfigurationProperty
    private RpcLoadBalanceProperties loadBalance;

    @NestedConfigurationProperty
    private RpcSerializerProperties serializer;

    @NestedConfigurationProperty
    private RpcClusterProperties cluster;

    public RpcRegistryProperties getRegistry() {
        return registry;
    }

    public void setRegistry(RpcRegistryProperties registry) {
        this.registry = registry;
    }

    public RpcLoadBalanceProperties getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(RpcLoadBalanceProperties loadBalance) {
        this.loadBalance = loadBalance;
    }

    public RpcSerializerProperties getSerializer() {
        return serializer;
    }

    public void setSerializer(RpcSerializerProperties serializer) {
        this.serializer = serializer;
    }

    public RpcClusterProperties getCluster() {
        return cluster;
    }

    public void setCluster(RpcClusterProperties cluster) {
        this.cluster = cluster;
    }
}