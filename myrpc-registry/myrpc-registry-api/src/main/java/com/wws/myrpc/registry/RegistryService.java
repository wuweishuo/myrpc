package com.wws.myrpc.registry;

import com.wws.myrpc.spi.SPI;

/**
 * RegistryService
 * 注册中心
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
@SPI("zookeeper")
public interface RegistryService {

    /**
     * 连接注册中心
     *
     * @param addr
     * @throws Exception
     */
    void connect(String addr) throws Exception;

    /**
     * 注册服务
     *
     * @param serverInfo
     * @throws Exception
     */
    void register(ServerInfo serverInfo) throws Exception;

    /**
     * 取消注册
     *
     * @param serverInfo
     * @throws Exception
     */
    void unregister(ServerInfo serverInfo) throws Exception;

    /**
     * 订阅服务
     *
     * @param name
     * @param notifyListener
     */
    void subscribe(String name, NotifyListener notifyListener);

    /**
     * 取消订阅
     *
     * @param name
     * @param notifyListener
     */
    void unsubscribe(String name, NotifyListener notifyListener);

}
