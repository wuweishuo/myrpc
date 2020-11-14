package com.wws.myrpc.client;

import com.wws.myrpc.client.proxy.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

public class RpcClientFactoryBean<T> implements FactoryBean<T> {

    private String ip;

    private int port;

    private Class<T> clientClass;

    private ProxyFactory proxyFactory;

    @Override
    public T getObject() throws Exception {
        Client client = new Client(ip, port);
        return proxyFactory.getProxy(client, clientClass);
    }

    @Override
    public Class<?> getObjectType() {
        return clientClass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Class<T> getClientClass() {
        return clientClass;
    }

    public void setClientClass(Class<T> clientClass) {
        this.clientClass = clientClass;
    }

    public ProxyFactory getProxyFactory() {
        return proxyFactory;
    }

    public void setProxyFactory(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }
}