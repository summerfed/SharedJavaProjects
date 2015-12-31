package com.svi.bpo.graph;

public enum NodeAttributes {
	NODE_NAME(NodeFunctions.NODE_ATTR_NODE_NAME),
	STD_UNIT_OF_MEASURE(NodeFunctions.NODE_ATTR_STD_UNIT_MEASURE),
	ALLOWED_WAITING_DURATION(NodeFunctions.NODE_ATTR_ALLOWED_WAITING_DURATION),
	ALLOWED_PROCESS_DURATION(NodeFunctions.NODE_ATTR_ALLOWED_PROCESS_DURATION),
	TARGET_OUTPUT(NodeFunctions.NODE_ATTR_TARGET_OUTPUT),
	COST(NodeFunctions.NODE_ATTR_COST),
	ALLOWED_ERROR(NodeFunctions.NODE_ATTR_ALLOWED_ERROR);
	
	private String attribute;
	private NodeAttributes(String attribute) {
		this.attribute = attribute;
	}
	
	public String toString() {
		return attribute;
	}



}
