package com.fed.receipt.generator.views.receiptgenerator;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.fed.receipt.generator.obj.Product;

public class TableData extends LinkedList<Product>{
	private Set<String> existingData = new HashSet<>();
	
	private BigDecimal totalAmount = new BigDecimal(0);
	public TableData() {
		SharedData.setTableData(this);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -5687363202948254578L;
	
	@Override
	public boolean add(Product e) {
		if(!existingData.contains(e.getDescription())) {
			existingData.add(e.getDescription());
			super.add(e);
			return true;
		} else {
			return false;
		}
		
	}
	public void addTotalAmount(BigDecimal value) {
		totalAmount = totalAmount.add(value);
		SharedData.getTotalAmountLabel().setTotalAmount(totalAmount);
	}

}
