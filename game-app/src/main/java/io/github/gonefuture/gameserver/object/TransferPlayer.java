package io.github.gonefuture.gameserver.object;

import io.netty.channel.ChannelFuture;

/**
 * 跨服玩家数据:提交跨服事件，发送消息，发奖、存放跨服中的临时数据等
 *
 * @author gonefuture
 * @version 2021/03/29 16:19
 */
public class TransferPlayer implements IPlayerBridge{

    /**
     *  玩家id
     */
    private long playerId;


    public long getPlayerId() {
        return playerId;
    }

    @Override
    public ChannelFuture sendPacket(Object packet, boolean flushNow) {
        return null;
    }
}
