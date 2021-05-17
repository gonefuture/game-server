package io.github.gonefuture.gameserver.object;

import io.github.gonefuture.gameserver.common.delay.MergeSubmit;
import io.github.gonefuture.gameserver.object.controller.AbstractVisibleObjectController;
import io.github.gonefuture.gameserver.object.controller.FilterController;
import io.github.gonefuture.gameserver.object.knowlist.KnowList;
import io.github.gonefuture.gameserver.object.resource.SpawnResource;
import io.github.gonefuture.gameserver.object.tick.ObjectTick;
import io.github.gonefuture.gameserver.object.tick.ObjectTickType;
import io.github.gonefuture.gameserver.object.util.DirectionEnum;
import io.github.gonefuture.gameserver.object.update.CreatureUpdateType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author gonefuture  gonefuture@qq.com
 * @version 1.00
 * @since 2021/03/12 10:41
 * Description:  可见物
 */
public abstract class AbstractVisibleObject extends AbstractObject {

    /**
     *  观察者列表
     */
    private KnowList knownList;

    /**
     *  视野位面(决定视野可见，等于0表示可被所有位面可见)
     */
    private int planes;

    /**
     * 控制器
     */
    private AbstractVisibleObjectController controller;

    /**
     * 可见对象的目标
     */
    private AbstractVisibleObject target;

    /**
     *  出生配置
     */
    private SpawnResource spawnResource;

    private int bornX;
    private int bornY;
    private DirectionEnum bornHeading;

    /**
     *  该对象的心跳业务，主要用于防止忘记取消心跳，统一取消
     */
    private Map<ObjectTickType, ObjectTick<?>> tickMap = new ConcurrentHashMap<>(2);


    protected boolean birth;

    /**
     *  过滤器
     */
    private FilterController filterController;

    /**
     *  任务状态
     */
    private MergeSubmit<CreatureUpdateType> creatureUpdateTask;

    /**
     * 生物心跳是否已经暂停
     */
    private AtomicBoolean tickPause = new AtomicBoolean(true);




    /**
     *
     * @param objectId
     */




    public AbstractVisibleObject(long objectId) {
        super(objectId);
    }





    public int getDispatcherHashCode() {
        return this.hashCode();
    }
}
