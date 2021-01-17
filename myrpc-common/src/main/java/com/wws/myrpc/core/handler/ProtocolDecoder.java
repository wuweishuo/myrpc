package com.wws.myrpc.core.handler;

import com.wws.myrpc.core.exception.NoLongException;
import com.wws.myrpc.core.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * ProtocolDecoder protocol解码器
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class ProtocolDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();

        Protocol protocol = new Protocol();
        try {
            protocol.read(byteBuf);
        } catch (NoLongException e) {
            byteBuf.resetReaderIndex();
            return;
        }

        list.add(protocol);

    }
}
