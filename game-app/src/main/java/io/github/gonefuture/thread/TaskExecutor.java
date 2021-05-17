package io.github.gonefuture.thread;

import io.netty.util.concurrent.FastThreadLocal;
import org.apache.commons.lang.Validate;

/**
 * 带名字参数的执行器
 *
 * @author gonefuture
 * @version 2021/04/16 10:58
 */
public interface TaskExecutor {
    FastThreadLocal<TaskExecutor> TASK_EXECUTOR_LOCAL = new FastThreadLocal<>();

    /**
     *  绑定当前线程 的任务执行器
     *  不推荐业务手动绑定，最好是统一管理线程进行绑定，业务动态绑定容易内存泄漏
     * @param executor  任务执行器
     */
    static void bindCurThreadExecutor(TaskExecutor executor) {
        if (TASK_EXECUTOR_LOCAL.isSet()) {
            throw new RuntimeException("repeated bindCurThreadExecutor!");
        }
        TASK_EXECUTOR_LOCAL.set(executor);
    }


    /**
     * 尝试绑定当前线程的任务执行器，如果已经有了，就不绑定了
     * @param executor 任务执行器
     * @return false表示已经有执行器了
     */
    static boolean tryBindCurThreadExecutor(TaskExecutor executor) {
        if (TASK_EXECUTOR_LOCAL.isSet()) {
            return false;
        }
        TASK_EXECUTOR_LOCAL.set(executor);
        return true;
    }

    /**
     *  直接当前线程
     */
    TaskExecutor CUR_THREAD = (taskName, command) -> command.run();


    /**
     *  执行具体逻辑
     * @param taskName 任务名字
     * @param command 逻辑体
     */
    void execute(String taskName, Runnable command);

    default void checkNotInCurrentExecutorThrowException() {
        Validate.isTrue(this == TASK_EXECUTOR_LOCAL.get(), this + " not is " + TASK_EXECUTOR_LOCAL.get());
    }

    /**
     *  检测是否位当前线程
     */
    default boolean isInExecutor() {
        throw new IllegalStateException(this + "not support isInExecutor() !");
    }


}
