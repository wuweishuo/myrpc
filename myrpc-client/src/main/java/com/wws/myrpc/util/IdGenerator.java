package com.wws.myrpc.util;

/**
 * IdGenerator
 * flowId生成器
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public interface IdGenerator {

    /**
     * 生成flowId
     * @return
     */
    long generate();

}
