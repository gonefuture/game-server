package io.github.gonefuture.profile;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * 遗憾性能记录信息
 *
 * @author gonefuture
 * @version 2021/04/13 12:05
 */
public class ProRowInfo<PK, Column extends Enum<Column> & IProColumn> {

    private final PK key;

    /**
     *  数据
     */
    private final AtomicLongArray columnValues;
    private final IProStrategy[] strategies;



    ProRowInfo(PK key, Column[] columns) {
        this.key = key;
        this.columnValues = new AtomicLongArray(columns.length);
        this.strategies = new IProStrategy[columns.length];
        for (int i = 0; i < columns.length; i++) {
            strategies[i] = columns[i].getStrategy();
        }
    }

    /**
     *  记录一次数据
     * @param proRow 行信息
     */
    public void record(ProRow<PK, Column> proRow) {
        // 增记录次数
        long[] row = proRow.getRow();
        for (int i=0; i < row.length; i++) {
            long newValue = row[i];
            IProStrategy strategy = strategies[i];
            columnValues.getAndUpdate(i, operand -> strategy.reAdd(operand, newValue));
        }
    }

    /**
     *  获取字段统计数据
     * @param column 字段
     * @return 如果无统计信息，返回0
     */
    public long getValue(Column column) {
        return columnValues.get(column.ordinal());
    }


    public PK getKey() {
        return key;
    }


}
