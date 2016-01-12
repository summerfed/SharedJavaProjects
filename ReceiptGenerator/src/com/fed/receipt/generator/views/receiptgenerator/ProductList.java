package com.fed.receipt.generator.views.receiptgenerator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import com.fed.receipt.generator.db.ProductDatabase;
import com.fed.receipt.generator.obj.Product;


public class ProductList extends JList<String> {

	private static final long serialVersionUID = 5814704419281548124L;
	private JPopupMenu menu = new JPopupMenu();
	private int currentListIndex;
	
	public ProductList() {
		SharedData.setProductList(this);
		setVisibleRowCount(1);
		setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8294174740073402112L;
			List<String> descriptionList = ProductDatabase.getInstance().viewProducts();
			public int getSize() {
				return descriptionList.size();
			}
			public String getElementAt(int index) {
				return descriptionList.get(index);
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				 int index = SharedData.getProductList().locationToIndex(evt.getPoint());
				 currentListIndex = index;
				
			        if (evt.getClickCount() == 2) {
			            // Double-click detected
			            boolean status = SharedData.getTable().addRow(index, SharedData.getProductList());
			            if(!status) {
			            	StringBuilder msg = new StringBuilder();
			            	msg.append(SharedData.getProductList().getModel().getElementAt(index));
			            	msg.append(" is Already Added.");
			            	JOptionPane.showMessageDialog(SharedData.getCurrentFrame(), msg,"Invalid Operation", JOptionPane.ERROR_MESSAGE);
			            }
			        } else if(SwingUtilities.isRightMouseButton(evt)) {
			        	SharedData.getProductList().setSelectedIndex(index);
			        	showPopup(evt);
			        }
			}

		});
		
		setupPopupMenu();
	}

	private void showPopup(MouseEvent evt) {
		menu.show(evt.getComponent(), evt.getX(), evt.getY());
	}
	
	
	
	
	private void setupPopupMenu() {
		JMenuItem item = new JMenuItem("Add");
	    menu.add(item);
	  
	    item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 SharedData.getTable().addRow(currentListIndex, SharedData.getProductList());
			}
		});
	    
	    item = new JMenuItem("Edit");
	    menu.add(item);
	    
	    item = new JMenuItem("Delete");
	    menu.add(item);
	}

}
