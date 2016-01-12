package com.fed.receipt.generator.views.receiptgenerator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class ReceiptGeneratorFrame extends JFrame {
	private static final long serialVersionUID = 1743239330855881603L;
	private GridBagConstraints gbc = new GridBagConstraints();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReceiptGeneratorFrame frame = new ReceiptGeneratorFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public ReceiptGeneratorFrame() {
		SharedData.setCurrentFrame(this);
		 for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            try {
						UIManager.setLookAndFeel(info.getClassName());
					} catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
		            break;
		        }
		    }
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		ProductList productList = new ProductList();
		JScrollPane scrollPane = new JScrollPane(productList);
//		scrollPane.setSize((int) (this.getWidth()*.1), this.getHeight());
//		scrollPane.setViewportView(list);
		Dimension dim = new Dimension();
		dim.width = 100;
		dim.height = 100;
		scrollPane.setPreferredSize(dim);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.2;
		gbc.weighty = 0;
		add(scrollPane, gbc);
		
		JButton addButton = new JButton("Add to Receipt");
		JButton editButton = new JButton("Edit");
		JButton deleteButton = new JButton("Delete");
		JButton unselectItems = new JButton("Unselect");
		
		addProductEvent(addButton);
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(addButton);
		panel.add(editButton);
		panel.add(deleteButton);
		panel.add(unselectItems);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0.1;
		gbc.weighty = 1;
		this.add(panel, gbc);
		
		TableData tableData = new TableData();
		TableModel tableModel = new TableModel(tableData);
		
		Table table = new Table(tableModel);
		
		JScrollPane tableScrollPane = new JScrollPane(table);
		addToDisplay(gbc, 1, 1, this,tableScrollPane);
		
		TotalAmountLabel total = new TotalAmountLabel();
		addToDisplay(gbc, 1, 2, this,total);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setVisible(true);
	}

	private void addProductEvent(JButton addButton) {
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectedItems = SharedData.getProductList().getSelectedIndices();
				for(int each:selectedItems) {
					 SharedData.getTable().addRow(each, SharedData.getProductList());
				}
			}
		});
	}
	
	private void addToDisplay(GridBagConstraints gbc, int gridX, int gridY, JFrame frame, Component component) {
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		gbc.weightx = 0.8;
		gbc.weighty = 1;
		frame.add(component, gbc);
	}

}
