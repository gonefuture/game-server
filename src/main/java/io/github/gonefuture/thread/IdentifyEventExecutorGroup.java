package io.github.gonefuture.thread;

import com.alibaba.druid.util.DaemonThreadFactory;
import com.alibaba.druid.util.StringUtils;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.FastThreadLocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.*;

/**
 *  任务调度器
 */

public class IdentifyEventExecutorGroup implements DisposableBean, Executor {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentifyEventExecutorGroup.class);


    // 执行器，各线程独有
    private static FastThreadLocal<EventExecutor> executorFastThreadLocal = new FastThreadLocal<>();
    // 执行器组线程独有信息
    private static FastThreadLocal<IdentifyEventExecutorGroup> identifyEventExecutorGroupFastThreadLocal = new FastThreadLocal<>();


    private static FastThreadLocal<DefaultAttributeMap> env = new FastThreadLocal<>();


    // 事件执行器组
    private EventExecutor[] eventExecutors;

    /**
     *  线程池名字
     */
    private String poolName;

    /**
     *  初始化
     * @param poolName 线程池名
     * @param threadNum 线程数量
     */
    public IdentifyEventExecutorGroup(String poolName, int threadNum) {
       this.poolName = poolName;
       this.eventExecutors = new EventExecutor[threadNum];
       ThreadFactory threadFactory = new DefaultThreadFactory(poolName);
        for (int i = 0; i < threadNum; i++) {
            EventExecutor eventExecutor = new EventExecutor(i+1, null, threadFactory, true);
            eventExecutors[i] = eventExecutor;
            addTask(i, "setDispatcherCode", () -> {
                env.set(new DefaultAttributeMap());
                IdentifyEventExecutorGroup.executorFastThreadLocal.set(eventExecutor);
                IdentifyEventExecutorGroup.identifyEventExecutorGroupFastThreadLocal.set(this);
                // 绑定TaskExecutor
                TaskExecutor.bindCurThreadExecutor(eventExecutor);
            });
        }

    }


    public void shutdown() {
        for (EventExecutor c : eventExecutors) {
            c.shutdownGracefully();
        }
        LOGGER.warn("identityEventExecutorGroup[{}] closed!", poolName);
    }



    public  Future<?> addTask(int dispatcherCode, String name, Runnable runnable) {
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

    public  Future<?> addTask(AbstractDispatcherHashCodeRunnable dispatcherHashCodeRunnable) {
        checkName(dispatcherHashCodeRunnable.name());
        EventExecutor eventExecutor = takeExecutor(dispatcherHashCodeRunnable.getDispatcherHashCode());
        dispatcherHashCodeRunnable.sumbit(eventExecutor.getIndex(), false);
        return eventExecutor.addTask(dispatcherHashCodeRunnable);
    }

    private EventExecutor takeExecutor(int dispatcherHashCode) {
        return eventExecutors[adjustDispatcherCode(dispatcherHashCode)];
    }

    private  int adjustDispatcherCode(int dispatcherHashCode) {
        return Math.abs(dispatcherHashCode % eventExecutors.length);
    }


    public  Future<?> addTask(AbstractDispatcherHashCodeRunnable dispatcherHashCodeRunnable, int delay, TimeUnit timeUnit) {
        checkName(dispatcherHashCodeRunnable.name());
        EventExecutor eventExecutor = takeExecutor(dispatcherHashCodeRunnable.getDispatcherHashCode());
        dispatcherHashCodeRunnable.sumbit(eventExecutor.getIndex(), true);
        return eventExecutor.addScheduleTak(dispatcherHashCodeRunnable, delay, timeUnit);
    }

    public  Future<?> addTask(int dispatcherCode, String name, Runnable runnable, int delay, TimeUnit timeUnit) {
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
    public  ScheduledFuture<?> addScheduleAtFixedRate(int dispatcher, String name, long initialDelay, long period, TimeUnit unit, Runnable runnable) {
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

    /**
     * 添加定时器任务
     * 该任务按照周期执行
     */
    private  ScheduledFuture<?> addScheduleAtFixedRate(AbstractDispatcherHashCodeRunnable dispatcherHashCodeRunnable, long initialDelay, long period, TimeUnit unit) {
        checkName(dispatcherHashCodeRunnable.name());
        EventExecutor eventExecutor = takeExecutor(dispatcherHashCodeRunnable.getDispatcherHashCode());
        dispatcherHashCodeRunnable.sumbit(eventExecutor.getIndex(), true);
        return eventExecutor.addScheduleTakAtFixedRate(dispatcherHashCodeRunnable, initialDelay, period, unit);
    }


    @Override
    public void execute(Runnable command) {
    }

    @Override
    public void destroy() throws Exception {

    }
}
