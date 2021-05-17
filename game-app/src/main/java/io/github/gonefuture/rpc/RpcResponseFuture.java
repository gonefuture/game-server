package io.github.gonefuture.rpc;

import java.util.concurrent.TimeUnit;

/**
 * @author gonefuture
 * @version 2021/4/29 15:30
 */
public interface RpcResponseFuture<T> {

    long getRequestID();

    void addListener();


    /**
     *  同步等待数据返回
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return 数据
     * @throws Exception 超时等待异常
     */
    T get(long timeout, TimeUnit unit) throws Exception;

}
