package com.wws.myrpc.client;

import com.wws.myrpc.service.TestService;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

public class ClientTest {


    @Test
    public void testTransport() throws NoSuchMethodException, InterruptedException, ExecutionException, IOException {
        Client client = new Client("127.0.0.1", 8080);
        Method hello = TestService.class.getDeclaredMethod("hello", String.class);
        Object[] params = {"test"};
        Object result = client.transport(hello, params);
        System.out.println(result);
    }

}
