package io.github.gonefuture.profile.profileType.database;

import io.github.gonefuture.profile.*;

import java.util.Comparator;

/**
 * 数据库性能参数key
 *
 * @author gonefuture
 * @version 2021/04/15 11:53
 */
public enum DataBaseProKey implements IProColumn {

    /**
     *  提交储存的次数
     */
    submitCount{
        @Override
        public IProStrategy getStrategy() {
            return ProStrategy.sum;
        }
    };


    /**
     *  db储存性能信息输出第一种样式
     * @param profile 性能日志
     * @param <PK> 主健类型
     * @return
     */
    public static <PK> ProPrinter<PK, DataBaseProKey> printStyle1(Profile<PK, DataBaseProKey> profile) {
        ProPrinter<PK, DataBaseProKey> printer = ProPrinter.valueOfDefaultPrintConverter(DataBaseProKey.values());
        printer
                // 按照提交次数排序
                .sort(Comparator.comparing(value -> value.getValue(DataBaseProKey.submitCount)), true)
                // 任务名
                .addCustomColumn("entity", proRowInfo -> String.valueOf(proRowInfo.getKey()));
                return printer;
    }

}
