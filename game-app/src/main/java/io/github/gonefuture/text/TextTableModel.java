package io.github.gonefuture.text;

import javax.swing.table.AbstractTableModel;

/**
 * 文本表单模型
 *
 * @author gonefuture
 * @version 2021/4/27 14:59
 */
public abstract class TextTableModel extends AbstractTableModel {


    private static final long serialVersionUID = 4676817828876324076L;


    public abstract boolean allowNumberingAt(int row);

    public abstract boolean addSeparatorAt(int row);
}
