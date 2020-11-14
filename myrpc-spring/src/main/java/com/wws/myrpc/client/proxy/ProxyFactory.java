package com.wws.myrpc.client.proxy;

import com.wws.myrpc.client.Client;

public interface ProxyFactory {

    <T> T getProxy(Client client, Class<T> clazz);

}
