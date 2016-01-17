package com.fed.receipt.generator.db.views;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.fed.receipt.generator.views.receiptgenerator.ReceiptGeneratorFrame;

public class AddProductFrame extends JFrame {

	private static final long serialVersionUID = 7983997438533627159L;
	
	/**
	 * Launch the application.
	 */
	public static void showView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new AddProductFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public AddProductFrame() {
		setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH); 
		pack();
		this.setVisible(true);
	}
	
}
