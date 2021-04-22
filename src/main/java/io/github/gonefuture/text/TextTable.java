package io.github.gonefuture.text;


import com.sun.tools.javac.util.Name;

import javax.swing.*;
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


    public void  printTable(PrintStream ps, int indent) {

    }
}
