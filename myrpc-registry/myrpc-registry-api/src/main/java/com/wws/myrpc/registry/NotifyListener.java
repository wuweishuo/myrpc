package com.wws.myrpc.registry;

import java.util.List;

/**
 * NotifyListener
 * 注册中心监听器
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public interface NotifyListener {

    /**
     * 变更通知
     *
     * @param serverInfoList
     */
    void notify(List<ServerInfo> serverInfoList);

    /**
     * 订阅的服务名
     *
     * @return
     */
    String name();

}
