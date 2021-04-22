package io.github.gonefuture.text;

import org.apache.commons.lang3.StringUtils;

import javax.swing.table.TableModel;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;

public class TextTableRenderer  implements TableRenderer{

    protected String[] formats;
    protected int[] lengths;
    protected TextTable textTable;
    protected TableModel tableModel;

    private boolean showNulls = false;


    public TextTableRenderer(TextTable textTable) {
        this.textTable = textTable;
        this.tableModel = textTable.tableModel;
    }


    public TextTableRenderer(TextTable textTable, boolean showNulls) {
        this.textTable = textTable;
        this.tableModel = textTable.tableModel;
        this.showNulls = showNulls;
    }

    @Override
    public void render(OutputStream os, int indent) {
        PrintStream ps;
        if (os instanceof PrintStream) {
            ps = (PrintStream) os;
        } else {
            ps = new PrintStream(os);
        }
        TableModel tableModel = textTable.getTableModel();
        String indentStr = StringUtils.repeat(" " ,indent);

        // 找到每个行最大的字符串长度
        resolveColumnLengths();
        String separator = resolveSeparator(lengths);

        int rowCount = tableModel.getRowCount();
        int rowCountStrSize = Integer.toString(rowCount).length();
        String indexFormat1 = "%1$-"


    }

    private String resolveSeparator(int[] lengths) {
    }

    private void resolveColumnLengths() {
    }

    @Override
    public void render(Writer w, int indent) {
        throw new UnsupportedOperationException();
    }
}
