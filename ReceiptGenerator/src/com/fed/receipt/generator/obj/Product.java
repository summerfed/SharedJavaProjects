package com.fed.receipt.generator.obj;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fed.receipt.generator.views.receiptgenerator.SharedData;

public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2547670591691035042L;
	private String description = "";
	private String unit = "";
	private BigDecimal price = new BigDecimal(0);
	private int qty = 0;
	private BigDecimal totalAmount = price.multiply(new BigDecimal(qty));
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
		totalAmount = price.multiply(new BigDecimal(qty));
		SharedData.getTableData().addTotalAmount(totalAmount);
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
}
