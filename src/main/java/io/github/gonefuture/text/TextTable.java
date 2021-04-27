package io.github.gonefuture.text;


import com.sun.tools.javac.util.Name;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文本table
 *
 * @author gonefuture
 * @version 2021/04/21 18:22
 */
public class TextTable {

    protected TableModel tableModel;
    protected List<SeparatorPolicy> separatorPolicies = new ArrayList<>();

    protected boolean addRowNumbering;
    protected RowSorter<?> rowSorter;

    protected boolean headless;

    public TextTable(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    public TextTable(TableModel tableModel, boolean addRowNumbering) {
        this.tableModel = tableModel;
        this.addRowNumbering = addRowNumbering;
    }

    public TextTable(String[] columnNames, Object[][] data) {
        this.tableModel = new DefaultTableModel(data, columnNames);
    }


    public TableModel getTableModel() {
        return tableModel;
    }

    public void addSeparatorPolicy(SeparatorPolicy separatorPolicy) {
        separatorPolicies.add(separatorPolicy);
        separatorPolicy.setTableModel(tableModel);
    }

    public void setSort(int column) {
        setSort(column, SortOrder.ASCENDING);
    }

    public void setSort(int column, SortOrder sortOrder) {
        rowSorter = new TableRowSorter<>(this.tableModel);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(column, sortOrder));
        rowSorter.setSortKeys(sortKeys);
    }


    public void  printTable() {
        printTable(System.out, 0);
    }

    public void  printTable(PrintStream ps, int indent) {
        TextTableRenderer renderer = new TextTableRenderer(this);
        renderer.render(ps, indent);
    }


    /**
     *
     * @param row 行
     * @return 是否分隔
     */
    protected boolean hasSeparatorAt(int row) {
        for (SeparatorPolicy separatorPolicy : separatorPolicies) {
            if (separatorPolicy.hasSeparator(row)) {
                return true;
            }
        }
        return false;
    }
}
