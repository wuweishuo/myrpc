package com.wws.myrpc.core.handler;

import com.wws.myrpc.core.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * ProtocolDecoder protocol编码器
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class ProtocolEncoder extends MessageToByteEncoder<Protocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Protocol protocol, ByteBuf byteBuf) throws Exception {
        protocol.write(byteBuf);
    }
}
