package com.svi.tools.neo4j.rest.enums;

public enum Notifications {
	CREATE_NODE_SUCCESS("SUCCESS! Node is Added to Database"),
	CREATE_NODE_FAILED("FAILED! Node Not Added Please Contact Administrator"),
	
	ADD_NODE_LABEL_SUCCESS("SUCCESS! Label is Added to Node"),
	ADD_NODE_LABEL_FAILED("FAILED! Can't Add Label, Unknown Error"),
	
	CREATE_RELATIONSHIP_SUCCESS("SUCCESS! Relationship has been created"),
	CREATE_RELATIONSHIP_FAILED("FAILED! Can't proceed with relationship creation");
	
	String value = null;
	private Notifications(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
