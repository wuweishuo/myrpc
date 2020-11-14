package com.wws.myrpc.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "myrpc.server")
public class RpcServerProperties {

    private Integer port;

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
}
