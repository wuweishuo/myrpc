package com.wws.myrpc.core.protocol;

import com.wws.myrpc.core.exception.NoLongException;
import com.wws.myrpc.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;

public class Header implements Codec {

    /**
     * 头部长度
     */
    private int headerLen = FIX_LENGTH;

    /**
     * 魔数，标示包的起始位置
     */
    private long magicNum = 0xfb709394;

    /**
     * 协议版本号
     */
    private int version = 1;

    /**
     * 流水号
     */
    private long flowId;

    /**
     * 请求体长度
     */
    private int bodyLen;

    private static final int FIX_LENGTH = 2+4+2+4+2;

    @Override
    public void read(ByteBuf byteBuf) throws NoLongException {
        if(byteBuf.readableBytes() < 4){
            throw new NoLongException("no long to read header len");
        }
        headerLen = byteBuf.readUnsignedShort();

        if(byteBuf.readableBytes() < headerLen - 4){
            throw new NoLongException("no long to read header");
        }

        magicNum = byteBuf.readUnsignedInt();
        version = byteBuf.readUnsignedShort();
        flowId = byteBuf.readUnsignedInt();
        bodyLen = byteBuf.readUnsignedShort();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        ByteBufUtil.writeUnsignedShort(byteBuf, headerLen);
        ByteBufUtil.writeUnsignedInt(byteBuf, magicNum);
        ByteBufUtil.writeUnsignedShort(byteBuf, version);
        ByteBufUtil.writeUnsignedInt(byteBuf, flowId);
        ByteBufUtil.writeUnsignedShort(byteBuf, bodyLen);
    }

    public int getHeaderLen() {
        return headerLen;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getFlowId() {
        return flowId;
    }

    public void setFlowId(long flowId) {
        this.flowId = flowId;
    }

    public int getBodyLen() {
        return bodyLen;
    }

    public void setBodyLen(int bodyLen) {
        this.bodyLen = bodyLen;
    }

    public long getMagicNum() {
        return magicNum;
    }

    public void setMagicNum(long magicNum) {
        this.magicNum = magicNum;
    }
}
