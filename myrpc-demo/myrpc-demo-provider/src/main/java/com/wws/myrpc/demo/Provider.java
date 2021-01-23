package com.wws.myrpc.demo;

import com.wws.myrpc.demo.service.UserService;
import com.wws.myrpc.demo.service.impl.UserServiceImpl;
import com.wws.myrpc.server.Server;

/**
 * Provider
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-23
 */
public class Provider {

    public static void main(String[] args) throws Exception {
        Server server = new Server(9000);
        server.start();
        UserService userService = new UserServiceImpl();
        server.registerService(UserService.class, userService);
    }

}
