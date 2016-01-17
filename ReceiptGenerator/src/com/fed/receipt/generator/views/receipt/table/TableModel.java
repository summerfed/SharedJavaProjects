package com.fed.receipt.generator.views.receipt.table;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.fed.receipt.generator.obj.Product;
import com.fed.receipt.generator.views.receiptgenerator.SharedData;


public class TableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1446583813673830241L;
	private String[] columns = {"Qty", "Unit", "Description", "Unit Price", "Amount"};
	private TableData data;
	private Class<?>[] columnClasses = TableConstants.COLUMN_CLASSES;
	
	public TableModel(TableData data) {
		this.data = data;
		SharedData.setTableModel(this);
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}
	
	public String getColumnName(int col) {
		return columns[col];
	}

	@Override
	public int getRowCount() {
		return data.size();
	}
	
	
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(0 == columnIndex) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClasses[columnIndex];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Product currentRow = data.get(row);
		switch(col) {
			case 0: return currentRow.getQty();
			case 1: return currentRow.getUnit();
			case 2: return currentRow.getDescription();
			case 3: return currentRow.getPrice();
			case 4: return currentRow.getTotalAmount();
		}
		
		return "";
	}
	
	@Override
	public void setValueAt(Object updatedValue, int row, int col) {
		int qty = (int) updatedValue;
		data.get(row).setQty(qty);
		fireTableDataChanged();
	}
	
	private int toInteger(Object object) {
		if(!(object.toString().equalsIgnoreCase("null") && object!=null)) {
			if(object instanceof String) {
				return Integer.parseInt(object.toString());
			} else if(object instanceof Number) {
				return (int) object;
			}
		} 
		return 0;

	}
}
