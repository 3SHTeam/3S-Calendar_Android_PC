package Calendar.AcceptReject;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Renderer extends DefaultTableCellRenderer {

    private BtnPane acceptRejectPane;

    public Renderer() {
        acceptRejectPane = new BtnPane();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            acceptRejectPane.setBackground(table.getSelectionBackground());
        } else {
            acceptRejectPane.setBackground(table.getBackground());
        }
        return acceptRejectPane;
    }
}