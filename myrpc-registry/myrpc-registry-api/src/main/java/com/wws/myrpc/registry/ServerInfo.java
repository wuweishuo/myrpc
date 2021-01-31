package com.wws.myrpc.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * ServerInfo
 * 服务信息
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class ServerInfo {

    /**
     * 服务名
     */
    private String name;

    /**
     * ip
     */
    private String ip;

    /**
     * port
     */
    private Integer port;

    /**
     * 是否可用
     */
    private boolean enable;

    /**
     * 序列化方式
     */
    private String serializerName;

    /**
     * 额外信息
     */
    private final Map<String, String> metaData;

    public ServerInfo(String name, String ip, Integer port, String serializerName) {
        this(name, ip, port, true, serializerName);
    }

    public ServerInfo(String name, String ip, Integer port, boolean enable, String serializerName) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.enable = enable;
        this.serializerName = serializerName;
        this.metaData = new HashMap<>();
    }

    public String getUniqueKey() {
        return ip + ":" + port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getSerializerName() {
        return serializerName;
    }

    public void setSerializerName(String serializerName) {
        this.serializerName = serializerName;
    }

    public void setProperties(String key, String value){
        metaData.put(key, value);
    }

    public String getProperties(String key){
        return metaData.get(key);
    }
}
