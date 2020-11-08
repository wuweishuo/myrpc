package com.wws.myrpc.demo.client;

import com.wws.myrpc.client.Client;
import com.wws.myrpc.core.exception.RpcException;
import com.wws.myrpc.demo.domain.User;
import com.wws.myrpc.demo.service.UserService;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ClientTest {

    @Test
    public void testEcho() throws Throwable {
        Client client = new Client("127.0.0.1", 8080);
        Method method = UserService.class.getDeclaredMethod("echo", User.class);
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId(i);
            user.setName("test");
            User result = client.transport(method, User.class, user);
            System.out.println(i + " result:" + result);
        }

    }

    @Test
    public void testNullEcho() throws Throwable {
        Client client = new Client("127.0.0.1", 8080);
        Method echo = UserService.class.getDeclaredMethod("echo", User.class);
        User result = client.transport(echo, User.class, new Object[1]);
        System.out.println(result);
    }

    @Test
    public void testList() throws Throwable {
        Client client = new Client("127.0.0.1", 8080);
        Method echo = UserService.class.getDeclaredMethod("list");
        List result = client.transport(echo, List.class);
        System.out.println(result);
    }

    @Test
    public void testArray() throws Throwable {
        Client client = new Client("127.0.0.1", 8080);
        Method echo = UserService.class.getDeclaredMethod("array");
        User[] result = client.transport(echo, User[].class);
        System.out.println(Arrays.toString(result));
    }

    @Test
    public void testSaveArray() throws Throwable{
        Client client = new Client("127.0.0.1", 8080);
        Method echo = UserService.class.getDeclaredMethod("saveArr", User[].class);
        Object[] objects = {new User[0]};
        client.transport(echo, void.class, objects);
    }

    @Test(expected = RpcException.class)
    public void testException() throws Throwable{
        Client client = new Client("127.0.0.1", 8080);
        Method echo = UserService.class.getDeclaredMethod("exception");
        client.transport(echo, void.class);
    }

    @Test
    public void testMultiParam() throws Throwable{
        Client client = new Client("127.0.0.1", 8080);
        Method echo = UserService.class.getDeclaredMethod("multiParam", long.class, String.class);
        User user = client.transport(echo, User.class, 1, "test");
        System.out.println(user);
    }


}
