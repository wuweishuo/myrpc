package com.wws.myrpc.client;

import com.wws.myrpc.client.cluster.ClusterClient;
import com.wws.myrpc.client.cluster.ClusterProperties;
import com.wws.myrpc.client.instance.SimpleClient;
import com.wws.myrpc.client.instance.SimpleClientProperties;
import com.wws.myrpc.client.proxy.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

public class RpcClientFactoryBean<T> implements FactoryBean<T> {

    private Class<T> clientClass;

    private ProxyFactory proxyFactory;

    private ClientProperties properties;

    @Override
    public T getObject() throws Exception {
        Client client;
        if (properties instanceof ClusterProperties) {
            client = new ClusterClient((ClusterProperties) properties);
        } else {
            client = new SimpleClient((SimpleClientProperties) properties);
        }
        return proxyFactory.getProxy(client, clientClass);
    }

    @Override
    public Class<?> getObjectType() {
        return clientClass;
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

    public ClientProperties getProperties() {
        return properties;
    }

    public void setProperties(ClientProperties properties) {
        this.properties = properties;
    }
}
