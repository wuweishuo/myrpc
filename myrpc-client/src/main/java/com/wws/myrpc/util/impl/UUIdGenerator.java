package com.wws.myrpc.util.impl;

import com.wws.myrpc.util.IdGenerator;

import java.util.UUID;

public class UUIdGenerator implements IdGenerator {
    @Override
    public long generate() {
        return Math.abs(UUID.randomUUID().toString().hashCode());
    }
}
