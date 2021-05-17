package io.github.gonefuture.profile;

import io.github.gonefuture.text.TextTable;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        return valueOfDefaultPrintConverter(columnClass.getEnumConstants());
    }

    /**
     *  创建一个默认字段显示的表格输出
     * @param columns 需要显示的列数组
     * @param <PK>  key类型
     * @param <Column> value类型
     * @return 默认显示方式的表格输出
     */
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
            vo.addColumn(columnName, column);
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

    /**
     *  清除已有显示信息
     */
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


    /***
     *  输出表格到指定位置
     * @param profile 性能文件
     * @param ps 输出流
     */
    public void printTable(Profile<PK, Column> profile, PrintStream ps) {
        List<ProRowInfo<PK, Column>> proRowInfos = profile.covert2List();
        // 处理过滤
        if (!filters.isEmpty()) {
            proRowInfos = proRowInfos.stream().filter(proRowInfo -> {
                for (Predicate<ProRowInfo<PK, Column>> filter : filters) {
                    if (filter.test(proRowInfo)) {
                        return false;
                    }
                }
                return true;
            }).collect(Collectors.toList());
        }

        // 处理排序
        if (comparator != null) {
            proRowInfos.sort(comparator);
        }
        // 构造数据
        int columnNum = columnInfos.size();
        String[] columnNames = new String[columnNum];
        Object[][] table = new Object[proRowInfos.size()][columnNum];
        for (int column = 0; column < columnNum; column++) {
            ColumnInfo columnInfo = columnInfos.get(column);
            // 列明
            columnNames[column] = columnInfo.name;
            // 行数据
            for (int row = 0; row < proRowInfos.size(); row++) {
                ProRowInfo<PK, Column> proRowInfo = proRowInfos.get(row);
                table[row][column] = columnInfo.column.apply(proRowInfo);
            }
        }
        // 输出
        TextTable textTable = new TextTable(columnNames, table);
        textTable.printTable(ps, 0);
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
