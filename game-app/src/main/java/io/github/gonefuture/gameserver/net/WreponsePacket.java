package io.github.gonefuture.gameserver.net;

import io.netty.buffer.ByteBuf;

/**
 * 带协议id的协议包
 *
 * @author gonefuture
 * @version 2021/04/12 12:50
 */
public class WreponsePacket {
    /**
     *  协议di
     */
    private int packetId;

    /**
     *  数据
     */
    private byte[] data;

    private Object debugPacket;

    public static WreponsePacket valueOf(int packetId, byte[] data) {
        WreponsePacket wp = new WreponsePacket();
        wp.setPacketId(packetId);
        wp.data = data;
        return wp;
    }

    /**
     *  写入协议内容
     */
    public void write(ByteBuf out) {
        // 数据大小length标志, 本身加short的packetId占2个字段
        out.writeInt(data.length + 2);
        out.writeShort(packetId);
        out.writeBytes(data);
    }


    public int getPacketId() {
        return packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Object getDebugPacket() {
        return debugPacket;
    }

    public void setDebugPacket(Object debugPacket) {
        this.debugPacket = debugPacket;
    }
}
