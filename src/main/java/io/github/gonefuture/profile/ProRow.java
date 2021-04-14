package io.github.gonefuture.profile;

/**
 * 性能单行数据临时记录
 *
 * @author gonefuture
 * @version 2021/04/13 12:19
 */
public class ProRow<PK, Column extends Enum<Column> & IProColumn> {

    /**
     *  单行详细信息
     */
    private final long[] row;


    /**
     *  数据key
     */
    private PK pk;

    private ProRowInfo<PK, Column> proRowInfo;

    public ProRow(int len) {
        this.row = new long[len];
    }

    public void reset(PK pk, ProRowInfo<PK, Column> proRowInfo) {
        this.pk = pk;
        this.proRowInfo = proRowInfo;
    }

    /**
     *  添加数据
     */
    public ProRow<PK, Column>  add(Column column, long value) {
        row[column.ordinal()] = column.getColumnUnit().transferValue(value);
        return this;
    }

    /**
     *  记录加 1
     * @param column 数据字段
     */
    public ProRow<PK, Column> add1(Column column) {
        return add(column, 1);
    }


    /**
     *  提交记录
     */
    public void submit() {
        proRowInfo.record(this);
    }

    public PK getPk() {
        return pk;
    }

    public long[] getRow() {
        return row;
    }

    public long getValue(Column column) {
        return row[column.ordinal()];
    }








}
