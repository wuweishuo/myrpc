package com.wws.myrpc.client.cluster;

import com.wws.myrpc.registry.ServerInfo;

import java.lang.reflect.Method;
import java.util.Collections;
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

    public FailFastCluster(ClusterProperties properties) {
        super(properties);
    }

    @Override
    public <T> T doTransport(List<ServerInfo> serverInfos, Method method, Class<T> returnType, Object... args) throws Throwable {
        ServerInfo serverInfo = doSelect(serverInfos, Collections.emptyList());
        return getClient(serverInfo).transport(method, returnType, args);
    }


}
