package com.svi.printer;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;

import org.apache.pdfbox.pdmodel.PDDocument;

public class Print {
	
	private static PrinterJob printerjob;
	
	public Print(String printerName){
		PrintService[] printServices;
		PrintService printService;


		PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
		printServiceAttributeSet.add(new PrinterName(printerName, null));
		printServices = PrintServiceLookup.lookupPrintServices(null,
				printServiceAttributeSet);

		printerjob = PrinterJob.getPrinterJob();

		try {
			printService = printServices[0];
			printerjob.setPrintService(printService); // Try setting the printer
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Error: No printer named '" + printerName
					+ "', using default printer.");
			printerjob = null;
		} catch (PrinterException exception) {
			System.err.println("Printing error: " + exception);
		}

	}

	public static void main(String[] args) throws IOException, PrinterException {
		// PDDocument doc = PDDocument.load("1.pdf");
		//
		// // PrinterJob pj = new PrinterJob.getPrinterJob();
		// // pj.setPrintService(service)
		// doc.print();
		// System.out.println("silent print start");
		// // doc.silentPrint();
		// System.out.println("silent print end");

		
		// try {
		// // printerjob.print(); // Actual print command
		// } catch (PrinterException exception) {
		// System.err.println("Printing error: " + exception);
		// }


	}
	
	public static boolean print(File file) throws IOException, PrinterException {
		PDDocument doc = PDDocument.load(file);
		if (printerjob != null) {
			double height = doc.getPageFormat(0).getPaper().getHeight();
			double width= doc.getPageFormat(0).getPaper().getWidth();
			
			System.out.println("height " + height);
			System.out.println("width " + width);
			
//			doc.silentPrint(printerjob);
		} else {
//			doc.silentPrint();
		}
		
		doc.close();
		return true;
	}
	
	

}
