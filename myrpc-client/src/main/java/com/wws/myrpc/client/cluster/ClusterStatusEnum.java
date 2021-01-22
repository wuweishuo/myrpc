package com.wws.myrpc.client.cluster;

/**
 * ClusterStatusEnum
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-19
 */
public enum ClusterStatusEnum {
    /**
     * 初始化
     */
    INIT(),
    /**
     * 运行中
     */
    RUNNING(),
    /**
     * 已销毁
     */
    SHUTDOWN();

}
