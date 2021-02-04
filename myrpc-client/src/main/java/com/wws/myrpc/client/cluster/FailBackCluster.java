package com.wws.myrpc.client.cluster;

import com.wws.myrpc.core.exception.RpcException;
import com.wws.myrpc.registry.ServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * FailbackCluster
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-28
 */
public class FailBackCluster extends AbstractCluster {

    private static final Logger logger = LoggerFactory.getLogger(FailBackCluster.class);

    private static final int DEFAULT_RETRY = 3;

    private static final int RETRY_PERIOD_SECOND = 5;

    private static final String THREAD_PREFIX = "fail_back_thread";

    private final AtomicInteger seq = new AtomicInteger();

    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(8, r -> new Thread(r, THREAD_PREFIX + "_" + seq.getAndIncrement()));

    public FailBackCluster(ClusterProperties properties) {
        super(properties);
    }

    @Override
    <T> T doTransport(List<ServerInfo> serverInfos, Method method, Class<T> returnType, Object... args) throws RpcException {
        ServerInfo serverInfo = doSelect(serverInfos, Collections.emptyList());
        try {
            return getClient(serverInfo).transport(method, returnType, args);
        } catch (Throwable throwable) {
            logger.error("transport err:", throwable);
            addFailed(serverInfos, serverInfo, method, returnType, args);
            return null;
        }
    }

    private void addFailed(List<ServerInfo> serverInfos, ServerInfo selected, Method method, Class<?> returnType, Object... args) {
        scheduledThreadPoolExecutor.schedule(new RetryTask(DEFAULT_RETRY, 0, serverInfos,
                new ArrayList<>(Collections.singleton(selected)), method, returnType, args), RETRY_PERIOD_SECOND, TimeUnit.SECONDS);
    }

    private class RetryTask implements Runnable {

        private final int maxRetry;

        private int retry;

        private final List<ServerInfo> serverInfos;

        private final List<ServerInfo> selected;

        private final Method method;

        private final Class<?> returnType;

        private final Object[] args;

        public RetryTask(int maxRetry, int retry, List<ServerInfo> serverInfos, List<ServerInfo> selected, Method method, Class<?> returnType, Object... args) {
            this.maxRetry = maxRetry;
            this.serverInfos = serverInfos;
            this.selected = selected;
            this.method = method;
            this.returnType = returnType;
            this.args = args;
            this.retry = retry;
        }

        private RetryTask increaseAndGet(){
            retry++;
            return this;
        }


        @Override
        public void run() {
            ServerInfo serverInfo = null;
            try {
                serverInfo = doSelect(serverInfos, selected);
                getClient(serverInfo).transport(method, returnType, args);
            } catch (Throwable throwable) {
                if(serverInfo != null) {
                    logger.error("{} transport err:{}", serverInfo, throwable);
                    selected.add(serverInfo);
                }
                RetryTask retryTask = increaseAndGet();
                if(retryTask.retry > retryTask.maxRetry){
                    logger.error("{} retry err", this);
                    return;
                }
                scheduledThreadPoolExecutor.schedule(increaseAndGet(), RETRY_PERIOD_SECOND, TimeUnit.SECONDS);
            }
        }

        @Override
        public String toString() {
            return "RetryTask{" +
                    "serverInfos=" + serverInfos +
                    ", selected=" + selected +
                    ", method=" + method +
                    ", returnType=" + returnType +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
