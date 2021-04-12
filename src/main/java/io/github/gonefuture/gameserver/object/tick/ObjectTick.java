package io.github.gonefuture.gameserver.object.tick;

import io.github.gonefuture.gameserver.executor.IdentifyEventExecutorGroup;
import io.github.gonefuture.gameserver.object.AbstractVisibleObject;

import java.lang.ref.WeakReference;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  生物心跳定时器，该对象主要用于监控心跳对象是否已经被回收，
 *  如果已经被回收，就自动取消定时器，避免内存泄漏
 *
 * @author gonefuture
 * @version 2021/03/12 10:57
 */

public class ObjectTick<T extends AbstractVisibleObject> implements Runnable {

    /**
     *  心跳类型
     */
    private ObjectTickType tickType;

    /**
     *  心跳拥有者
     */
    private WeakReference<T> owner;

    /**
     *  定时任务
     */
    private ScheduledFuture<?> future;

    /**
     *  心跳业务
     */
    private Tick<T> tick;

    /**
     *  初始化延迟时间
     */
    private long initialDelay;

    /**
     *  持续时间
     */
    private long period;

    /**
     *  时间单位
     */
    private TimeUnit timeUnit;

    /**
     * 是否被暂停
     */
    private AtomicBoolean pause;

    /**
     *  是否被关闭
     */
    private AtomicBoolean close;
    private long delayOnPause;








    @Override
    public final void run() {
        T obj = owner.get();
        if (obj == null) {
            // 对象已经缺失,保底取消定时器
            tryCancel();
        } else {
            tick.tick(obj);
        }
    }

    /***
     *  取消定时器
     */
    public synchronized final boolean tryCancel() {
        boolean success = close.compareAndSet(false, true);
        if (success) {
            if (future != null) {
                future.cancel(false);
            }
        }
        return success;
    }

    /**
     *  尝试定时
     */
    private void trySchedule() {
        if (close.get()) {
            // 定时器已经关闭
            return;
        }
        if (pause.get()) {
            // 定时器已经暂停
            return;
        }
        T obj = owner.get();
        if (obj == null) {
            throw new RuntimeException("tick obj was recycled");
        }
        if (future != null && !future.isCancelled()) {
            return;
        }
        // 使用
        future = IdentifyEventExecutorGroup.addScheduleAtFixedRate(obj.getDispatcherHashCode(), tickType.name(),
                initialDelay, period, timeUnit, this);
    }
}
