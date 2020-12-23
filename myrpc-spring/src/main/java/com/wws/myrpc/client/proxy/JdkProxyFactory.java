package com.wws.myrpc.client.proxy;

import com.wws.myrpc.client.Client;

import java.lang.reflect.Proxy;

public class JdkProxyFactory implements ProxyFactory {
    @Override
    public <T> T getProxy(Client client, Class<T> clazz) {
        Class<?>[] interfaces = {clazz};
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new RpcClientHandler(client));
    }
}
