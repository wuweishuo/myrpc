package com.wws.myrpc.client;

import io.netty.channel.Channel;

import java.lang.reflect.Method;

public interface Client {

    Channel connect() throws InterruptedException;

    <T> T transport(Method method, Class<T> returnType, Object... args) throws Throwable;

    void shutdown();
}
