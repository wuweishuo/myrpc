package com.wws.myrpc.client;

import com.wws.myrpc.client.cluster.ClusterClient;
import com.wws.myrpc.client.cluster.ClusterClientProperties;
import com.wws.myrpc.client.instance.SimpleClient;
import com.wws.myrpc.client.instance.SimpleClientProperties;
import com.wws.myrpc.client.proxy.ProxyFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

public class RpcClientFactoryBean<T> implements FactoryBean<T>, DisposableBean {

    private Class<T> clientClass;

    private ProxyFactory proxyFactory;

    private ClientProperties properties;

    private Client client;

    @Override
    public T getObject() throws Exception {
        if (properties instanceof ClusterClientProperties) {
            client = new ClusterClient((ClusterClientProperties) properties);
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

    @Override
    public void destroy() throws Exception {
        client.shutdown();
    }
}
