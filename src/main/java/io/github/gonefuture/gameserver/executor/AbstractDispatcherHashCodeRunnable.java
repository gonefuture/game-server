package io.github.gonefuture.gameserver.executor;

import java.util.concurrent.TimeUnit;

/**
 * 任务分发标记器
 *
 * @author gonefuture
 * @version 2021/03/12 17:51
 */
public abstract class AbstractDispatcherHashCodeRunnable implements Runnable{

    /**
     *  是否为定时任务
     */
    private boolean scheduleTask;
    /**
     *  线程标记
     */
    private int threadIndex;

    /**
     *  提交时间
     */
    private long submitTime;

    /**
     *  用于分发的编号
     */
    abstract int getDispatcherHashCode();

    /**
     *  任务类型，同一种类型任务添加一种任务即可
     */
    abstract String name();

    /**
     *  执行的任务
     */
    abstract public void doRun();


    @Override
    public void run() {

    }

    /**
     *  提交任务
     * @param threadIndex 线程标记
     * @param scheduleTask 是否是定时任务
     */
   public void sumbit(int threadIndex, boolean scheduleTask) {
       this.threadIndex = threadIndex;
       this.scheduleTask = scheduleTask;
       this.submitTime = System.nanoTime();
   }


}
