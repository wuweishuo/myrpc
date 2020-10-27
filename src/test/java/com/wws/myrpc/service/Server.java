package com.wws.myrpc.service;

import com.wws.myrpc.service.impl.TestServiceImpl;

public class Server {

    public static void main(String[] args) throws InterruptedException {
        com.wws.myrpc.server.Server server = new com.wws.myrpc.server.Server(8080);
        server.registerService(TestService.class, new TestServiceImpl());
        server.start();
    }

}
