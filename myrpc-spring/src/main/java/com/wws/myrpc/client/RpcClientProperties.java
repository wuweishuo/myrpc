package com.wws.myrpc.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

/**
 * GlobalProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-05
 */
@ConfigurationProperties(prefix = "myrpc.client")
public class RpcClientProperties {

    @NestedConfigurationProperty
    private RpcInstanceProperties global;

    @NestedConfigurationProperty
    private Map<String, RpcInstanceProperties> instances;

    public RpcInstanceProperties getGlobal() {
        return global;
    }

    public void setGlobal(RpcInstanceProperties global) {
        this.global = global;
    }

    public Map<String, RpcInstanceProperties> getInstances() {
        return instances;
    }

    public void setInstances(Map<String, RpcInstanceProperties> instances) {
        this.instances = instances;
    }
}