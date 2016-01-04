package com.svi.bpo.objects;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BpoDtlsObj implements IsSerializable{

	private String endpoint;
	private String bpoAcctPath;
	private String bpoEndpntPath;
	private String mode;
	
	
	public BpoDtlsObj() {
	}
	
	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getBpoAcctPath() {
		return bpoAcctPath;
	}

	public void setBpoAcctPath(String bpoAcctPath) {
		this.bpoAcctPath = bpoAcctPath;
	}

	public String getBpoEndpntPath() {
		return bpoEndpntPath;
	}

	public void setBpoEndpntPath(String bpoEndpntPath) {
		this.bpoEndpntPath = bpoEndpntPath;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
}
