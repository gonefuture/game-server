package io.github.gonefuture.text;

import java.util.List;
import java.util.Map;

/**
 * map类型的文本表单
 *
 * @author gonefuture
 * @version 2021/4/27 15:20
 */
public class MapBasedTableModel extends TextTableModel{

    private List<String> columnNames;
    private List<Map<Object, Object>> mapList;


    public MapBasedTableModel(List<Map<Object, Object>> mapList) {
        this.mapList = mapList;
    }

    @Override
    public boolean allowNumberingAt(int row) {
        return false;
    }

    @Override
    public boolean addSeparatorAt(int row) {
        return false;
    }

    @Override
    public int getRowCount() {
        return mapList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Map<Object,Object> m = mapList.get(rowIndex);
        String columnName = columnNames.get(columnIndex);
        return m.get(columnName);
    }
}
