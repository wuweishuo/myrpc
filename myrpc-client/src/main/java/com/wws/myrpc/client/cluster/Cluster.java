package com.wws.myrpc.client.cluster;

import java.lang.reflect.Method;

/**
 * Cluster
 * 集群
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public interface Cluster {

    <T> T transport(Method method, Class<T> returnType, Object... args) throws Throwable;

    void shutdown() throws Exception;

}
