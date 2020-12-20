package com.wws.myrpc.client.proxy;

import com.wws.myrpc.client.IClient;

import java.lang.reflect.Proxy;

public class JdkProxyFactory implements ProxyFactory {
    @Override
    public <T> T getProxy(IClient client, Class<T> clazz) {
        Class<?>[] interfaces = {clazz};
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new RpcClientHandler(client));
    }
}
