package io.github.gonefuture.gameserver.executor;

import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.SingleThreadEventExecutor;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class EventExecutor extends SingleThreadEventExecutor {

    /**
     *  执行器的序号
     */
    private int index;

    /**
     *
     * @param index 传入的标记序号
     */
    protected EventExecutor(int index, EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
        super(parent, threadFactory, addTaskWakesUp);
        this.index = index;
    }

    @Override
    protected void run() {
        do {
            Runnable task = takeTask();
            if (task != null) {
                task.run();
                updateLastExecutionTime();
            }
        } while (!confirmShutdown());
    }


    public Future<?> addTask(AbstractDispatcherHashCodeRunnable task) {
        return submit(task);
    }

    /**
     *  开始定时任务
     * @param dispatcherHashCodeRunnable 任务
     * @param initialDelay 延迟时间
     * @param period 周期
     * @param unit 时间单位
     * @return 任务句柄
     */
    public ScheduledFuture<?> addScheduleTakAtFixedRate(AbstractDispatcherHashCodeRunnable dispatcherHashCodeRunnable, long initialDelay, long period, TimeUnit unit) {
        return scheduleAtFixedRate(dispatcherHashCodeRunnable, initialDelay, period, unit);
    }

    public int getIndex() {
        return index;
    }

    public Future<?> addScheduleTak(AbstractDispatcherHashCodeRunnable task, int delay, TimeUnit timeUnit) {
        return schedule(task, delay, timeUnit);
    }


}
