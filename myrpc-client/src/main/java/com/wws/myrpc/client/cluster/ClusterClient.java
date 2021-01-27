package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.Client;
import com.wws.myrpc.client.cluster.loadbalance.LoadBalance;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.registry.RegistryServiceFactory;
import com.wws.myrpc.spi.ExtensionLoaderFactory;
import io.netty.channel.Channel;

import java.lang.reflect.Method;

/**
 * ClusterClient
 * 集群客户端
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class ClusterClient implements Client {

    /**
     * 服务名
     */
    private final String name;

    /**
     * 集群
     */
    private final Cluster cluster;

    public ClusterClient(ClusterProperties properties) throws Exception {
        this.name = properties.getName();
        LoadBalance loadBalance = ExtensionLoaderFactory.load(LoadBalance.class, properties.getLoadBalanceName());
        RegistryService registryService = RegistryServiceFactory.getInstance(properties.getRegistryName(), properties.getRegisterUrl());
        this.cluster = ClusterFactory.getInstance(properties.getClusterName(), name, loadBalance, registryService);
    }

    @Override
    public Channel connect() {
        return null;
    }

    @Override
    public <T> T transport(Method method, Class<T> returnType, Object... args) throws Throwable {
        return cluster.transport(method, returnType, args);
    }

    @Override
    public void shutdown() {
        cluster.shutdown();
    }

    @Override
    public String toString() {
        return "ClusterClient{" +
                "name='" + name + '\'' +
                '}';
    }
}
