package io.github.gonefuture.gameserver.object;

import io.netty.channel.ChannelFuture;

/**
 * 玩家桥接器：桥接本服玩家与跨服玩家
 *
 * @author gonefuture
 * @version 2021/04/09 16:50
 */
public interface IPlayerBridge {

    /**
     * 默认发包方式，会执行合包操作
     * @param packet 协议包
     * @return future
     */
    default ChannelFuture sendPacket(Object packet) {
        return sendPacket(packet, false);
    }


    /**
     *  精细发包方式，可以自定义是否精细合包，大部分逻辑推荐直接使用{@link #sendPacket(Object)}
     * @param packet 协议包
     * @param flushNow true立即发送，不执行合包操作(true状态只推荐及时性要求高的战斗相关业务使用)
     * @return  future
     */
    ChannelFuture sendPacket(Object packet, boolean flushNow);

}
