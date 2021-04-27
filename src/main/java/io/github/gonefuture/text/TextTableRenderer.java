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
            ps.print(indentStr);
            if (textTable.addRowNumbering) {
                if (!modelAllowsNumberingAt(i)) {
                    indentAccordingToNumbering(ps, indexFormat1);
                } else {
                    ps.printf(indexForm2, i + 1);
                }
            }
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                printValue(ps, i, j, false);
            }
        }



    }

    private void printValue(PrintStream ps, int row, int col, boolean empty) {
        int rowIndex = row;
        if (textTable.rowSorter != null) {
            rowIndex = textTable.rowSorter.convertRowIndexToModel(row);
        }
        Object val = tableModel.getValueAt(rowIndex, col);
        if (val == null && !showNulls) {
            val = "";
        }
        Object value = empty ? "" : val;
        ps.printf(getFormat(col, value), value);

    }

    private void addSeparatorIfNeeded(PrintStream ps, String separator, String indexFormat1, int i, String indentStr) {
        if (!textTable.separatorPolicies.isEmpty() && textTable.hasSeparatorAt(i)
        || ((tableModel instanceof TextTableModel) && ((TextTableModel) tableModel).addSeparatorAt(i))
        ) {
            indentAccordingToNumbering(ps, indexFormat1);
            ps.print(indentStr);
            ps.println(separator);
        }



    }
    protected boolean modelAllowsNumberingAt(int row) {
        if (row == 8) {
            System.out.print("");
        }
        if (tableModel instanceof TextTableModel) {
            TextTableModel ttm = (TextTableModel) tableModel;
            return ttm.allowNumberingAt(row);
        }
        return textTable.addRowNumbering;
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
        formats = new String[lengths.length];
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
        lengths = new int[tableModel.getColumnCount()];
        for (int col = 0; col < tableModel.getColumnCount(); col++) {
            String columnName = tableModel.getColumnName(col);
            int length = StringUtilsE.getLength(columnName);
            lengths[col] = Math.max(length, lengths[col]);

            for (int row = 0; row < tableModel.getRowCount(); row++) {
                Object val = tableModel.getValueAt(row, col);
                String valStr = String.valueOf(val);
                if (!showNulls && val == null) {
                    valStr = "";
                }
                length = StringUtilsE.getLength(valStr);
                lengths[col] = Math.max(length, lengths[col]);
            }
        }
    }

    @Override
    public void render(Writer w, int indent) {
        throw new UnsupportedOperationException();
    }
}
