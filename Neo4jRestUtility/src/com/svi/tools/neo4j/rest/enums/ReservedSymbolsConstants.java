package com.svi.tools.neo4j.rest.enums;

public enum ReservedSymbolsConstants {
	PERIOD("."),
	COLON(":"),
	SEMICOLON(";"),
	COMMA(","),
	TILDE("~"),
	PLUS("+"),
	MINUS("-"),
	EQUALS("="),
	PARENTHESIS_OPEN("("),
	PARENTHESIS_CLOSE(")"),
	ANGLE_BRACKET_OPEN("<"),
	ANGLE_BRACKET_CLOSE(">"),
	CURLY_BRACKET_OPEN("{"),
	CURLY_BRACKET_CLOSE("}"),
	SQUARE_BRACKET_OPEN("["),
	SQUARE_BRACKET_CLOSE("]"),
	QUESTIONMARK_I("?i"),
	ASTERISK("*"),
	;
	String value = null;
	private ReservedSymbolsConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
