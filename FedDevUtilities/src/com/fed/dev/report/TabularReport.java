package com.fed.dev.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This tool will produce tabular report based on provided data
 * SAMPLE CODE:
 ** <pre>
 *	//Location of log file
	File location = new File("/log/logFile1.log"); 
    Report report = new TabularReport(location);
    
    //Add Headers
	report.setColumnHeaders("Column1", "Column2");
	
	//Add Data
	report.addRow("Column1", "Data1");
	report.addRow("Column2", "Data2");
   
    //Write report log
    report.writeReport();
	* </pre>
 * 
 * @author fmanangan
 *
 */ 
public class TabularReport {
	private Queue<String> originalColumnHeaders;
	private Map<String, Queue<String>> data;
	private String description;
	
	public TabularReport() {
		data = new LinkedHashMap<>();
		originalColumnHeaders = new LinkedList<>();
	}
	
	/*public TabularReport(File fileLocation) {
		data = new LinkedHashMap<>();
		originalColumnHeaders = new LinkedList<>();
		
//		this.fileLocation.getParentFile().mkdirs();
		this.fileLocation = fileLocation;
		try {
			this.fileLocation.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	
	public boolean isColumnHeaderExist(String header) {
		boolean dataToReturn = false;
		if(data.containsKey(header)) {
			dataToReturn = true;
		}
		return dataToReturn;
	}

	public boolean setColumnHeaders(String... headers) {
		boolean dataToReturn = false;
		for(String each: headers) {
			originalColumnHeaders.add(each);
			String key = each.trim().toUpperCase();
			if(!data.containsKey(key)) {
				Queue<String> tmpValue = new LinkedList<>();
				data.put(key, tmpValue);
				if(!dataToReturn) {
					dataToReturn = true;
				}
			}
		}
		return dataToReturn;
	}

	public boolean addRow(String columnName, String rowValue) {
		boolean dataToReturn = false;
		String key = columnName.trim().toUpperCase();
		String tmpRowValue = rowValue.trim();
		if(data.containsKey(key)) {
			data.get(key).add(tmpRowValue);
			dataToReturn = true;
		} else {
			dataToReturn = false;
		}
		
		return dataToReturn;
	}

	public String getReportString() {
		StringBuilder dataToReturn = new StringBuilder();
		Date date = new Date();
		
		StringBuilder rowFormat = new StringBuilder();
		StringBuilder borderFormat = new StringBuilder();
		StringBuilder columnFormat = new StringBuilder();
		
		buildTableFormat(rowFormat, borderFormat, columnFormat);
			String timestamp = "Timestamp: " + new Timestamp(date.getTime()).toString()+"%n";
			dataToReturn.append(String.format(timestamp));
			if(description!=null) {
				dataToReturn.append(String.format("Description: " + description+"%n"));
			}
			dataToReturn.append(String.format(borderFormat.toString()));
			dataToReturn.append(String.format(columnFormat.toString()));
			dataToReturn.append(String.format(borderFormat.toString()));
			while(true) {
				boolean isProcess = false;
				Iterator<Queue<String>> columnRows = data.values().iterator();
				StringBuilder row = new StringBuilder();
				while(columnRows.hasNext()) {
					Queue<String> currentRowList = columnRows.next();
					if(currentRowList.size()!=0) {
						String rowValue = currentRowList.poll();
						row.append(rowValue + "%,");
						isProcess = true;
					} else {
						String rowValue = "-";
						row.append(rowValue + "%,");
					}
				}
				row.replace(row.length()-2, row.length(), "");
				
				if(!isProcess) {
					break;
				} 
				String format = rowFormat.toString();
				Object[] rowDisplay = row.toString().split("%,", -1);
				dataToReturn.append(String.format(format,rowDisplay));
				dataToReturn.append(String.format(borderFormat.toString()));
			}	
			dataToReturn.append(String.format("%n%n%n"));
		return dataToReturn.toString();
	}
	
	public String writeReport() {
		Date date = new Date();
		StringBuilder writer = new StringBuilder();
		StringBuilder rowFormat = new StringBuilder();
		StringBuilder borderFormat = new StringBuilder();
		StringBuilder columnFormat = new StringBuilder();
		
		buildTableFormat(rowFormat, borderFormat, columnFormat);
		
			String timestamp = "Timestamp: " + new Timestamp(date.getTime()).toString()+"%n";
			writer.append(String.format(timestamp));
			if(description!=null) {
				writer.append(String.format("Description: " + description+"%n"));
			}
			writer.append(String.format(borderFormat.toString()));
			writer.append(String.format(columnFormat.toString()));
			writer.append(String.format(borderFormat.toString()));
			while(true) {
				boolean isProcess = false;
				Iterator<Queue<String>> columnRows = data.values().iterator();
				StringBuilder row = new StringBuilder();
				List<StringBuilder> innerRows = new LinkedList<>();
				while(columnRows.hasNext()) {
					Queue<String> currentRowList = columnRows.next();
					if(currentRowList.size()!=0) {
						String rowValue = currentRowList.poll();
						row.append(rowValue + "%,");
						isProcess = true;
					} else {
						String rowValue = "-";
						row.append(rowValue + "%,");
						
					}
				}
				
				row.replace(row.length()-2, row.length(), "");
				if(!isProcess) {
					break;
				} 
				String format = rowFormat.toString();
				Object[] rowDisplay = row.toString().split("%,", -1);
				writer.append(String.format(format,rowDisplay));
				for(StringBuilder each: innerRows) {
					each.replace(each.length()-2, each.length(), "");
					Object[] innerRowDisplay = each.toString().split("%,", -1);
					writer.append(String.format(format,innerRowDisplay));
				}
				
				writer.append(String.format(borderFormat.toString()));
			}	
			writer.append(String.format("%n%n%n"));
		
		return writer.toString();
	}
	
	public boolean writeReport(File file) {
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Date date = new Date();
		
		StringBuilder rowFormat = new StringBuilder();
		StringBuilder borderFormat = new StringBuilder();
		StringBuilder columnFormat = new StringBuilder();
		
		buildTableFormat(rowFormat, borderFormat, columnFormat);
		try (FileWriter fw = new FileWriter(file, true);
			 BufferedWriter bw = new BufferedWriter(fw);
		     PrintWriter writer = new PrintWriter(bw); 
			){
			String timestamp = "Timestamp: " + new Timestamp(date.getTime()).toString()+"%n";
			writer.write(String.format(timestamp));
			if(description!=null) {
				writer.write(String.format("Description: " + description+"%n"));
			}
			writer.write(String.format(borderFormat.toString()));
			writer.write(String.format(columnFormat.toString()));
			writer.write(String.format(borderFormat.toString()));
			while(true) {
				boolean isProcess = false;
				Iterator<Queue<String>> columnRows = data.values().iterator();
				StringBuilder row = new StringBuilder();
				while(columnRows.hasNext()) {
					Queue<String> currentRowList = columnRows.next();
					if(currentRowList.size()!=0) {
						String rowValue = currentRowList.poll();
						row.append(rowValue + "%,");
						isProcess = true;
					} else {
						String rowValue = "-";
						row.append(rowValue + "%,");
					}
				}
				row.replace(row.length()-2, row.length(), "");
				
				if(!isProcess) {
					break;
				} 
				String format = rowFormat.toString();
				Object[] rowDisplay = row.toString().split("%,", -1);
				writer.write(String.format(format,rowDisplay));
				writer.write(String.format(borderFormat.toString()));
			}	
			writer.write(String.format("%n%n%n"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	private void buildTableFormat(StringBuilder format, StringBuilder border,
			StringBuilder header) {
		Iterator<Queue<String>> iterator = data.values().iterator();
		Queue<String> tmpHeaders = new LinkedList<>(originalColumnHeaders);
		while(iterator.hasNext()) {
			String headerValue = tmpHeaders.poll();
			Queue<String> value = iterator.next();
			value.add(headerValue);
			int widthSize = getLongestString(value);
			value.remove(headerValue);
			format.append("| %-"+(widthSize+4)+"s");
			
			
			int headerPadding = widthSize - headerValue.length() + 4;
			border.append("+-----");
			header.append("| " + headerValue);
			for(int i=0; i<headerPadding; i++) {
				header.append(" ");
			}
			
			if(tmpHeaders.size()==0) {
				header.append("|");
			}
			
			for(int i=0; i<widthSize; i++) {
				border.append("-");
			}
			
 		}
		border.append("+%n");
		format.append("|%n");
		header.append("%n");
	}
	
	public int getLongestString(Collection<String> list) {
		int max = 0;
		Iterator<String> iterator = list.iterator();
		while(iterator.hasNext()) {
			String currentString = iterator.next();
			int currentLength = currentString.length();
			if(currentLength>max) {
				max = currentLength;
			}
		}
		return max;
	}

	public void setReportDescription(String description) {
		this.description = description;
	}

	public boolean hasData() {
		Collection<Queue<String>> tmp = data.values();
		for(Queue<String> each: tmp) {
			if(each.size()>0) {
				return true;
			}
		}
		return false;
	}

}
