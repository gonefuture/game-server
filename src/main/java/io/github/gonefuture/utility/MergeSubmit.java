package io.github.gonefuture.utility;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * 提交合并工具
 * <p><p/>
 * 如果要更一步的修改，可以将按照bitset的做法，把int当成32个bit位来使用。
 * 唯一要注意的点是：这个工具关注的是某个具体bit位置的变更，而不是整理有任何变化。
 * 合并成一个int后可能会造成cas次数变多。
 *
 *
 * @author gonefuture
 * @version 2021/04/15 17:47
 */
public class MergeSubmit<Mark extends Enum<Mark>> {
    /**
     * 标记
     */
    private final AtomicIntegerArray marks;

    /**
     * @param markClass 标记枚举class
     */
    public MergeSubmit(Class<Mark> markClass) {
        Mark[] constants = markClass.getEnumConstants();
        this.marks = new AtomicIntegerArray(constants.length);
    }

    public boolean trySubmit(Mark mark) {
        return marks.compareAndSet(mark.ordinal(), 0, 1);
    }

    public boolean cancelSubmit(Mark mark) {
        return marks.compareAndSet(mark.ordinal(), 1, 0);
    }



}
