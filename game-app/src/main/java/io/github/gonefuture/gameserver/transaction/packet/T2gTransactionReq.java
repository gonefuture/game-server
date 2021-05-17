package io.github.gonefuture.gameserver.transaction.packet;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import io.github.gonefuture.gameserver.transaction.MsgData;

/**
 * 战斗服发往游戏服事务
 *
 * @author gonefuture
 * @version 2021/04/08 20:02
 */
public class T2gTransactionReq {


    @Protobuf(description = "事务id")
    private int transactionId;

    @Protobuf(description = "事务信息")
    private MsgData msgData;

    public static T2gTransactionReq valueOf(int transactionId, MsgData msgData) {
        T2gTransactionReq req = new T2gTransactionReq();
        req.transactionId = transactionId;
        req.msgData = msgData;
        return req;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public MsgData getMsgData() {
        return msgData;
    }

    public void setMsgData(MsgData msgData) {
        this.msgData = msgData;
    }
}
