package com.wws.myrpc.server;

import com.wws.myrpc.server.locator.ServiceDescriptor;
import com.wws.myrpc.server.locator.ServiceLocator;
import com.wws.myrpc.service.TestService;
import com.wws.myrpc.service.impl.TestServiceImpl;
import junit.framework.TestCase;

public class ServerTest extends TestCase {

    public void testRegisterService() {
        Server server = new Server(8000);
        server.registerService(TestService.class, new TestServiceImpl());
        ServiceDescriptor serviceDescriptor = ServiceLocator.INS.get("public abstract java.lang.String com.wws.myrpc.service.TestService.hello(java.lang.String)");
        System.out.println(serviceDescriptor);
    }
}
