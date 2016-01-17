package com.svi.printer.util;

public enum SilentPrinterEnum {

	CONFIG_PATH("config/config.ini"),
	
	GLOBAL_FIELDS("GLOBAL_FIELDS"),
	INPUT_PATH("INPUT_PATH"),
		ARCHIVE_PATH("ARCHIVE_PATH"),
		PRINTER("PRINTER"),
		
	
	;
	
	private String value;
	private int valueInt=0;
	
	SilentPrinterEnum(String value) {
		this.setValue(value);
	}
	SilentPrinterEnum(int value) {
		this.setValueInteger(value);
	}
	public void setValueInteger(int value) {
		this.valueInt = value;
		
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;  
    }

	public int getValueInt() {
		return valueInt;
	}
}
