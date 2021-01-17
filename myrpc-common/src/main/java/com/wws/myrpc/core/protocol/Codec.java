package com.wws.myrpc.core.protocol;

import com.wws.myrpc.core.exception.NoLongException;
import io.netty.buffer.ByteBuf;

/**
 * Codec
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public interface Codec {

    /**
     * byteBuf读取为protocol
     *
     * @param byteBuf ByteBuf
     * @throws NoLongException ByteBuf长度不够异常
     */
    void read(ByteBuf byteBuf) throws NoLongException;

    /**
     * protocol写入byteBuf
     *
     * @param byteBuf ByteBuf
     */
    void write(ByteBuf byteBuf);

}
