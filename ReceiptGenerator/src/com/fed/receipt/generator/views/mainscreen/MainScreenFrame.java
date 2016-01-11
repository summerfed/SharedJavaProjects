package com.fed.receipt.generator.views.mainscreen;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.fed.receipt.generator.views.receiptgenerator.ReceiptGeneratorFrame;

public class MainScreenFrame extends JFrame {
	private GridBagConstraints gbc = new GridBagConstraints();
	private static final long serialVersionUID = 3685483712029750739L;
	
	public MainScreenFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		Dimension dim = new Dimension();
		dim.setSize(500, 300);
		setSize(dim);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	    
	    
	    JButton tmp1 = new JButton("Add Product");
	    addToDisplay(gbc, 0, 1, this,tmp1);
	    JButton tmp2 = new JButton("Generate Receipt");
	    addToDisplay(gbc, 1, 1, this,tmp2);
	    
	    tmp2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
				ReceiptGeneratorFrame frame = new ReceiptGeneratorFrame();
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				frame.setVisible(true);
			}
		});
	    
		setVisible(true);
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
