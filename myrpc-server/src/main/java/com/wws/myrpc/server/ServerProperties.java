package com.wws.myrpc.server;

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

    private String registerUrl;

    private String serializerName;

    public ServerProperties(int port) {
        this.port = port;
    }

    public ServerProperties(int port, String name) {
        this.port = port;
        this.name = name;
    }

    public ServerProperties(int port, String name, boolean register, String registryName, String registerUrl, String serializerName) {
        this.port = port;
        this.name = name;
        this.register = register;
        this.registryName = registryName;
        this.registerUrl = registerUrl;
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

    public String getRegisterUrl() {
        return registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

    public String getSerializerName() {
        return serializerName;
    }

    public void setSerializerName(String serializerName) {
        this.serializerName = serializerName;
    }
}
