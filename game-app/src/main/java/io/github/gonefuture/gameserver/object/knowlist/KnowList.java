package io.github.gonefuture.gameserver.object.knowlist;

import io.github.gonefuture.gameserver.object.AbstractVisibleObject;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 观察者列表
 *
 * @author gonefuture
 * @version 2021/03/12 11:15
 */
public class KnowList {

    /**
     *  视野拥有者
     */
    protected final AbstractVisibleObject owner;

    private final ConcurrentHashMap<Long, AbstractVisibleObject> konwObjects = new ConcurrentHashMap<>();


    public KnowList(AbstractVisibleObject owner) {
        this.owner = owner;
    }
}
