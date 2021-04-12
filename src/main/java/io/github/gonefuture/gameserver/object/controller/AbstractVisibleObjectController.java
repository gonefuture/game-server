package io.github.gonefuture.gameserver.object.controller;

import io.github.gonefuture.gameserver.object.AbstractVisibleObject;

/**
 * 抽象可见物控制器
 *
 * @author gonefuture
 * @version 2021/03/12 10:57
 */
public class AbstractVisibleObjectController<T extends AbstractVisibleObject> {
    /**
     *  被控制器控制的生物
     */
    private T owner;

    /**
     *  设置生物
     * @param owner 控制拥有者
     */
    public void setOwner(T owner) {
        this.owner = owner;
    }

    /**
     *  获取控制器的对象
     * @return 该控制器的对象
     */
    public T getOwner() {
        return owner;
    }

    /**
     * 该控制器的对象看到其他可见对象时调用
     */
    public void see(AbstractVisibleObject object) {

    }

    /**
     *   该控制器的对象不再看到其他可见对象时调用
     */
    public void noteSee(AbstractVisibleObject object, boolean isOutOfRange) {

    }

    /**
     *
     */


}
