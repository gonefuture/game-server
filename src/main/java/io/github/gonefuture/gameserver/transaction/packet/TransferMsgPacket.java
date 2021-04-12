package io.github.gonefuture.gameserver.transaction.packet;

import io.github.gonefuture.gameserver.net.WreponsePacket;
import io.github.gonefuture.gameserver.utility.ByteUtils;
import io.netty.buffer.ByteBuf;


/**
 * 自定义跨服消息包
 *
 * @author gonefuture
 * @version 2021/04/12 12:32
 */
public class TransferMsgPacket extends WreponsePacket {

    public static TransferMsgPacket valueOf(int packetId, int index, byte[] data) {
        TransferMsgPacket packet = new TransferMsgPacket();
        byte[] newData = new  byte[data.length + 4];
        System.arraycopy(ByteUtils.intToByte(index),0, newData, 0, 4);
        System.arraycopy(data,0, newData, 4, data.length);
        packet.setData(newData);
        packet.setPacketId(packetId);
        return packet;
    }

    @Override
    public void write(ByteBuf out) {
        out.writeInt(getData().length + 2);
        out.writeShort(getPacketId());
        out.writeBytes(getData());
    }
}
