package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.cluster.loadbalance.LoadBalance;
import com.wws.myrpc.core.exception.RpcException;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.registry.ServerInfo;

import java.lang.reflect.Method;
import java.util.List;

public class FailFastCluster extends AbstractCluster {

    public FailFastCluster(String name, LoadBalance loadBalance, RegistryService registryService) {
        super(name, loadBalance, registryService);
    }

    @Override
    public <T> T transport(Method method, Class<T> returnType, Object... args) throws Throwable {
        List<ServerInfo> serverInfos = listServers();
        ServerInfo serverInfo = getLoadBalance().select(serverInfos);
        if (serverInfo == null) {
            throw new RpcException("server not found:" + getName());
        }
        return getClient(serverInfo).transport(method, returnType, args);
    }


}
