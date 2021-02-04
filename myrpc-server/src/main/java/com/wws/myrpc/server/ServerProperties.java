package com.wws.myrpc.server;

import com.wws.myrpc.registry.RegistryProperties;
import com.wws.myrpc.serialize.SerializerProperties;

/**
 * ServerProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-23
 */
public class ServerProperties {

    private int port;

    private String name;

    private boolean register;

    private RegistryProperties registryProperties;

    private SerializerProperties serializerProperties;

    public ServerProperties(int port) {
        this.port = port;
    }

    public ServerProperties(int port, String name) {
        this.port = port;
        this.name = name;
    }

    public ServerProperties(int port, String name, boolean register, RegistryProperties registryProperties, SerializerProperties serializerProperties) {
        this.port = port;
        this.name = name;
        this.register = register;
        this.registryProperties = registryProperties;
        this.serializerProperties = serializerProperties;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public RegistryProperties getRegistryProperties() {
        return registryProperties;
    }

    public void setRegistryProperties(RegistryProperties registryProperties) {
        this.registryProperties = registryProperties;
    }

    public SerializerProperties getSerializerProperties() {
        return serializerProperties;
    }

    public void setSerializerProperties(SerializerProperties serializerProperties) {
        this.serializerProperties = serializerProperties;
    }
}
