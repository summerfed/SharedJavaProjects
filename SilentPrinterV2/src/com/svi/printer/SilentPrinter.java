package com.svi.printer;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

public class SilentPrinter {
	
	public static boolean printFile(String filePath, String printerName){
		return printFile(new File(filePath), printerName, 1);
	}
	
	public static boolean printFile(String filePath, String archivePath, String printerName){
		return printFile(new File(filePath), new File(archivePath), printerName, 1);
	}
	
	public static boolean printFile(String filePath, File archivePath, String printerName){
		return printFile(new File(filePath), archivePath, printerName, 1);
	}
	
	public static boolean printFile(File filePath, String archivePath, String printerName){
		return printFile(filePath, new File(archivePath), printerName, 1);
	}
	
	public static boolean printFile(String filePath, File archivePath, String printerName, int numOfCopies){
		if (numOfCopies < 1) {
			System.err.println("Number of compies must not be less than 1.");
			return false;
		}
		return printFile(new File(filePath), archivePath, printerName, numOfCopies);
	}
	
	public static boolean printFile(String filePath, String archivePath, String printerName, int numOfCopies){
		if (numOfCopies < 1) {
			System.err.println("Number of compies must not be less than 1.");
			return false;
		}
		return printFile(new File(filePath), new File(archivePath), printerName, 1);
	}
	
	public static boolean printFile(File filePath, String archivePath, String printerName, int numOfCopies){
		if (numOfCopies < 1) {
			System.err.println("Number of compies must not be less than 1.");
			return false;
		}
		return printFile(filePath, new File(archivePath), printerName, numOfCopies);
	}
	
	private static boolean printFile(File filePath, File archivePath, String printerName, int numOfCopies){
		
		if (!archivePath.isDirectory()) {
			System.err.println("Archive Path must be a directory: " + filePath.getAbsolutePath());
			return false;
		}
		
		if (filePath.isDirectory()) {
			System.err.println("Input File must be a file: " + filePath);
			return false;
		}
		
		try {
			PrintService[] printServices;
			PrintService printService;


			PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
			printServiceAttributeSet.add(new PrinterName(printerName, null));
			printServices = PrintServiceLookup.lookupPrintServices(null,
					printServiceAttributeSet);

			PrinterJob printerjob = PrinterJob.getPrinterJob();

			try {
				printService = printServices[0];
				printerjob.setPrintService(printService); // Try setting the printer
															// you want
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("Error: No printer named '" + printerName
						+ "', using default printer.");
				printerjob = null;
			} catch (PrinterException exception) {
				System.err.println("Printing error: " + exception);
				return false;
			}
			
			PDDocument doc = null;
			try {
				doc = PDDocument.load(filePath);
				
				for (int i = 0; i < numOfCopies; i++) {
					if (printerjob != null) {
						doc.silentPrint(printerjob);
					} else {
						doc.silentPrint();
					}
				}
			} catch(Exception e ) {
				e.printStackTrace();
			} finally {
				doc.close();
			}
			
		} catch (Exception e) {
			System.err.println("Error in printing: " + filePath.getName());
			return false;
		}
		
		try {
			FileUtils.moveFile(filePath, new File(archivePath, filePath.getName()));
		} catch (IOException e) {
			System.err.println("Error moving file: " + filePath);
			return false;
		}
		
		return true;
	}
	
	public static boolean printFile(File filePath, String printerName, int numOfCopies){
		
		if (filePath.isDirectory()) {
			System.err.println("Input File must be a file: " + filePath);
			return false;
		}
		
		try {
			PrintService[] printServices;
			PrintService printService;


			PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
			printServiceAttributeSet.add(new PrinterName(printerName, null));
			printServices = PrintServiceLookup.lookupPrintServices(null,
					printServiceAttributeSet);

			PrinterJob printerjob = PrinterJob.getPrinterJob();

			try {
				printService = printServices[0];
				printerjob.setPrintService(printService); // Try setting the printer
															// you want
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("Error: No printer named '" + printerName
						+ "', using default printer.");
				printerjob = null;
			} catch (PrinterException exception) {
				System.err.println("Printing error: " + exception);
				return false;
			}
			
			PDDocument doc = null;
			try {
				doc = PDDocument.load(filePath);
				
				for (int i = 0; i < numOfCopies; i++) {
					if (printerjob != null) {
						doc.silentPrint(printerjob);
					} else {
						doc.silentPrint();
					}
				}
			} catch(Exception e ) {
				e.printStackTrace();
			} finally {
				doc.close();
			}
			
		} catch (Exception e) {
			System.err.println("Error in printing: " + filePath.getName());
			return false;
		}
		
		return true;
	}
}
