package io.github.gonefuture.profile;

/**
 * 性能信息统计策略
 *
 * @author gonefuture
 * @version 2021/04/13 11:59
 */
@FunctionalInterface
public interface IProStrategy {

    /**
     *  处理新值与旧值
     * @param oldValue 统计记录中以及存在的值
     * @param newValue 新产生的值
     * @return 返回记录值
     */
    long reAdd(long oldValue, long newValue);

}
