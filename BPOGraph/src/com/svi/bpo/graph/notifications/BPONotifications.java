package com.svi.bpo.graph.notifications;

public enum BPONotifications {
	
	
	/**
	 * GENERAL NOTIFICATIONS
	 */
	
	FAILED_CHECK_CONNECTION("Failed, Check Connection"),
	
	/**
	 * NODE FUNCTIONS NOTIFICATIONS
	 */
	NODE_ALREADY_EXIST("Node Already Exist"),
	NODE_DOES_NOT_EXIST("Node Does Not Exist"),
	
	/**
	 * Exception Functions Notifications
	 */
	EXCEPTION_NODE_ALREADY_EXIST("Exception Code Already Exist"),
	EXCEPTION_NODE_INSERT_SUCCESS("Exception Node is Added Successfully"),
	EXCEPTION_FLOW_CREATE_SUCCESS("Exception Flow Constructed Successfully!"),
	EXCEPTION_LIST_DOES_NOT_EXIST("Any of Exception Nodes Does Not Exist");
	
	private String value;
	
	private BPONotifications(String value) {
		this.value = value;
	}
	public String toString() {
		return this.value;
	}
	
}
