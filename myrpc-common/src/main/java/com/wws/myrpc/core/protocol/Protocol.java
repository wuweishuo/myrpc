package com.wws.myrpc.core.protocol;

import com.wws.myrpc.core.exception.NoLongException;
import io.netty.buffer.ByteBuf;

/**
 * Protocol
 * 协议包
 * header                              ｜ body
 * headerLen ｜ magic ｜ version ｜ flowId ｜ bodyLen ｜ body
 * 2         ｜ 4     ｜ 2       ｜ 4      ｜ 2       ｜ bodyLen
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class Protocol implements Codec {

    /**
     * 头部信息
     */
    private Header header;

    /**
     * 内容
     */
    private byte[] body;

    @Override
    public void read(ByteBuf byteBuf) throws NoLongException {
        header = new Header();
        header.read(byteBuf);

        int bodyLen = header.getBodyLen();
        if (byteBuf.readableBytes() < bodyLen) {
            throw new NoLongException("no long to read body");
        }

        body = new byte[header.getBodyLen()];
        byteBuf.readBytes(body, 0, header.getBodyLen());
    }

    @Override
    public void write(ByteBuf byteBuf) {
        header.write(byteBuf);
        if (body != null) {
            byteBuf.writeBytes(body);
        }
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
