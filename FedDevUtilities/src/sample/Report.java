package sample;

import java.io.File;

import com.fed.dev.report.TabularReport;

public class Report {

	public static void main(String[] args) {
		File reportFile = new File("report.txt");
		TabularReport report = new TabularReport();
		report.setColumnHeaders("TEST");
		report.setColumnHeaders("TEST1");
//		report.setReportDescription("TEST");
		report.addRow("TEST", "TestValue");
		report.addRow("TEST1", "Test1Value");
		System.out.println(report.writeReport());
	
		/*String value = "Test%,Test%,";
		String tmp = value.replaceAll("[^%,]" ,"");
		System.out.println(tmp);
		System.out.println(replaceLast(tmp,"%,","").length());
		System.out.println(tmp.length());*/
	}
	
	private static String replaceLast(String string, String substring, String replacement)
	{
	  int index = string.lastIndexOf(substring);
	  if (index == -1)
	    return string;
	  return string.substring(0, index) + replacement
	          + string.substring(index+substring.length());
	}
	

}
