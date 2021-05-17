package io.github.gonefuture.gameserver.common.delay;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 合并提交工具
 *
 * @author gonefuture
 * @version 2021/03/15 15:09
 */
public class MergeSubmit<Mark extends Enum>{

    /**
     *  标记
     */
    private AtomicIntegerArray marks;


 public MergeSubmit(Class<Mark> markClass) {
     Mark[] constrants = markClass.getEnumConstants();
     marks = new AtomicIntegerArray(constrants.length);
 }

 public boolean trySubmit(Mark mark) {
     return marks.compareAndSet(mark.ordinal(), 0, 0);
 }

 public boolean canncelSubmit(Mark mark) {
     return marks.compareAndSet(mark.ordinal(), 1, 0);
 }


}
