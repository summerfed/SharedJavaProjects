package com.fed.receipt.generator.views.receiptgenerator;

import java.math.BigDecimal;
import java.util.LinkedList;

import com.fed.receipt.generator.obj.Product;

public class TableData extends LinkedList<Product>{
	private BigDecimal totalAmount = new BigDecimal(0);
	public TableData() {
		SharedData.setTableData(this);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -5687363202948254578L;
	
	public void addTableRow(Product product) {
		add(product);
	}
	
	public void addTotalAmount(BigDecimal value) {
		totalAmount = totalAmount.add(value);
		SharedData.getTotalAmountLabel().setTotalAmount(totalAmount);
	}

}
