package io.github.gonefuture.profile;

import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 性能信息输出工具
 *
 * @author gonefuture
 * @version 2021/04/14 10:24
 */
public class ProPrinter<PK, Column extends Enum<Column> & IProColumn> {

    private ArrayList<ColumnInfo> columnInfos;
    private Comparator<ProRowInfo<PK, Column>> comparator;
    private List<Predicate<ProRowInfo<PK, Column>>> filters;


    public ProPrinter() {

    }

    public static <PK, Column extends Enum<Column> & IProColumn> ProPrinter<PK, Column> valueOf() {
        ProPrinter<PK, Column> vo = new ProPrinter<>();
        vo.columnInfos = new ArrayList<>();
        vo.filters = new ArrayList<>(3);
        return vo;
    }


    public static <PK, Column extends Enum<Column> & IProColumn> ProPrinter<PK, Column> valueOfDefaultPrintConverter(Class<Column> columnClass) {
        return valueOfDefaultPrintConverter(columnClass.getEnumConstants())
    }

    public static <PK, Column extends Enum<Column> & IProColumn> ProPrinter<PK, Column> valueOfDefaultPrintConverter(Column[] columns) {
        ProPrinter<PK, Column> vo = new ProPrinter<>();
        vo.columnInfos = new ArrayList<>();
        for (Column column : columns) {
            String columnName;
            String unitName = column.getColumnUnit().getUnitName();
            if (StringUtils.isNotEmpty(unitName)) {
                columnName = column.name() + "(" + unitName + ")";
            } else {
                columnName = column.name();
            }
            vo.
        }
        vo.filters =  new ArrayList<>(3);
        return vo;
    }

     /**
      * 添加显示列，采用默认toString处理值
     * @param name 列名
     * @param column 列
     */
    public ProPrinter<PK, Column> addColumn(String name, Column column) {
        return addCustomColumn(name, proRowInfo -> {
            long value = proRowInfo.getValue(column);
            return column.getColumnUnit().convertValue(value);
        });
    }

    /**
     * 添加显示列，自定义值转换字符串的方式
     * @param name 列名
     * @param column 列字段获取器
     */
    public ProPrinter<PK, Column> addCustomColumn(String name, Function<ProRowInfo<PK, Column>, String> column) {
        ColumnInfo columnInfo = new ColumnInfo(name, column);
        columnInfos.add(columnInfo);
        return this;
    }

    public ProPrinter<PK, Column> clear() {
        this.columnInfos.clear();
        return this;
    }

    public ProPrinter<PK, Column> removeColumn(Column column) {
        columnInfos.removeIf( columnInfo -> columnInfo.column == column);
        return this;
    }


    public ProPrinter<PK, Column> sort(Comparator<ProRowInfo<PK, Column>> comparator, boolean reversed) {
        if (reversed) {
            this.comparator = comparator.reversed();
        } else {
            this.comparator = comparator;
        }
        return this;
    }


    public void printTable(Profile<PK, Column> profile, PrintStream ps) {
        List<ProRowInfo<PK, Column>> proRowInfos = profile.covert2List();
    }



    private class ColumnInfo {
        /**
         *  列名
         */
        private String name;

        /**
         *  列数据获取器
         */
        private Function<ProRowInfo<PK, Column>, String> column;

        public ColumnInfo(String name, Function<ProRowInfo<PK, Column>, String> column) {
            this.name = name;
            this.column = column;
        }
    }
}
