package com.svi.tools.neo4j.rest.enums;

public enum ReservedWordsConstants {
	_MATCH_(" MATCH "),
	_RETURN_(" RETURN "),
	_AND_(" AND "),
	_WHERE_(" WHERE "),
	_DISTINCT_(" DISTINCT "),
	_AS_(" AS "),
	
	;
	String value = null;
	private ReservedWordsConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
