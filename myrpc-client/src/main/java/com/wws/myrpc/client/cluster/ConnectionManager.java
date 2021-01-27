package com.wws.myrpc.client.cluster;

import com.wws.myrpc.client.Client;
import com.wws.myrpc.registry.ServerInfo;

import java.util.List;

/**
 * ConnectionManager
 * client连接管理器
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public interface ConnectionManager {

    /**
     * 获取client
     *
     * @param serverInfo 服务信息
     * @return
     */
    Client getClient(ServerInfo serverInfo);

    /**
     * 刷新管理的client连接
     *
     * @param serverInfoList 从注册中心获取的最新服务信息
     */
    void refresh(List<ServerInfo> serverInfoList);

    /**
     * 关闭所有连接
     */
    void shutdown();

}
