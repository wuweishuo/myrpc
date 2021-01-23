package com.wws.myrpc.demo;

import com.wws.myrpc.client.Client;
import com.wws.myrpc.client.instance.SimpleClient;
import com.wws.myrpc.demo.domain.User;
import com.wws.myrpc.demo.service.UserService;

import java.lang.reflect.Method;

/**
 * Consumer
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-23
 */
public class Consumer {

    public static void main(String[] args) throws Throwable {
        Client client = new SimpleClient("127.0.0.1", 9000);
        client.connect();
        Class<UserService> clazz = UserService.class;
        Method getById = clazz.getMethod("getById", Integer.class);
        User user = client.transport(getById, User.class, 1);
        System.out.println(user);
        client.shutdown();
    }

}
