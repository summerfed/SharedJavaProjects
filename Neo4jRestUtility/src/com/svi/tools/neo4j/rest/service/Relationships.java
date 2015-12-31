package com.svi.tools.neo4j.rest.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.svi.tools.neo4j.rest.enums.Notifications;

public class Relationships {
	UrlLocations urls;
	CypherUtilities cypher;
	public Relationships(UrlLocations urls) {
		this.urls = urls;
		cypher = new CypherUtilities(urls);
	}
	
/*	public static void main(String args[]) {
		UrlLocations urls = new UrlLocations("http://localhost:7474/");
		Relationships utils = new Relationships(urls);
		String startNode = "http://localhost:7474/db/data/node/1";
		String endNode = "http://localhost:7474/db/data/node/3";
		String firstNodeLabel = "PERSON";
		Map<String, Object> firstNodeProperties = new HashMap<>();
		firstNodeProperties.put("firstName", "Arnold");
		firstNodeProperties.put("middleName", "Matthew");
		
		String secondNodeLabel = "VEHICLE";
		Map<String, Object> secondNodeProperties = new HashMap<>();
		secondNodeProperties.put("plateNumber", "ABC123");
		
		utils.createRelationship(firstNodeLabel, firstNodeProperties, secondNodeLabel, secondNodeProperties, "OWNED_BY");
		
	}*/
	
	
	protected String createRelationship(String startingNodeUrl, String endingNodeUrl, String relationshipType) {
		String uri = startingNodeUrl+"/relationships";
		
		String jsonPost = "{" +
							"\"to\" : \"" +endingNodeUrl+ "\", " +
							"\"type\" : \"" +relationshipType+ "\"" +
					      "}";
		
        WebResource resource = Client.create().resource(uri);
       
        ClientResponse response = resource
                .accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity(jsonPost)
                .post( ClientResponse.class );
        
      	String dataToReturn = Notifications.CREATE_RELATIONSHIP_FAILED.getValue();
        int statusCode = response.getStatus();
      	if(statusCode == 201) {
      		dataToReturn = Notifications.CREATE_RELATIONSHIP_SUCCESS.getValue();
      	} else {
      		dataToReturn = Notifications.CREATE_RELATIONSHIP_FAILED.getValue();
      	}
        response.close();
        
        return dataToReturn;
	}
	
	protected String createRelationship(List<String> startingNodes, List<String> endingNodes, String relationshipType) {
		int relationshipCreated = 0;
		String dataToReturn = "Relationship Created: ";
		for(String startNode:startingNodes) {
			for(String endNode:endingNodes) {
				String result = createRelationship(startNode, endNode, relationshipType);
				if(result.equals(Notifications.CREATE_RELATIONSHIP_SUCCESS.getValue())) {
					relationshipCreated++;
				}
			}
		}
		return dataToReturn += relationshipCreated;
	}
	
	protected void createRelationship(String firstNodeLabel, Map<String, Object> firstNodeProperties, String secondNodeLabel, Map<String, Object> secondNodeProperties, String relationshipType) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("MERGE (firstNode:"+firstNodeLabel+ " {");
		
		System.out.println(firstNodeProperties);
		buildCreateRelationshipQuery(firstNodeProperties, queryBuilder);
		
		queryBuilder.append(" MERGE (secondNode:"+secondNodeLabel+ " {");
		buildCreateRelationshipQuery(secondNodeProperties, queryBuilder);
		
		queryBuilder.append(" MERGE firstNode-[:"+relationshipType+"]->secondNode");
		
		
		System.out.println("THIS");
		System.out.println(queryBuilder.toString());
		cypher.sendCypherQuery(queryBuilder.toString(), null);
	}

	private void buildCreateRelationshipQuery(
			Map<String, Object> firstNodeProperties, StringBuilder queryBuilder) {
		for(Entry<String, Object> each: firstNodeProperties.entrySet()) {
	        	String propertyName = each.getKey();
	        	String value = "";
	        	if(each.getValue()!=null) {
	        		String valueToEnter = each.getValue().toString();
	        		valueToEnter = valueToEnter.replaceAll("\"", "\\\\" + "\"");
	        		value = "\'" +valueToEnter+"\'";
	        	}
	        	
	        	queryBuilder.append(propertyName+ ":" +value+ ", ");
	        }
	        queryBuilder.replace(queryBuilder.lastIndexOf(","), queryBuilder.length(), "");
	        queryBuilder.append("})");
	}

	
}
