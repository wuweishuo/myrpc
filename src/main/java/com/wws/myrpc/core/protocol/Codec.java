package com.wws.myrpc.core.protocol;

import com.wws.myrpc.core.exception.NoLongException;
import io.netty.buffer.ByteBuf;

public interface Codec {

    void read(ByteBuf byteBuf) throws NoLongException;

    void write(ByteBuf byteBuf);

}
