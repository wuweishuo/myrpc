package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.IClient;
import com.wws.myrpc.client.cluster.loadbalance.RandomLoadBalance;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.registry.RegistryServiceFactory;
import io.netty.channel.Channel;

import java.lang.reflect.Method;

public class ClusterClient implements IClient {

    private final String name;

    private final Cluster cluster;

    private final RegistryService registryService;

    public ClusterClient(String name, String registerUrl) throws Exception {
        this.name = name;
        this.registryService = RegistryServiceFactory.getInstance("zookeeper", registerUrl);
        this.cluster = new FailfastCluster(name, new RandomLoadBalance(), registryService);
    }

    @Override
    public Channel connect() {
        return null;
    }

    @Override
    public <T> T transport(Method method, Class<T> returnType, Object... args) throws Throwable {
        return cluster.transport(method, returnType, args);
    }
}
