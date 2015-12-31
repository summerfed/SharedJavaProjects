package com.fed.dev.report;

/**
 * The Interface for all report classes included in this utility
 * @author fmanangan
 *
 */

public interface Report {
	
	public boolean hasData();
	
	public void setReportDescription(String description);
	
	/**
	 * Get Report String format
	 * @return
	 */
	public String getReportString();
	
	
	
	public boolean isColumnHeaderExist(String header);
	/**
	 * @param headers, case insensitive
	 * @return boolean
	 */
	public boolean setColumnHeaders(String... headers);

	/**
	 * @param columnName, case-insensitive
	 * @param rowValue
	 * @return false if unsuccessful
	 */
	public boolean addRow(String columnName, String rowValue);
	
	
	/**
	 * @param fileLocation
	 * @return
	 */
	public boolean writeReport();
	
}
