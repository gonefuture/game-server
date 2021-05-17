package io.github.gonefuture.rpc;

import io.github.gonefuture.thread.TaskExecutor;

/**
 * 默认使用当前线程绑的TaskExecutor
 *
 * @author gonefuture
 * @version 2021/4/29 15:32
 */
public interface RpcListener<T> {

    default TaskExecutor getTaskExecutor() {
        // todo
        return null;
    }

    /**
     *
     * @param ret 可能是异常，在IO线程执行
     *            <p> Eception or msg <p/>
     */
    default void onRet(Object ret) {
        try {
            if (ret instanceof  Throwable) {
                onError((Throwable) ret);
            } else {
                onSuc((T) ret);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            finalCallback();
        }
    }


    /**
     *  rpc执行成功
     */
    void  onSuc(T ret);

    /**
     *  rpc调用出现异常
     */
    default void onError(Throwable e) {
        // 打印错误日志
    }


    /**
     *  不管成功事变必然回调
     */
    default void  finalCallback() {
        // do nothing
    }


}
