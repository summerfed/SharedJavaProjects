package com.fed.receipt.generator.views.receiptgenerator;

import java.math.BigDecimal;

import javax.swing.JLabel;

public class TotalAmountLabel extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1518401222037379775L;

	public TotalAmountLabel() {
		this.setText("Total Amount: " + 0);
		SharedData.setTotalAmountLabel(this);
	}
	
	public void setTotalAmount(BigDecimal totalAmount) {
		this.setText("Total Amount: " + totalAmount);
	}
	
	
}
