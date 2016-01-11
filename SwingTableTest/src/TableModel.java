import java.awt.Color;
import java.awt.Component;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;


public class TableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1446583813673830241L;
	private String[][] data;
	private String[] columns = {"Column1", "Column2", "Column3"};
	
	List<Color> rowColours = Arrays.asList(
	        Color.RED,
	        Color.GREEN,
	        Color.CYAN
	    );
	
	public void setRowColour(int row, Color c) {
        rowColours.set(row, c);
        fireTableRowsUpdated(row, row);
    }

    public Color getRowColour(int row) {
        return rowColours.get(row);
    }
	
	public TableModel(String[][] data) {
		this.data = data;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}
	
	public String getColumnName(int col) {
		return columns[col];
	}

	@Override
	public int getRowCount() {
		return data.length;
	}
	
	
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		this.setRowColour(1, Color.YELLOW);
		if(0 == columnIndex) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	@Override
	public Object getValueAt(int row, int col) {
		String tmp = data[row][col];
		if(tmp!=null) {
			return tmp;
		}
		return "";
	}
	
	static class Renderer extends DefaultTableCellRenderer{

		/**
		 * 
		 */
		private static final long serialVersionUID = -5606690746521300613L;
		
		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        TableModel model = (TableModel) table.getModel();
	        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        c.setBackground(model.getRowColour(row));
	        return c;
	    }

	}

}
