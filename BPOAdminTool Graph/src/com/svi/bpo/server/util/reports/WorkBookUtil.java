package com.svi.bpo.server.util.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class WorkBookUtil {

	protected WoorkBookFormat format;
	protected Workbook workBook;
	protected CreationHelper createHelper;
	
	public WorkBookUtil(Workbook workBook){
		this.workBook = workBook;
		createHelper = workBook.getCreationHelper();
	}
	
	public void addSheet(WoorkBookFormat format){
		this.format = format;
	}

	public abstract void setStyle();
	public abstract void bldData(List<Object> records);
	
	protected Cell createCell(Row row, int column, Object content, CellStyle style){
		return createCell(row, column, content, style, false);
	}
	
	protected Cell createCell(Row row, int column, Object content, CellStyle style, boolean isNumber){

		Cell cell = row.createCell(column);
		if(style != null){
			cell.setCellStyle(style);
		}
		
		if (content instanceof Date) {
			cell.setCellValue((Date)content);
		} else if (isNumber || content instanceof Number) {
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			if(content instanceof Integer){
				cell.setCellValue((Integer)content);
			} else if (content instanceof Double) {
				cell.setCellValue(new BigDecimal((Double)content).doubleValue());
			} else if (content instanceof BigDecimal) {
				cell.setCellValue(new BigDecimal(content.toString()).doubleValue());
			} else {
				cell.setCellValue(content.toString());
			}
		} else {
			cell.setCellValue((String)content);
		}
		return cell;
	}
	
	public void closeFile(File outputDir){

		String password = format.getPassword();
		
		if(password != null && !password.isEmpty()){
			format.getSheet().protectSheet(password);
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outputDir);
			workBook.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
