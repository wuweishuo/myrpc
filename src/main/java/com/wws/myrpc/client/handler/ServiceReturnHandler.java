package com.wws.myrpc.client.handler;

import com.wws.myrpc.client.callback.CallbackContext;
import com.wws.myrpc.client.callback.CallbackPool;
import com.wws.myrpc.core.protocol.Header;
import com.wws.myrpc.core.protocol.Protocol;
import com.wws.myrpc.serialization.JdkSerializer;
import com.wws.myrpc.serialization.Serializer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Type;

public class ServiceReturnHandler extends SimpleChannelInboundHandler<Protocol> {

    private final Serializer serializer = new JdkSerializer();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Protocol protocol) throws Exception {
        System.out.println("service return ......");
        Header header = protocol.getHeader();
        long flowId = header.getFlowId();
        CallbackContext callbackContext = CallbackPool.INS.get(flowId);
        Type returnType = callbackContext.getReturnType();
        Type[] returnTypes = {returnType};
        try {
            Object[] obj = serializer.deserialize(returnTypes, protocol.getBody());
            callbackContext.getCallbackFuture().setResult(obj[0]);
        }catch (Throwable e){
            callbackContext.getCallbackFuture().setError(e);
        }
    }
}
