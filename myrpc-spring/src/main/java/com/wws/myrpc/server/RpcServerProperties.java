package com.wws.myrpc.server;

import com.wws.myrpc.RpcRegistryProperties;
import com.wws.myrpc.RpcSerializerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "myrpc.server")
public class RpcServerProperties {

    private Integer port;

    private boolean register;

    private String name;

    @NestedConfigurationProperty
    private RpcRegistryProperties registry;

    @NestedConfigurationProperty
    private RpcSerializerProperties serializer;

    protected ServerProperties getServerProperties(){
        return new ServerProperties(port, name, register, registry.toProperties(), serializer.toProperties());
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RpcRegistryProperties getRegistry() {
        return registry;
    }

    public void setRegistry(RpcRegistryProperties registry) {
        this.registry = registry;
    }

    public RpcSerializerProperties getSerializer() {
        return serializer;
    }

    public void setSerializer(RpcSerializerProperties serializer) {
        this.serializer = serializer;
    }
}
