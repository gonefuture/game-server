package io.github.gonefuture.gameserver.utility.obsever;

/**
 * 观察者生效方式
 *
 * @author gonefuture
 * @version 2021/03/24 12:42
 */
public enum ObserverInvalidType {

    /**
     *  从不失效
     */
    NEVER,

    /**
     *  运行一次后失效
     */
    ONE_OFF

    ;
}
