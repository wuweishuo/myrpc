package com.wws.myrpc.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "myrpc.server")
public class RpcServerProperties {

    private Integer port;

    private boolean register;

    private String name;

    private String registerUri;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getRegisterUri() {
        return registerUri;
    }

    public void setRegisterUri(String registerUri) {
        this.registerUri = registerUri;
    }

    public boolean getRegister() {
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
}
