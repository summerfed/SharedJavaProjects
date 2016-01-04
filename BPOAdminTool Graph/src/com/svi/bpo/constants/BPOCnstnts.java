package com.svi.bpo.constants;

public enum BPOCnstnts {

	LGINSVC("lginSvc"),
	BPOSVC("bpoSvc"),
	
	ENDPOINT("ENDPOINT"),
	ENDPOINT_LABEL("ENDPOINT_LABEL"),
	BPO_ACCOUNT_PATH("BPO_ACCOUNT_PATH"),
	BPO_ENDPOINT_PATH("BPO_ENDPOINT_PATH"),
	
	CLIENT_MODE("CLIENT_MODE"),
	SSO("SSO"),
	APP_NAME("APP_NAME"),
	SSO_PORTAL("SSO_PORTAL"),
	
	USERNAME("USERNAME"),
	PASSWORD("PASSWORD"),
	USER_ENDPOINTS("USER_ENDPOINTS"),
	
	;
	
	private String value;
	
	BPOCnstnts(String value) {
		this.setValue(value);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;  
    }
}
