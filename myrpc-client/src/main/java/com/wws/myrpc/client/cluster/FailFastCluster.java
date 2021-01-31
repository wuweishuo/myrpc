package com.wws.myrpc.client.cluster;

import com.wws.myrpc.core.exception.RpcException;
import com.wws.myrpc.registry.ServerInfo;

import java.lang.reflect.Method;
import java.util.List;

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
    public <T> T doTransport(List<ServerInfo> serverInfos, Method method, Class<T> returnType, Object... args) throws Throwable {
        ServerInfo serverInfo = getLoadBalance().select(serverInfos);
        if (serverInfo == null) {
            throw new RpcException("server not found:" + getName());
        }
        return getClient(serverInfo).transport(method, returnType, args);
    }


}
