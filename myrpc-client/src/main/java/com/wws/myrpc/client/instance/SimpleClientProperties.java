package com.wws.myrpc.client.instance;

import com.wws.myrpc.client.ClientProperties;

/**
 * SimpleClientProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-19
 */
public class SimpleClientProperties implements ClientProperties {

    private String ip;

    private Integer port;

    private String serializerName;

    public SimpleClientProperties(String ip, Integer port, String serializerName) {
        this.ip = ip;
        this.port = port;
        this.serializerName = serializerName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSerializerName() {
        return serializerName;
    }

    public void setSerializerName(String serializerName) {
        this.serializerName = serializerName;
    }
}
