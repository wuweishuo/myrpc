package com.wws.myrpc.client;

import com.wws.myrpc.RpcClusterProperties;
import com.wws.myrpc.RpcLoadBalanceProperties;
import com.wws.myrpc.RpcRegistryProperties;
import com.wws.myrpc.RpcSerializerProperties;
import com.wws.myrpc.client.cluster.ClusterClient;
import com.wws.myrpc.client.cluster.ClusterClientProperties;
import com.wws.myrpc.client.instance.SimpleClient;
import com.wws.myrpc.client.instance.SimpleClientProperties;
import com.wws.myrpc.client.proxy.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.Map;

public class RpcClientFactoryBean<T> implements FactoryBean<T>, DisposableBean, ApplicationContextAware {

    private Class<T> clientClass;

    private ProxyFactory proxyFactory;

    private Client client;

    private String ip;

    private int port;

    private String name;

    private ApplicationContext applicationContext;

    @Override
    public T getObject() throws Exception {
        RpcClientProperties rpcClientProperties = applicationContext.getBean(RpcClientProperties.class);
        RpcInstanceProperties global = rpcClientProperties.getGlobal();
        Map<String, RpcInstanceProperties> instances = rpcClientProperties.getInstances();
        RpcInstanceProperties instance = instances.get(name);
        if (StringUtils.isEmpty(ip) || port == 0) {
            RpcClusterProperties cluster = global.getCluster();
            RpcLoadBalanceProperties loadBalance = global.getLoadBalance();
            RpcRegistryProperties registry = global.getRegistry();
            if (instance != null) {
                if (instance.getCluster() != null) {
                    cluster = instance.getCluster();
                }
                if (instance.getLoadBalance() != null) {
                    loadBalance = instance.getLoadBalance();
                }
                if (instance.getRegistry() != null) {
                    registry = instance.getRegistry();
                }
            }
            client = new ClusterClient(new ClusterClientProperties(name, cluster.toProperties(), loadBalance.toProperties(), registry.toProperties()));
        } else {
            RpcSerializerProperties serializerProperties = global.getSerializer();
            RpcSerializerProperties serializer = global.getSerializer();
            if (instance != null && instance.getSerializer() != null) {
                serializer = instance.getSerializer();
            }
            client = new SimpleClient(new SimpleClientProperties(ip, port, serializer.toProperties()));
        }
        return proxyFactory.getProxy(client, clientClass);
    }

    @Override
    public Class<?> getObjectType() {
        return clientClass;
    }

    @Override
    public void destroy() throws Exception {
        client.shutdown();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
