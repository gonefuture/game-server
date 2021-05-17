package io.github.gonefuture.gameserver.transaction.packet;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import io.github.gonefuture.gameserver.transaction.MsgData;

/**
 * 收到战斗服跨服事务，在游戏服执行事务
 *
 * @author gonefuture
 * @version 2021/04/09 18:09
 */
public class T2gTransactionResp {

    @Protobuf(description = "事务id")
    private int transactionId;

    @Protobuf(description = "事务id")
    private boolean success;

    @Protobuf(description = "事务执行返回数据")
    private MsgData returnMagData;

    public static T2gTransactionResp valueOfSuccess(int transactionId, MsgData returnMagData) {
        T2gTransactionResp resp = new T2gTransactionResp();
        // 未完成
       return resp;
    }

    public static T2gTransactionResp valueOfFail(int transactionId) {
        T2gTransactionResp resp = new T2gTransactionResp();
        // 未完成
        return resp;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public MsgData getReturnMagData() {
        return returnMagData;
    }

    public void setReturnMagData(MsgData returnMagData) {
        this.returnMagData = returnMagData;
    }
}
