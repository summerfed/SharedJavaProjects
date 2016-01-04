package com.svi.bpo.objects;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EndpointObj  implements IsSerializable{

	public String endpointId;
	public String label;
	boolean isActive = true;
	
	public EndpointObj() {
	}
	
	
	public EndpointObj(String endpointId, String label) {
		this.endpointId = endpointId;
		this.label = label;
	}


	public String getEndpointId() {
		return endpointId;
	}
	public void setEndpointId(String endpointId) {
		this.endpointId = endpointId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "EndpointObj [endpointId=" + endpointId + ", label=" + label
				+ ", isActive=" + isActive + "]";
	}
	
	
}
