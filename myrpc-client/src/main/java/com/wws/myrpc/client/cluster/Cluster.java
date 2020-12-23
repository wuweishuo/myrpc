package com.wws.myrpc.client.cluster;

import java.lang.reflect.Method;

public interface Cluster {

    <T> T transport(Method method, Class<T> returnType, Object... args) throws Throwable;

    void shutdown();

}
