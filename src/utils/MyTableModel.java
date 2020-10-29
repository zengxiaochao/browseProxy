package utils;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel
{
    // 禁止编辑表格
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }
}