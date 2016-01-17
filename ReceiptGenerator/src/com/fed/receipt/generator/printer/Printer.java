package com.fed.receipt.generator.printer;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class Printer {
	private static PrintService[] installedPrinters = PrintServiceLookup.lookupPrintServices(null, null);
	private static PrintService selectedPrinter = PrintServiceLookup.lookupDefaultPrintService();
	
	public static PrintService[] getInstalledPrinters() {
		return installedPrinters;
	}
	
	public static void setInstalledPrinters(PrintService[] installedPrinters) {
		Printer.installedPrinters = installedPrinters;
	}

	public static PrintService getSelectedPrinter() {
		return selectedPrinter;
	}

	public static void setSelectedPrinter(PrintService selectedPrinter) {
		Printer.selectedPrinter = selectedPrinter;
	}
}
