package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.cluster.loadbalance.LoadBalance;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.spi.SPI;

import java.lang.reflect.Method;

/**
 * Cluster
 * 集群
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
@SPI("failFast")
public interface Cluster {

    void init(String name, LoadBalance loadBalance, RegistryService registryService);

    <T> T transport(Method method, Class<T> returnType, Object... args) throws Throwable;

    void shutdown();

}
