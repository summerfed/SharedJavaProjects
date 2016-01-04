package com.svi.bpo.objects;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SSOInfoDto implements IsSerializable{
	private String ssoPrtlUrl;
	private boolean isSSO;
	private boolean isDisconnected;
	private String appNm;
	public String getSsoPrtlUrl() {
		return ssoPrtlUrl;
	}
	public void setSsoPrtlUrl(String ssoPrtlUrl) {
		this.ssoPrtlUrl = ssoPrtlUrl;
	}
	public boolean isSSO() {
		return isSSO;
	}
	public void setSSO(boolean isSSO) {
		this.isSSO = isSSO;
	}
	public void setDisconnected(boolean isDisconnected) {
		this.isDisconnected = isDisconnected;
	}
	public boolean isDisconnected() {
		return isDisconnected;
	}
	public void setAppNm(String appNm) {
		this.appNm = appNm;
	}
	public String getAppNm() {
		return appNm;
	}
	
}
