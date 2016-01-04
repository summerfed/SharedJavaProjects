package com.svi.bpo.server.util.reports;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

public class WoorkBookFormat {

	private int maxColumn;
	private int starttRow;
	private int currentRow;
	private String password;
	private String title;
	
	private Sheet sheet;
	
	public WoorkBookFormat(Workbook workBook, String title) {
		currentRow = starttRow;
		this.title = title;
		sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName(this.title));	
	}
	
	public int getMaxColumn() {
		return maxColumn;
	}

	public void setMaxColumn(int maxColumn) {
		this.maxColumn = maxColumn;
	}

	public int getStarttRow() {
		return starttRow;
	}

	public void setStarttRow(int starttRow) {
		this.starttRow = starttRow;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTitle() {
		return title;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public int getIncCrrntRow() {
		return ++currentRow;
	}
	
	public Row createRow(){
		return sheet.createRow(getIncCrrntRow());
	}
	
}
