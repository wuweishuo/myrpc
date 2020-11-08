package com.wws.myrpc.server.handler;

import com.wws.myrpc.core.exception.ServiceNotFoundException;
import com.wws.myrpc.core.protocol.Header;
import com.wws.myrpc.core.protocol.Protocol;
import com.wws.myrpc.core.protocol.Request;
import com.wws.myrpc.core.protocol.Response;
import com.wws.myrpc.serialize.Serializer;
import com.wws.myrpc.serialize.impl.JdkSerializer;
import com.wws.myrpc.server.RequestContext;
import com.wws.myrpc.server.locator.ServiceDescriptor;
import com.wws.myrpc.server.locator.ServiceLocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServerHandler extends SimpleChannelInboundHandler<Protocol> {

    private final Serializer serializer = new JdkSerializer();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Protocol protocol) throws Exception {
        System.out.println("service invoking ....");
        // 反序列化request
        Header header = protocol.getHeader();

        //心跳不处理
        if(header.getBodyLen() == 0){
            return;
        }

        RequestContext.set(new RequestContext(header.getVersion(), header.getFlowId()));
        Request request = serializer.deserialize(protocol.getBody(), Request.class);
        String methodName = request.getMethod();
        ServiceDescriptor serviceDescriptor = ServiceLocator.INS.get(methodName);

        // 服务调用
        Response response = new Response();
        Method method = serviceDescriptor.getMethod();
        if (method == null) {
            response.setException(new ServiceNotFoundException(methodName));
        } else {
            try {
                Object returnObj = method.invoke(serviceDescriptor.getTarget(), request.getArgs());
                response.setResult(returnObj);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                cause.printStackTrace();
                response.setException(cause);
            } catch (Throwable e) {
                e.printStackTrace();
                response.setException(e);
            }
        }

        // 序列化response
        Protocol retProtocol = new Protocol();
        retProtocol.setHeader(header);
        byte[] bytes = serializer.serialize(response);
        if (bytes != null) {
            header.setBodyLen(bytes.length);
            retProtocol.setBody(bytes);
        }
        channelHandlerContext.channel().writeAndFlush(retProtocol);

        RequestContext.clear();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        RequestContext requestContext = RequestContext.get();
        if(requestContext != null) {
            Protocol protocol = new Protocol();
            Response response = new Response();
            response.setException(cause);
            byte[] bytes = serializer.serialize(response);
            protocol.setBody(bytes);

            Header header = new Header();
            header.setFlowId(requestContext.getFlowId());
            header.setBodyLen(bytes.length);
            protocol.setHeader(header);

            cause.printStackTrace();
            ctx.channel().writeAndFlush(protocol);
        }else{
            super.exceptionCaught(ctx, cause);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.close();
        }
        super.userEventTriggered(ctx, evt);
    }
}
