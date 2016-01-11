package com.fed.receipt.generator.views.receiptgenerator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

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
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
					frame.setVisible(true);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		ProductList productList = new ProductList();
		JScrollPane scrollPane = new JScrollPane(productList);
//		scrollPane.setViewportView(list);
		Dimension dim = new Dimension();
		dim.width = (int)(this.getWidth()*.2);
		dim.height = this.getHeight();
		scrollPane.setPreferredSize(dim);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.2;
		gbc.weighty = 0;
		add(scrollPane, gbc);
		
		TableData tableData = new TableData();
		TableModel tableModel = new TableModel(tableData);
		
		Table table = new Table(tableModel);
		
		JScrollPane tableScrollPane = new JScrollPane(table);
		addToDisplay(gbc, 1, 1, this,tableScrollPane);
		
		TotalAmountLabel total = new TotalAmountLabel();
		addToDisplay(gbc, 1, 2, this,total);
		
	}
	
	private void addToDisplay(GridBagConstraints gbc, int gridX, int gridY, JFrame frame, Component component) {
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		gbc.weightx = 0.8;
		gbc.weighty = 1;
		frame.add(component, gbc);
	}

}
