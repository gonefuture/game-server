package io.github.gonefuture.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 性能记录信息
 *
 * @author gonefuture
 * @version 2021/04/12 16:19
 */
public class Profile<PK, Column extends Enum<Column> & IProColumn> {

    /**
     *  记录信息<数据key, 数据>
     */
    private final Map<PK, ProRowInfo<PK, Column>> rowMap = new ConcurrentHashMap<>();

    /**
     *  统计开始时间
     */
    private long startTime = System.currentTimeMillis();

    /**
     *  统计时长
     */
    private long totalTime;

    /**
     *  需要记录字段的enum字段
     */
    private final Column[] columns;

    public Profile(Class<Column> columnClass) {
        this(columnClass.getEnumConstants());
    }

    public Profile(Column[] columns) {
        this.columns = columns;
    }

    /**
     *  重置统计
     */
    public void reset() {
        this.rowMap.clear();
        this.startTime = System.currentTimeMillis();
        this.totalTime = 0;
    }


    /**
     *  获取或者创建一行记录数据
     *  <p>
     *   优先使用{@link BaseProfi}
     * @param pk
     * @return
     */
    public ProRowInfo<PK, Column> getOrCreateRowInfo(PK pk) {
        return rowMap.computeIfAbsent(pk, pk1 -> { new ProRowInfo<>(pk1, columns)});
    }

    /**
     *  获取统计持续时长
     *  如果在调用end之前调用该方法，则意味着统计还未结束，则使用当前时间作为统计结束时间用于计算时长
     *
     */
    public long getDuration(TimeUnit timeUnit) {
        return timeUnit.convert((this.totalTime == 0 ? System.currentTimeMillis() - this.startTime : this.totalTime) ,TimeUnit.MILLISECONDS);
    }


    /**
     * @return 性能统计开始时间
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     *  获取list结构的性能统计信息
     * @return list
     */
    public List<ProRowInfo<PK,Column>> covert2List() {
        return new ArrayList<>(rowMap.values());
    }


    /**
     * @return 获取当前统计的数据集合，注意线程安全
     */
    public Map<PK, ProRowInfo<PK, Column>> getRowMap() {
        return rowMap;
    }

    public Profile<PK, Column> collect(boolean reset) {
        Profile<PK, Column> clone = new Profile<>(columns);
        clone.rowMap.putAll(this.rowMap);
        clone.startTime = startTime;
        clone.totalTime = System.currentTimeMillis() - startTime;
        if (reset) {
            this.reset();
        }
        return clone;
    }

    /**
     *  返回行数
     */
    public int count() {
        return rowMap.size();
    }

}
