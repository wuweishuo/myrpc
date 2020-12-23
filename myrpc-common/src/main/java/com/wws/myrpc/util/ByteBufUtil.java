package com.wws.myrpc.util;

import io.netty.buffer.ByteBuf;

public class ByteBufUtil {

    public static void writeUnsignedInt(ByteBuf byteBuf, long x) {
        byteBuf.writeInt((int) (x & 0xffffffff));
    }

    public static void writeUnsignedShort(ByteBuf byteBuf, int x) {
        byteBuf.writeShort(x & 0xffff);
    }

}
