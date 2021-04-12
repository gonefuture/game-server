package io.github.gonefuture.gameserver.object.tick;

/**
 * 心跳接口
 *
 * @author gonefuture
 * @version 2021/03/12 12:36
 */
public interface Tick<T> {

    /**
     *  心跳业务
     * @param t 心跳对象
     */
    void tick(T t);
}
