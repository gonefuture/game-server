package io.github.gonefuture.text;

import java.io.PrintStream;

/**
 * 树形文本表格渲染器
 * @author gonefuture
 * @version 2021/4/29 12:27
 */
public class TextTreeTableRenderer extends TextTableRenderer{


    public TextTreeTableRenderer(TextTable textTable) {
        super(textTable);
    }

    public TextTreeTable getTextTreeTable() {
        return (TextTreeTable) tableModel;
    }

    @Override
    protected void printValue(PrintStream ps, int row, int col, boolean c) {
        boolean empty = false;
        if (row != 0 && col == getTextTreeTable().hierarchicalColumn && !textTable.hasSeparatorAt(row)) {
            empty = true;
        }
        super.printValue(ps, row, col, empty);
    }

}
