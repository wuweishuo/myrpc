package com.wws.myrpc.service.impl;

import com.wws.myrpc.service.TestService;

public class TestServiceImpl implements TestService {
    @Override
    public String hello(String name) {
        return "hello " + name;
    }
}
