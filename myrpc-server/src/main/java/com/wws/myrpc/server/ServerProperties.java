package com.wws.myrpc.server;

import com.wws.myrpc.registry.RegistryProperties;

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

    private String registryName;

    private RegistryProperties registryProperties;

    private String serializerName;

    public ServerProperties(int port) {
        this.port = port;
    }

    public ServerProperties(int port, String name) {
        this.port = port;
        this.name = name;
    }

    public ServerProperties(int port, String name, boolean register, String registryName, RegistryProperties registryProperties, String serializerName) {
        this.port = port;
        this.name = name;
        this.register = register;
        this.registryName = registryName;
        this.registryProperties = registryProperties;
        this.serializerName = serializerName;
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

    public String getSerializerName() {
        return serializerName;
    }

    public void setSerializerName(String serializerName) {
        this.serializerName = serializerName;
    }
}
