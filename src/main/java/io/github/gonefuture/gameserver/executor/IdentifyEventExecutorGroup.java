package io.github.gonefuture.gameserver.executor;

import com.alibaba.druid.util.DaemonThreadFactory;
import com.alibaba.druid.util.StringUtils;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 *  任务调度器
 */

public class IdentifyEventExecutorGroup {

    /**
     *  线程名前缀
     */
    private static final String EXECUTOR_NAME_PREFIX = "Identity-dispatcher";

    /**
     *  分发码，各线程独有
     */
    private static final ThreadLocal<Integer> dispatcherCode = new ThreadLocal<>();

    /**
     *  事件执行器组
     */
    static private EventExecutor[] childrens;

    synchronized public static void init(int nThreads) {
        if (childrens == null) {
            childrens = new EventExecutor[nThreads];
            ThreadFactory threadFactory = new DaemonThreadFactory(EXECUTOR_NAME_PREFIX);
            for (int i = 0; i < nThreads; i++) {
                EventExecutor eventExecutor = new EventExecutor(i+1, null, threadFactory, true);
                childrens[i] = eventExecutor;
                int dispatcherCode = i;
                addTask(i, "setDispatcherCode", () -> {
                    IdentifyEventExecutorGroup.dispatcherCode.set(dispatcherCode);
                });
            }
        }
    }

    public static Future<?> addTask(int dispatcherCode, String name, Runnable runnable) {
        checkName(name);
        return addTask(new AbstractDispatcherHashCodeRunnable() {
            @Override
            public void doRun() {
                runnable.run();
            }

            @Override
            int getDispatcherHashCode() {
                return dispatcherCode;
            }

            @Override
            String name() {
                return name;
            }
        });
    }

    public static Future<?> addTask(AbstractDispatcherHashCodeRunnable dispatcherHashCodeRunnable) {
        checkName(dispatcherHashCodeRunnable.name());
        EventExecutor eventExecutor = takeExecutor(dispatcherHashCodeRunnable.getDispatcherHashCode());
        dispatcherHashCodeRunnable.sumbit(eventExecutor.getIndex(), false);
        return eventExecutor.addTask(dispatcherHashCodeRunnable);
    }

    private static EventExecutor takeExecutor(int dispatcherHashCode) {
        return childrens[adjustDispatcherCode(dispatcherHashCode)];
    }

    private static int adjustDispatcherCode(int dispatcherHashCode) {
        return Math.abs(dispatcherHashCode % childrens.length);
    }


    public static Future<?> addTask(AbstractDispatcherHashCodeRunnable dispatcherHashCodeRunnable, int delay, TimeUnit timeUnit) {
        checkName(dispatcherHashCodeRunnable.name());
        EventExecutor eventExecutor = takeExecutor(dispatcherHashCodeRunnable.getDispatcherHashCode());
        dispatcherHashCodeRunnable.sumbit(eventExecutor.getIndex(), true);
        return eventExecutor.addScheduleTak(dispatcherHashCodeRunnable, delay, timeUnit);
    }

    public static Future<?> addTask(int dispatcherCode, String name, Runnable runnable, int delay, TimeUnit timeUnit) {
        checkName(name);
        return addTask(new AbstractDispatcherHashCodeRunnable() {
            @Override
            public void doRun() {
                runnable.run();
            }

            @Override
            int getDispatcherHashCode() {
                return dispatcherCode;
            }

            @Override
            String name() {
                return name;
            }
        }, delay, timeUnit);
    }

    /**
     * 检查名字是否为空
     */
    private static void checkName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("name is null!");
        }
    }

    /**
     * 添加定时器任务，该任务按照周期执行
     */
    public static ScheduledFuture<?> addScheduleAtFixedRate(int dispatcher, String name, long initialDelay, long period, TimeUnit unit, Runnable runnable) {
        return addScheduleAtFixedRate(new AbstractDispatcherHashCodeRunnable() {

            @Override
            int getDispatcherHashCode() {
                return dispatcher;
            }

            @Override
            String name() {
                return name;
            }

            @Override
            public void doRun() {
                runnable.run();
            }
        }, initialDelay, period, unit);
    }

    private static ScheduledFuture<?> addScheduleAtFixedRate(AbstractDispatcherHashCodeRunnable dispatcherHashCodeRunnable, long initialDelay, long period, TimeUnit unit) {
        checkName(dispatcherHashCodeRunnable.name());
        EventExecutor eventExecutor = takeExecutor(dispatcherHashCodeRunnable.getDispatcherHashCode());
        dispatcherHashCodeRunnable.sumbit(eventExecutor.getIndex(), true);
        return eventExecutor.addScheduleTakAtFixedRate(dispatcherHashCodeRunnable, initialDelay, period, unit);
    }


}
