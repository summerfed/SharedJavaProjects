package com.fed.receipt.generator.views.receiptgenerator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import com.fed.receipt.generator.db.ProductDatabase;
import com.fed.receipt.generator.obj.Product;


public class ProductList extends JList<String> {

	private static final long serialVersionUID = 5814704419281548124L;
	
	public ProductList() {
		SharedData.setProductList(this);
		setVisibleRowCount(1);
		setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8294174740073402112L;
			List<String> values = ProductDatabase.getInstance().viewProducts();
			public int getSize() {
				return values.size();
			}
			public String getElementAt(int index) {
				return values.get(index);
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				 JList<?> list = (JList<?>)evt.getSource();
			        if (evt.getClickCount() == 2) {
			            // Double-click detected
			            int index = list.locationToIndex(evt.getPoint());
			           	Object productDesc = list.getModel().getElementAt(index);
			           	Product product = ProductDatabase.getInstance().getProduct(productDesc.toString());
			           	SharedData.getTableData().add(product);
			           	SharedData.getTableModel().fireTableDataChanged();
			        }
			}
		});
	}

}
