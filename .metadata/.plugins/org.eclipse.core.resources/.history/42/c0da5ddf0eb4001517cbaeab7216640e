package com.svi.tools.neo4j.rest.test;

import com.svi.tools.neo4j.rest.tools.DynamicQueryBuilder;

public class Test {

	public static void main(String[] args) {
		String query = "MATCH (p:PERSON) WHERE p.firstName={firstName} AND p.middleName={middleName} AND p.lastName={lastName} RETURN p";
		DynamicQueryBuilder qb = new DynamicQueryBuilder(query);
		qb.setProperty("lastName", "Fed");
//		qb.setProperty("middleName", "Fed");
//		qb.setProperty("firstName", "Manangan");
		System.out.println("QUERY: " + qb.getQuery());
	}

}
