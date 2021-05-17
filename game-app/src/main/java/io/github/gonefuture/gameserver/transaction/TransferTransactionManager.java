package io.github.gonefuture.gameserver.transaction;

import io.github.gonefuture.gameserver.exception.ManagedException;
import io.github.gonefuture.gameserver.object.Player;
import io.github.gonefuture.gameserver.object.TransferPlayer;
import io.github.gonefuture.gameserver.transaction.packet.T2gTransactionReq;
import io.github.gonefuture.gameserver.transaction.packet.T2gTransactionResp;
import org.apache.commons.lang3.time.DateUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 跨服事务管理器
 *
 * @author gonefuture
 * @version 2021/04/02 18:14
 */
public class TransferTransactionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferTransactionManager.class);

    private static TransferTransactionManager instance;

    public TransferTransactionManager() {
        instance = this;
    }

    public static TransferTransactionManager self() {
        return instance;
    }

    /**
     *  事务过期时间
     */
    private static final long TRANSACTION_TIMEOUT = 2 * DateUtils.MILLIS_PER_SECOND;


    /**
     *  战斗服 -》 游戏服 事务
     */
    private Map<Integer, BaseT2GTransaction> t2GTransactionMap = new ConcurrentHashMap<>();


    @Scheduled(cron = "0/3 * * * * ?")
    public void checkTimeout() {
        long now = System.currentTimeMillis();
        t2GTransactionMap.values().removeIf(t2GTransaction -> {
           if (now - t2GTransaction.getBeginTime() >= TRANSACTION_TIMEOUT) {
               // 事务超时
               long playerId = t2GTransaction.getPlayerId();
               t2GTransaction.timeout(playerId);
               // 超时日志
               LOGGER.error(String.format("%s transaction timeout! remove transaction [%s]", playerId, t2GTransaction));
               return false;
           }
           return false;
        });
    }



    /**
     *  开启事务
     * @param transferPlayer 跨服玩家
     * @param t2GTransaction 事务
     */
    public void beginTransaction(TransferPlayer transferPlayer, BaseT2GTransaction t2GTransaction) {
        t2GTransaction.setPlayerId(transferPlayer.getPlayerId());
        int transactionId = Integer.MAX_VALUE;
        if (!t2GTransaction.isExecTransaction(transferPlayer)) {
            // 如果判定不可执行，取消事务
            t2GTransaction.cancel(transferPlayer);
            return;
        }
        MsgData msgData;
        try {
            msgData = TransferMsgManager.self().buildMsg(t2GTransaction);
        } catch (Exception e) {
            throw new RuntimeException(String.format("%s not support @ITransferMsg", t2GTransaction));
        }

        // 发送跨服协议
        T2gTransactionReq req = T2gTransactionReq.valueOf(transactionId, msgData);
        transferPlayer.sendPacket(req);
        t2GTransaction.setBeginTime(System.currentTimeMillis());
        // 将时间放入map中待检测
        t2GTransactionMap.put(transactionId, t2GTransaction);
        LOGGER.debug("transaction begin, {} {}", transactionId, t2GTransaction);
    }

    /**
     *  收到战斗服跨服事务，在游戏服执行事务
     * @param player 玩家
     * @param t2gTransactionReq 事务请求
     * @return 事务执行结果
     */
    public T2gTransactionResp runTransactionInGameServer(Player player, T2gTransactionReq t2gTransactionReq) {
        int transactionId = t2gTransactionReq.getTransactionId();
        BaseT2GTransaction baseT2GTransaction = null;
        try {
            baseT2GTransaction = TransferMsgManager.self().parseMsg(t2gTransactionReq.getMsgData());
            Object returnMsg = baseT2GTransaction.runInGameServer(player);
            MsgData msgData = null;
            if (returnMsg != null) {
                msgData = TransferMsgManager.self().buildMsg(returnMsg);;
            }
            LOGGER.debug("transaction run, {} : {}", transactionId, baseT2GTransaction);
            return T2gTransactionResp.valueOfSuccess(transactionId, msgData);
        }catch (ManagedException me) {
            // 业务提示，发送给玩家错误提示
            // 。。。。
            return T2gTransactionResp.valueOfFail(transactionId);
        } catch (Exception e) {
            // 代码报错
            LOGGER.error(String.format("run transaction[%d: %s] error!", transactionId, baseT2GTransaction),e);
            return T2gTransactionResp.valueOfFail(transactionId);
        }
    }


    /**
     *  战斗服接收到游戏跨服事务执行结束
     * @param transferPlayer 跨服玩家信息
     * @param resp 返回
     */
    public void callback(TransferPlayer transferPlayer, T2gTransactionResp resp) {
        int transactionId = resp.getTransactionId();
        BaseT2GTransaction baseT2GTransaction = t2GTransactionMap.remove(transactionId);
        if (baseT2GTransaction == null) {
            LOGGER.error("not found t2gTransaction with transactionId {}", transactionId);
            return;
        }
        LOGGER.debug("transaction callback result {}, {}:{}", transactionId, resp.isSuccess(), baseT2GTransaction);

        if (resp.isSuccess()) {
            Object msg = null;
            MsgData returnMsgData = resp.getReturnMagData();
            if (returnMsgData != null) {
                msg = TransferMsgManager.self().parseMsg(returnMsgData);
            }
            baseT2GTransaction.successCallback(transferPlayer, msg);
        } else {
            baseT2GTransaction.failCallback(transferPlayer);
        }

    }


    /**
     *  获得在线玩家
     * @param playerId 玩家id
     * @return 跨服id
     */
    public TransferPlayer getOnlinePlayer(long playerId) {
        return null;
    }




}
