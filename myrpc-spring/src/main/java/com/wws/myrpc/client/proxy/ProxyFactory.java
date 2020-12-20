package com.wws.myrpc.client.proxy;

import com.wws.myrpc.client.IClient;

public interface ProxyFactory {

    <T> T getProxy(IClient client, Class<T> clazz);

}
