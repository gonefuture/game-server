package io.github.gonefuture.text;

import io.github.gonefuture.utility.StringUtilsE;
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
        String indexFormat1 = "%1$-" + rowCountStrSize + "s ";
        String indexForm2 = "%1$" + rowCountStrSize + "s.";
        // Generate a format string for each column and calc totalLength
        int totalLength = resolveFormats();

        String headerStartSep = StringUtils.repeat("-", totalLength + tableModel.getColumnCount() * 2);
        ps.print(indentStr);
        indentAccordingToNumbering(ps, indexFormat1);
        for ( int i =0; i < tableModel.getColumnCount(); i++) {
            String columnName = tableModel.getColumnName(i);
            ps.printf(getFormat(i, columnName), columnName);
        }

        indentAccordingToNumbering(ps, indexFormat1);
        String headerSep = StringUtils.repeat("=", totalLength + tableModel.getColumnCount() * 2 - 1);
        ps.print("|");
        ps.print(headerSep);
        ps.println("|");

        // Print ' em out
        for (int i=0; i < tableModel.getRowCount(); i++) {
            addSeparatorIfNeeded(ps, separator, indexFormat1, i, indentStr);
        }



    }

    private void addSeparatorIfNeeded(PrintStream ps, String separator, String indexFormat1, int i, String indentStr) {
        if (!textTable.separatorPolicies.isEmpty() && textTable.) {
        }


    }

    /**
     *  格式
     * @param cal 格子序号
     * @param o 对象
     * @return 字符串
     */
    private String getFormat(int cal, Object o) {
        int length = lengths[cal];
        StringBuilder sb = new StringBuilder();
        if (cal == 0) {
            sb.append("|");
        }
        sb.append(" %1$-");
        int offset = 0;
        if (o != null) {
            String input = o.toString();
            offset = StringUtilsE.getLength(input) - input.length();
        }
        sb.append(length - offset);
        sb.append("s|");
        sb.append(cal + 1 == lengths.length ? "\n" : "");
        return sb.toString();
    }

    private void indentAccordingToNumbering(PrintStream ps, String indexFormat1) {
        if (textTable.addRowNumbering) {
            ps.printf(indexFormat1, "");
        }
    }

    /**
     *  处理格式
     * @return 总长度
     */
    private int resolveFormats() {
        int totalLength = 0;
        for (int i = 0; i < lengths.length; i++ ) {
            StringBuilder sb = new StringBuilder();
            if (i == 0) {
                sb.append("|");
            }
            sb.append(" %1$-");
            sb.append(lengths[i]);
            sb.append("s|");
            sb.append(i + 1 == lengths.length ? "\n" : "");
            formats[i] = sb.toString();
            totalLength += lengths[i];
        }
        return totalLength;
    }

    /**
     *  处理分隔间距
     * @param lengths 格子长度数组
     * @return 分隔字符串
     */
    private String resolveSeparator(int[] lengths) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            if (i == 0) {
                sb.append("|");
            }
            lengths[i] = Math.max(tableModel.getColumnName(i).length(), lengths[i]);
            // add 1 because of the leading space in each column
            sb.append(StringUtils.repeat("-", lengths[i] + 1));
            sb.append("|");
        }
        return sb.toString();
    }

    private void resolveColumnLengths() {
    }

    @Override
    public void render(Writer w, int indent) {
        throw new UnsupportedOperationException();
    }
}
