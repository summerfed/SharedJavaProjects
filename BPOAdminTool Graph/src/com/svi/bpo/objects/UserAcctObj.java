package com.svi.bpo.objects;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserAcctObj implements IsSerializable{

	//TODO remove password
	//	replace endpoints to List<EndpointObj>
	private String username;
	private String password;
	private List<EndpointObj> endpoints;
	private String loginMsg;

	public UserAcctObj() {
		this.endpoints = new ArrayList<EndpointObj>();
	}
	
	

	public String getLoginMsg() {
		return loginMsg;
	}

	public void setLoginMsg(String loginMsg) {
		this.loginMsg = loginMsg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<EndpointObj> getEndpoints() {
		return endpoints;
	}

	@Override
	public String toString() {
		return "UserAcctObj [username=" + username + ", password=" + password
				+ ", endpoints.size()=" + endpoints.size() + ", loginMsg=" + loginMsg + "]";
	}
	
	
}
