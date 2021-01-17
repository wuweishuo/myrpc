package com.wws.myrpc.util.impl;

import com.wws.myrpc.util.IdGenerator;

import java.util.UUID;

/**
 * UUIdGenerator
 * 使用uuid生成flowId
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class UUIdGenerator implements IdGenerator {
    @Override
    public long generate() {
        return Math.abs(UUID.randomUUID().toString().hashCode());
    }
}
