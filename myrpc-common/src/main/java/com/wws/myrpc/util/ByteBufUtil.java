package com.wws.myrpc.util;

import io.netty.buffer.ByteBuf;

/**
 * ByteBufUtil
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class ByteBufUtil {

    /**
     * 无符号int
     *
     * @param byteBuf
     * @param x
     */
    public static void writeUnsignedInt(ByteBuf byteBuf, long x) {
        byteBuf.writeInt((int) (x & 0xffffffff));
    }

    /**
     * 无符号short
     *
     * @param byteBuf
     * @param x
     */
    public static void writeUnsignedShort(ByteBuf byteBuf, int x) {
        byteBuf.writeShort(x & 0xffff);
    }

}
