package io.github.gonefuture.profile.profileType.rpc;

import io.github.gonefuture.profile.*;

import java.util.Comparator;

/**
 * rpc x
 *
 * @author gonefuture
 * @version 2021/4/27 18:03
 */
public enum RPCProKey implements IProColumn {


    /**
     *  事务执行次数
     */
    excCount {
        @Override
        public IProStrategy getStrategy() {
            return ProStrategy.sum;
        }
    },


    /**
     *  事务响应平均时间
     */
    callbackAvg {
        @Override
        public IProStrategy getStrategy() {
            return ProStrategy.avg();
        }

        @Override
        public ColumnUnit getColumnUnit() {
            return ColumnUnit.NANO_TO_MS;
        }
    },

    /**
     *  事务响应最大时间
     */
    callbackMax {
        @Override
        public IProStrategy getStrategy() {
            return ProStrategy.max;
        }
        @Override
        public ColumnUnit getColumnUnit() {
            return ColumnUnit.NANO_TO_MS;
        }
    },
    /**
     *  事务响应超时次数
     */
    timeoutCount {
        @Override
        public IProStrategy getStrategy() {
            return ProStrategy.sum;
        }
    }

    ;

    /**
     *  编码性能输出第一种样式
     * @param profile 性能日志
     * @param <PK> 主健类型
     */
    public static <PK> ProPrinter<PK, RPCProKey> printStyle1(Profile<PK, RPCProKey> profile) {
        ProPrinter<PK, RPCProKey> printer = ProPrinter.valueOfDefaultPrintConverter(RPCProKey.values());
        printer
            // 按照执行次数排序
            .sort(Comparator.comparingLong(value -> value.getValue(RPCProKey.excCount)), true)
            // 任务名
            .addCustomColumn("transaction", pkrpcProKeyProRowInfo -> String.valueOf(pkrpcProKeyProRowInfo.getKey()));
        return printer;
    }
}
