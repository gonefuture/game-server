package io.github.gonefuture.profile;

/**
 * 性能统计常用测量
 *
 * @author gonefuture
 * @version 2021/04/15 11:57
 */
public enum ProStrategy implements IProStrategy{

    /**
     *  求和
     */
    sum {
        @Override
        public long reAdd(long oldValue, long newValue) {
            return oldValue + newValue;
        }
    },
    max {
        @Override
        public long reAdd(long oldValue, long newValue) {
            return Math.max(oldValue, newValue);
        }
    },
    /**
     *  最小值
     */
    min {
        @Override
        public long reAdd(long oldValue, long newValue) {
            return Math.min(oldValue,newValue);
        }
    },

    ;

    /**
     *  平均值
     */
    public static IProStrategy avg() {
        long[] cuntAndSum = new long[2];
        return ((oldValue, newValue) -> {
            cuntAndSum[0]++;
            cuntAndSum[1] += newValue;
            // 这会带来一定程序的精度丢失
            return cuntAndSum[1] / cuntAndSum[0];
        });

    }

}
