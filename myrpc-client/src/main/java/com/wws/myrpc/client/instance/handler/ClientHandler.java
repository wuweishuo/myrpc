package com.wws.myrpc.client.instance.handler;

import com.wws.myrpc.client.instance.callback.CallbackContext;
import com.wws.myrpc.client.instance.callback.CallbackContextMap;
import com.wws.myrpc.client.instance.constance.AttributeKeyConst;
import com.wws.myrpc.core.protocol.Header;
import com.wws.myrpc.core.protocol.Protocol;
import com.wws.myrpc.core.protocol.Response;
import com.wws.myrpc.serialize.Serializer;
import com.wws.myrpc.serialize.impl.JdkSerializer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * ClientHandler
 * 客户端核心处理器，读取服务端返回信息
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class ClientHandler extends SimpleChannelInboundHandler<Protocol> {

    private Serializer serializer;

    public ClientHandler(Serializer serializer){
        this.serializer = serializer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Protocol protocol) throws Exception {
        Header header = protocol.getHeader();
        long flowId = header.getFlowId();
        CallbackContextMap callbackContextMap = channelHandlerContext.channel().attr(AttributeKeyConst.CALLBACK_CONTEXT_MAP_ATTRIBUTE_KEY).get();
        CallbackContext callbackContext = callbackContextMap.get(flowId);
        Response response = serializer.deserialize(protocol.getBody(), Response.class);
        Throwable exception = response.getException();
        if (exception != null) {
            callbackContext.getCallbackFuture().setError(exception);
        }
        callbackContext.getCallbackFuture().setResult(response.getResult());
        callbackContextMap.remove(flowId);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //处理心跳
        if (evt instanceof IdleStateEvent) {
            Protocol protocol = new Protocol();
            Header header = new Header();
            header.setFlowId(ctx.channel().attr(AttributeKeyConst.ID_GENERATOR_ATTRIBUTE_KEY).get().generate());
            protocol.setHeader(header);
            ctx.writeAndFlush(protocol);
        }
        super.userEventTriggered(ctx, evt);
    }
}
