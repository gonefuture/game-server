package io.github.gonefuture.text;

import javax.swing.table.TableModel;
import java.io.PrintStream;

/**
 *  树形表格
 * @author gonefuture
 * @version 2021/4/29 14:36
 */
public class TextTreeTable extends TextTable{

    protected int hierarchicalColumn;

    public TextTreeTable(String[] columnNames, String[][] data) {
        super(columnNames, data);
        addSeparatorPolicy(new TreeTableSeparatorPolicy());
    }


    public TextTreeTable(TableModel tableModel, boolean addRowNumbering, int hierarchicalColumn) {
        super(tableModel, addRowNumbering);
        addSeparatorPolicy(new TreeTableSeparatorPolicy());
    }

    public TextTreeTable(TableModel tableModel) {
        super(tableModel);
        addSeparatorPolicy(new TreeTableSeparatorPolicy());
    }

    public void setHierarchicalColumn(int hierarchicalColumn) {
        this.hierarchicalColumn = hierarchicalColumn;
    }

    @Override
    public void printTable(PrintStream ps, int indent) {
        TextTableRenderer renderer = new TextTreeTableRenderer(this);
        renderer.render(ps, indent);
    }

    private class TreeTableSeparatorPolicy extends SeparatorPolicy {
        @Override
        boolean hasSeparator(int row) {
            if (row == 0) {
                return false;
            }
            Object rowAgo = getValueAt(row - 1, hierarchicalColumn);
            Object hierarchicalColumnVal = getValueAt(row, hierarchicalColumn);
            if (!hierarchicalColumnVal.equals(rowAgo)) {
                return true;
            }
            return false;
        }
    }
}
