package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.Client;
import com.wws.myrpc.client.cluster.loadbalance.LoadBalance;
import com.wws.myrpc.client.cluster.loadbalance.LoadBalanceProperties;
import com.wws.myrpc.registry.RegistryProperties;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.registry.RegistryServiceFactory;
import com.wws.myrpc.spi.ExtensionLoaderFactory;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(ClusterClient.class);

    /**
     * 服务名
     */
    private final String name;

    /**
     * 集群
     */
    private final Cluster cluster;

    public ClusterClient(ClusterClientProperties properties) throws Exception {
        this.name = properties.getName();
        LoadBalanceProperties loadBalanceProperties = properties.getLoadBalanceProperties();
        RegistryProperties registryProperties = properties.getRegistryProperties();
        LoadBalance loadBalance = ExtensionLoaderFactory.load(LoadBalance.class,
                loadBalanceProperties.getProperty(LoadBalanceProperties.LOAD_BALANCE_ADDR));
        RegistryServiceFactory registryServiceFactory = ExtensionLoaderFactory.load(RegistryServiceFactory.class,
                registryProperties.getProperty(RegistryProperties.SERVER_NAME));
        RegistryService registryService = registryServiceFactory.connect(properties.getRegistryProperties());
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
    public void shutdown() throws Exception {
        cluster.shutdown();
        logger.info("client:{} shutdown", this);
    }

    @Override
    public String toString() {
        return "ClusterClient{" +
                "name='" + name + '\'' +
                '}';
    }
}
