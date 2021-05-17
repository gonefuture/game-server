package io.github.gonefuture.gameserver.transaction;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

/**
 * 网络信息数据
 *
 * @author gonefuture
 * @version 2021/04/08 12:32
 */
public class MsgData {

    @Protobuf(description = "索引")
    private int index;
    @Protobuf(description = "数据")
    private byte[] data;

    public static MsgData valueOF(int index, byte[] data) {
        MsgData vo = new MsgData();
        vo.index = index;
        vo.data = data;
        return vo;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
