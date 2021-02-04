package com.wws.myrpc.spi;

/**
 * AbstarctFactory
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-04
 */
public interface SPIFactory<T, P extends SPIProperties> {

    T getInstance(P properties);

}
