package com.svi.printer.exception;

@SuppressWarnings("serial")
public class SilentPrinterException extends Exception {

	private String message;
	
	public SilentPrinterException(String message) {
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}

}
