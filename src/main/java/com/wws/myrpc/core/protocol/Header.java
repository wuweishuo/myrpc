package com.wws.myrpc.core.protocol;

import com.wws.myrpc.core.exception.NoLongException;
import com.wws.myrpc.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;

public class Header implements Codec {

    /**
     * 头部长度
     */
    private int headerLen;

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
     * 请求主键
     */
    private long aid;

    /**
     * method字段长度
     */
    private int methodLen;

    /**
     * 标示调用方法
     */
    private String method;

    /**
     * 请求体长度
     */
    private int bodyLen;

    private static final int FIX_LENGTH = 2+4+2+4+4+2+2;

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
        aid = byteBuf.readUnsignedInt();
        methodLen = byteBuf.readUnsignedShort();
        byte[] bytes = new byte[methodLen];
        byteBuf.readBytes(bytes, 0, methodLen);
        method = new String(bytes);
        bodyLen = byteBuf.readUnsignedShort();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        ByteBufUtil.writeUnsignedShort(byteBuf, FIX_LENGTH + methodLen);
        ByteBufUtil.writeUnsignedInt(byteBuf, magicNum);
        ByteBufUtil.writeUnsignedShort(byteBuf, version);
        ByteBufUtil.writeUnsignedInt(byteBuf, flowId);
        ByteBufUtil.writeUnsignedInt(byteBuf, aid);
        ByteBufUtil.writeUnsignedShort(byteBuf, methodLen);
        byteBuf.writeBytes(method.getBytes());
        ByteBufUtil.writeUnsignedShort(byteBuf, bodyLen);
    }

    public int getHeaderLen() {
        return FIX_LENGTH + methodLen;
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

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
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

    public int getMethodLen() {
        return methodLen;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
        this.methodLen = method.length();
    }
}
