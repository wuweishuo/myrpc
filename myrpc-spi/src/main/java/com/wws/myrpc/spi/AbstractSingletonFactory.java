package com.wws.myrpc.spi;

/**
 * AbstractSingletonFactory
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-04
 */
public abstract class AbstractSingletonFactory<T, P extends SPIProperties> implements SPIFactory<T, P> {

    private volatile T instance;

    @Override
    public T getInstance(P properties) {
        if(instance == null){
            synchronized (this){
                if (instance == null){
                    instance = initInstance(properties);
                }
            }
        }
        return instance;
    }

    public abstract T initInstance(P properties);
}
