package com.wws.myrpc.demo.server;

import com.wws.myrpc.demo.service.TestService;
import com.wws.myrpc.demo.service.UserService;
import com.wws.myrpc.demo.service.impl.TestServiceImpl;
import com.wws.myrpc.demo.service.impl.UserServiceImpl;

public class Server {

    public static void main(String[] args) throws InterruptedException {
        com.wws.myrpc.server.Server server = new com.wws.myrpc.server.Server(8080);
        server.registerService(TestService.class, new TestServiceImpl());
        server.registerService(UserService.class, new UserServiceImpl());
        server.start();
    }

}
