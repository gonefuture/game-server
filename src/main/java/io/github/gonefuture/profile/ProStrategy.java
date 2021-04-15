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
    }
    ;

}
