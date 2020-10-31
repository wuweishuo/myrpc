package com.wws.myrpc.demo.service.impl;

import com.wws.myrpc.demo.service.TestService;

public class TestServiceImpl implements TestService {
    @Override
    public String hello(String name) {
        return "hello "+name;
    }
}
