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

public class RpcClientFactoryBean<T> implements FactoryBean<T>, DisposableBean, ApplicationContextAware {

    private Class<T> clientClass;

    private ProxyFactory proxyFactory;

    private Client client;

    private String clusterBean;

    private String loadBalanceBean;

    private String serializerBean;

    private String registryBean;

    private String ip;

    private int port;

    private String name;

    private ApplicationContext applicationContext;

    @Override
    public T getObject() throws Exception {
        GlobalClientProperties globalClientProperties = applicationContext.getBean(GlobalClientProperties.class);
        if(StringUtils.isEmpty(ip) || port == 0){
            RpcClusterProperties clusterProperties = globalClientProperties.getCluster();
            if(StringUtils.hasText(clusterBean)){
                clusterProperties = applicationContext.getBean(clusterBean, RpcClusterProperties.class);
            }
            RpcLoadBalanceProperties loadBalance = globalClientProperties.getLoadBalance();
            if(StringUtils.hasText(loadBalanceBean)){
                loadBalance = applicationContext.getBean(loadBalanceBean, RpcLoadBalanceProperties.class);
            }
            RpcRegistryProperties registry = globalClientProperties.getRegistry();
            if(StringUtils.hasText(registryBean)){
                registry = applicationContext.getBean(registryBean, RpcRegistryProperties.class);
            }
            client = new ClusterClient(new ClusterClientProperties(name, clusterProperties.toProperties(), loadBalance.toProperties(), registry.toProperties()));
        }else{
            RpcSerializerProperties serializerProperties = globalClientProperties.getSerializer();
            if(StringUtils.hasText(serializerBean)){
                serializerProperties = applicationContext.getBean(serializerBean, RpcSerializerProperties.class);
            }
            client = new SimpleClient(new SimpleClientProperties(ip, port, serializerProperties.toProperties()));
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

    public String getClusterBean() {
        return clusterBean;
    }

    public void setClusterBean(String clusterBean) {
        this.clusterBean = clusterBean;
    }

    public String getLoadBalanceBean() {
        return loadBalanceBean;
    }

    public void setLoadBalanceBean(String loadBalanceBean) {
        this.loadBalanceBean = loadBalanceBean;
    }

    public String getSerializerBean() {
        return serializerBean;
    }

    public void setSerializerBean(String serializerBean) {
        this.serializerBean = serializerBean;
    }

    public String getRegistryBean() {
        return registryBean;
    }

    public void setRegistryBean(String registryBean) {
        this.registryBean = registryBean;
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
