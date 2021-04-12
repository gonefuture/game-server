package io.github.gonefuture.gameserver.transaction;

import io.github.gonefuture.gameserver.object.Player;
import io.github.gonefuture.gameserver.object.TransferPlayer;
import io.github.gonefuture.gameserver.exception.ManagedException;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 跨服事务基类， 战斗服
 *
 * @author gonefuture
 * @version 2021/03/29 16:12
 */
public abstract class BaseT2GTransaction<ReturnMsg> {
    /**
     *  事务所属玩家
     */
    private long playerId;

    /**
     *  事务开始时间
     */
    private long beginTime;

    /**
     *  事务已经被执行标记，用于解决超时检测与事务返回并发问题
     */
    private AtomicBoolean run = new AtomicBoolean(false);

    /**
     *   是否执行事务,一般里面会有先在跨服前执行的逻辑
     * @param transferPlayer 跨服玩家
     * @return 是否开始事务到游戏服执行，返回false,取消事务
     */
    public boolean isExecTransaction(TransferPlayer transferPlayer) {
        return true;
    }

    public void cancel(TransferPlayer transferPlayer) {

    }

    /**
     *  在游戏服执行
     * @param player 游戏服玩家信息
     * @return 返回的消息包，可以返回null,需要支持{@link ITransferMsg}
     * @throws ManagedException
     */
    public abstract ReturnMsg runInGameServer(Player player) throws ManagedException;


    public abstract void successCallback(TransferPlayer transferPlayer, ReturnMsg returnMsg);

    /**
     *  游戏服执行失败后的回调
     * @param transferPlayer 跨服玩家信息
     */
    public abstract void failCallback(TransferPlayer transferPlayer);

    /**
     *  事务执行超时，各种玩法自行考虑事务超时的处理逻辑
     *  超时有2钟可能
     *  1.玩家已经退出了跨服导致玩家持有的跨服链接已断开，游戏服无法回传结果，这种情况已无法获取到transferPlayer
     *  2.是游戏服报错导致结果无法回传
     *
     * @param playerId 玩家id
     */
    public abstract void timeout(long  playerId);

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public AtomicBoolean getRun() {
        return run;
    }

    public void setRun(AtomicBoolean run) {
        this.run = run;
    }

    @Override
    public String toString() {
       return getClass().getSimpleName();
    }
}
