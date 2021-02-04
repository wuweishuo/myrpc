package com.wws.myrpc.client.cluster;

import com.wws.myrpc.registry.ServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * FailoverCluster
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-28
 */
public class FailOverCluster extends AbstractCluster {

    private static final Logger logger = LoggerFactory.getLogger(FailOverCluster.class);

    private static final int DEFAULT_RETRY = 3;

    public FailOverCluster(ClusterProperties properties) {
        super(properties);
    }

    @Override
    <T> T doTransport(List<ServerInfo> serverInfos, Method method, Class<T> returnType, Object... args) throws Throwable {
        Throwable e = null;
        List<ServerInfo> selected = new ArrayList<>();
        for (int i = 0; i < DEFAULT_RETRY; i++) {
            ServerInfo serverInfo = doSelect(serverInfos, selected);
            try {
                return getClient(serverInfo).transport(method, returnType, args);
            } catch (Throwable throwable) {
                logger.error("{} transport err:{}", serverInfo, throwable);
                e = throwable;
                selected.add(serverInfo);
            }
        }
        throw e;
    }
}
