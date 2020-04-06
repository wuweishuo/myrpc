package com.wws.myrpc.core.protocol;

import com.wws.myrpc.core.exception.NoLongException;
import io.netty.buffer.ByteBuf;

public class Header implements Codec {

    private int headerLen;

    private long magicNum;

    private int version;

    private long flowId;

    private long aid;

    private int methodLen;

    private String method;

    private int bodyLen;

    @Override
    public void read(ByteBuf byteBuf) throws NoLongException {
        if(byteBuf.readableBytes() < 4){
            throw new NoLongException("no long to read header len");
        }
        headerLen = byteBuf.readUnsignedShort();

        if(byteBuf.readableBytes() < headerLen){
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
    }

    public int getHeaderLen() {
        return headerLen;
    }

    public void setHeaderLen(int headerLen) {
        this.headerLen = headerLen;
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

    public void setMethodLen(int methodLen) {
        this.methodLen = methodLen;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
