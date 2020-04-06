package com.wws.myrpc.core.protocol;

import com.wws.myrpc.core.exception.NoLongException;
import io.netty.buffer.ByteBuf;

public class Protocol implements Codec {

    private Header header;

    private byte[] body;

    @Override
    public void read(ByteBuf byteBuf) throws NoLongException {
        header = new Header();
        header.read(byteBuf);

        int bodyLen = header.getBodyLen();
        if(byteBuf.readableBytes() < bodyLen){
            throw new NoLongException("no long to read body");
        }

        body = new byte[header.getBodyLen()];
        byteBuf.readBytes(body, 0, header.getBodyLen());
    }

    @Override
    public void write(ByteBuf byteBuf) {
        header.write(byteBuf);
        byteBuf.writeBytes(body);
    }
}
