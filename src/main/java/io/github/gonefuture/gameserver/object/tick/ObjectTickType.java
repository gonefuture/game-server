package io.github.gonefuture.gameserver.object.tick;

/**
 * 对象心跳类型
 *
 * @author gonefuture
 * @version 2021/03/12 12:21
 */
public enum  ObjectTickType {

    /**
     *  定时回复血量蓝量
     */
    REGEN_HP_MP,

    /**
     * 效果tick
     */
    EFFECT_CONTROLLER,

    /**
     *  驱动AI
     */
    AI,

    /**
     *  AI事件消息处理
     */
    AI_EVENT_MESSAGE,

    /**
     *  检测个人掉落超时删除
     */
    PERSONAL_DROP_CHECK,

    /**
     *  Npc攻击心跳
     */
    NPC_ATTACK,


    ;


    ;
}
