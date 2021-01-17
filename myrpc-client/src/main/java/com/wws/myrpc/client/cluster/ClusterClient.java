package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.Client;
import com.wws.myrpc.client.cluster.loadbalance.RandomLoadBalance;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.registry.RegistryServiceFactory;
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

    /**
     * 注册中心
     */
    private final RegistryService registryService;

    public ClusterClient(String name, String registerUrl) throws Exception {
        this.name = name;
        this.registryService = RegistryServiceFactory.getInstance("zookeeper", registerUrl);
        this.cluster = new FailFastCluster(name, new RandomLoadBalance(), registryService);
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
}
