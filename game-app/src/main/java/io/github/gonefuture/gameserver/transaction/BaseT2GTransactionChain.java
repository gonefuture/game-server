package io.github.gonefuture.gameserver.transaction;

import io.github.gonefuture.gameserver.object.TransferPlayer;

/**
 * 跨服事务链，需要多事务执行同步时使用
 *
 * @author gonefuture
 * @version 2021/03/29 16:09
 */
public abstract class BaseT2GTransactionChain<ReturnMsg> extends BaseT2GTransaction<ReturnMsg> {

    /**
     *  所属事务队列
     */
    private TransferTransactionQueue transactionQueue;



    @Override
    public void successCallback(TransferPlayer transferPlayer, ReturnMsg returnMsg) {
        try {
            successCallback0(transferPlayer, returnMsg);
        } finally {
            transactionQueue.callback();
        }
    }

    @Override
    public void failCallback(TransferPlayer transferPlayer) {

    }

    @Override
    public void cancel(TransferPlayer transferPlayer) {
        super.cancel(transferPlayer);
    }

    @Override
    public void timeout(long playerId) {

    }

    /**
     *  事务执行成功
     */
    public abstract void successCallback0(TransferPlayer transferPlayer, ReturnMsg returnMsg);

    /**
     * 事务执行失败
     */
    public abstract void failCallback0(TransferPlayer transferPlayer);

    /**
     *  事务回调时玩家已经离开跨服
     */
    public abstract void timeout0(long playerId);

    public TransferTransactionQueue getTransactionQueue() {
        return transactionQueue;
    }

    public void setTransactionQueue(TransferTransactionQueue transactionQueue) {
        this.transactionQueue = transactionQueue;
    }
}
