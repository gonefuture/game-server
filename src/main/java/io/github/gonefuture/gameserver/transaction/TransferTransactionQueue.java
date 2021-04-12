package io.github.gonefuture.gameserver.transaction;

import io.github.gonefuture.gameserver.object.TransferPlayer;

import java.util.LinkedList;

/**
 * 跨服事务队列：用于保证多个服的事务操作同步
 *
 * @author gonefuture
 * @version 2021/03/29 16:04
 */
public class TransferTransactionQueue {

    /**
     *  当前是否有事务正在执行中
     */
    private boolean running = false;

    /**
     *  事务节点链表
     */
    private LinkedList<TransactionNode> queue;


    public TransferTransactionQueue(){
        this.queue = new LinkedList<>();
    }

    public synchronized void submit(TransferPlayer transferPlayer, BaseT2GTransactionChain transaction) {
        transaction.setTransactionQueue(this);
        if (running) {
            TransactionNode node = new TransactionNode(transferPlayer.getPlayerId() ,transaction);
            queue.add(node);
        } else {
            running = true;
            TransferTransactionManager.self().beginTransaction(transferPlayer, transaction);
        }
    }

    /**
     *  上一个跨服事务直接结束回调
     */
    protected synchronized void callback() {
        if (queue.isEmpty()) {
            running = false;
            return;
        }
        TransactionNode transactionNode;
        boolean beginNew = false;
        while ((transactionNode = queue.poll()) != null) {
            TransferPlayer transferPlayer = TransferTransactionManager.self().getOnlinePlayer(transactionNode.playerId);
            if (transferPlayer == null) {
                // 打印警告
            } else {
                TransferTransactionManager.self().beginTransaction(transferPlayer, transactionNode.transactionChain);
                beginNew = true;
                break;
            }
        }
        if (!beginNew) {
            running = false;
        }
    }

    /**
     * @return 是否所有事务都已经执行完毕
     */
    public boolean isRunOver() {
        return queue.isEmpty();
    }

    /**
     *  事务节点
     */
    private class TransactionNode {
        private long playerId;
        private BaseT2GTransactionChain  transactionChain;

        public TransactionNode(long playerId, BaseT2GTransactionChain transactionChain) {
            this.playerId = playerId;
            this.transactionChain = transactionChain;
        }
    }
}
