package io.github.gonefuture.text;

import javax.swing.table.TableModel;

/**
 * 分割规则
 *
 * @author gonefuture
 * @version 2021/04/21 18:26
 */
public abstract class SeparatorPolicy {

    protected TableModel tableModel;

    public SeparatorPolicy() {

    }

    public SeparatorPolicy(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    abstract boolean hasSeparator(int row);
}
