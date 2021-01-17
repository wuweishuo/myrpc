package com.wws.myrpc.client;

import io.netty.channel.Channel;

import java.lang.reflect.Method;

/**
 * Client
 * 客户端
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public interface Client {

    Channel connect() throws InterruptedException;

    /**
     * rpc远程调用
     *
     * @param method
     * @param returnType
     * @param args
     * @param <T>
     * @return
     * @throws Throwable
     */
    <T> T transport(Method method, Class<T> returnType, Object... args) throws Throwable;

    void shutdown();
}
