package com.wws.myrpc.client.instance;

import com.wws.myrpc.client.ClientProperties;
import com.wws.myrpc.serialize.SerializerProperties;

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

    private SerializerProperties serializerProperties;

    public SimpleClientProperties(String ip, Integer port, SerializerProperties serializerProperties) {
        this.ip = ip;
        this.port = port;
        this.serializerProperties = serializerProperties;
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

    public SerializerProperties getSerializerProperties() {
        return serializerProperties;
    }

    public void setSerializerProperties(SerializerProperties serializerProperties) {
        this.serializerProperties = serializerProperties;
    }
}
