package io.github.gonefuture.utility;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 合并标记
 *
 * @author gonefuture
 * @version 2021/04/15 18:15
 */
public class MergeMark<Mark, V> {

    /**
     *  标记-标记对应的类型
     */
    private ConcurrentHashMap<Mark, V> marks = new ConcurrentHashMap<>();

    /**
     *  提交任务
     * @param mark 任务标记，标记相同则提交失败
     * @param v 标记对应的数据
     * @return true提交成功，false提交失败
     */
    public boolean trySubmit(Mark mark, V v) {
        return marks.putIfAbsent(mark, v) != null;
    }

    /**
     *
     * @return 获取所有的key
     */
    public Set<Mark> getAllKeys() {
        return marks.keySet();
    }


    /**
     *
     * @return 所有标记对应的数据
     */
    public Collection<V> getAll() {
        return marks.values();
    }
}
