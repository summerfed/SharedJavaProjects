package com.svi.bpo.constants;

public enum ResultCnstnts {
	
	NODE("N"),
	ELEMENTS("E")
	
	;
	
	private String value;
	
	ResultCnstnts(String value) {
		this.setValue(value);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;  
    }
}
