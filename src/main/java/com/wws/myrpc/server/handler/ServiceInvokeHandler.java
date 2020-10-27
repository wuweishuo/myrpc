package com.wws.myrpc.server.handler;

import com.wws.myrpc.core.protocol.Header;
import com.wws.myrpc.core.protocol.Protocol;
import com.wws.myrpc.serialization.JdkSerializer;
import com.wws.myrpc.serialization.Serializer;
import com.wws.myrpc.server.locator.ServiceDescriptor;
import com.wws.myrpc.server.locator.ServiceLocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Type;

public class ServiceInvokeHandler extends SimpleChannelInboundHandler<Protocol> {

    private Serializer serializer = new JdkSerializer();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Protocol protocol) throws Exception {
        System.out.println("service invoking ....");
        Header header = protocol.getHeader();
        String method = header.getMethod();
        ServiceDescriptor serviceDescriptor = ServiceLocator.INS.get(method);
        Type[] parameterTypes = serviceDescriptor.getParameterTypes();
        Object[] args = serializer.deserialize(parameterTypes, protocol.getBody());
        Object returnObj = serviceDescriptor.getMethod().invoke(serviceDescriptor.getTarget(), args);
        Object[] returnObjs = {returnObj};
        Type[] returnTypes = {serviceDescriptor.getReturnType()};
        byte[] bytes = serializer.serialize(returnTypes, returnObjs);

        header.setBodyLen(bytes.length);
        Protocol retProtocol = new Protocol();
        retProtocol.setHeader(header);
        retProtocol.setBody(bytes);
        channelHandlerContext.channel().writeAndFlush(retProtocol);
    }
}
