package com.wws.myrpc.server.locator;

import com.wws.myrpc.service.TestService;
import com.wws.myrpc.service.impl.TestServiceImpl;
import org.junit.Test;

public class ServiceLocatorTest {

    private ServiceLocator serviceLocator = ServiceLocator.INS;

    @Test
    public void testGet() {
        serviceLocator.register(TestService.class, new TestServiceImpl());
        ServiceDescriptor serviceDescriptor = serviceLocator.get("public abstract java.lang.String com.wws.myrpc.service.TestService.hello(java.lang.String)");
        System.out.println(serviceDescriptor);
    }
}