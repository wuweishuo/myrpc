package com.wws.myrpc.registry;

/**
 * RegistryService
 * 注册中心
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public interface RegistryService {

    /**
     * 断开注册中心连接
     * @throws Exception
     */
    void destroy() throws Exception;

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
