package com.fed.receipt.generator.views.receiptgenerator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.fed.receipt.generator.db.ProductDatabase;
import com.fed.receipt.generator.obj.Product;

public class Table extends JTable {

	private static final long serialVersionUID = 7849321028555518173L;

	public Table(AbstractTableModel model) {
		super(model);
		SharedData.setTable(this);
		this.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
	}
	
	
	public Component prepareRenderer(
        TableCellRenderer renderer, int row, int column){
        Component c = super.prepareRenderer(renderer, row, column);
        if(column!=0) {
        	c.setBackground(Color.LIGHT_GRAY);
        } else {
        	c.setBackground(Color.WHITE);
        }
        return c;
    }
	
	public boolean addRow(int listIndex, final JList<?> list) {
		Object productDesc = list.getModel().getElementAt(listIndex);
		Product product = ProductDatabase.getInstance().getProduct(productDesc.toString());
		boolean status = SharedData.getTableData().add(product);
		if(status) {
			SharedData.getTableModel().fireTableDataChanged();
		} 
		return status;
	}
}
