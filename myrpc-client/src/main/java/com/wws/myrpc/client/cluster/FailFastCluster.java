package com.wws.myrpc.client.cluster;

import com.wws.myrpc.registry.ServerInfo;

import java.lang.reflect.Method;

/**
 * FailFastCluster
 * 使用failfast策略
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class FailFastCluster extends AbstractCluster {

    @Override
    public <T> T doTransport(ServerInfo serverInfo, Method method, Class<T> returnType, Object... args) throws Throwable {
        return getClient(serverInfo).transport(method, returnType, args);
    }


}
