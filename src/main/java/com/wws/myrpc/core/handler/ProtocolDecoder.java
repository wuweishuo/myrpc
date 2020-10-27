package com.wws.myrpc.core.handler;

import com.wws.myrpc.core.exception.NoLongException;
import com.wws.myrpc.core.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ProtocolDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("decode .....");
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
