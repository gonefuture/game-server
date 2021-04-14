package io.github.gonefuture.profile;

/**
 * 性能记录 列
 *
 * @author gonefuture
 * @version 2021/04/12 16:35
 */
public interface IProColumn {

    /**
     *  获取列 数值单位 和对应的输出方式
     */
    default ColumnUnit getColumnUnit() {
        return ColumnUnit.SIMPLE;
    }

    /**
     *  获取性能信息记录策略
     *
     * @return 可以直接实现IProStrategy,也可以使用自己定义的proStrategy
     */
    IProStrategy getStrategy();



}
