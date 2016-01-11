package com.fed.receipt.generator.views.receiptgenerator;

public class SharedData {
	private static TableData tableData;
	private static ProductList productList;
	private static TableModel tableModel;
	private static Table table;
	private static TotalAmountLabel totalAmountLabel;

	public static TableData getTableData() {
		return tableData;
	}

	public static void setTableData(TableData tableData) {
		SharedData.tableData = tableData;
	}

	public static ProductList getProductList() {
		return productList;
	}

	public static void setProductList(ProductList productList) {
		SharedData.productList = productList;
	}

	public static TableModel getTableModel() {
		return tableModel;
	}

	public static void setTableModel(TableModel tableModel) {
		SharedData.tableModel = tableModel;
	}

	public static Table getTable() {
		return table;
	}

	public static void setTable(Table table) {
		SharedData.table = table;
	}

	public static TotalAmountLabel getTotalAmountLabel() {
		return totalAmountLabel;
	}

	public static void setTotalAmountLabel(TotalAmountLabel totalAmountLabel) {
		SharedData.totalAmountLabel = totalAmountLabel;
	}
}
