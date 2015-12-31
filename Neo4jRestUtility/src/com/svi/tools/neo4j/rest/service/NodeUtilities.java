package com.svi.tools.neo4j.rest.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.svi.tools.neo4j.rest.enums.Notifications;


public class NodeUtilities{
	UrlLocations urls;
	CypherUtilities cypher;

	protected NodeUtilities(UrlLocations urls) {
		this.urls = urls;
		cypher = new CypherUtilities(urls);
	}
	
	protected String createNodeWithProperties(Map<String, Object> properties) {
        StringBuilder jsonPostBuilder = buildCreateNodeJSON(properties);
        
        WebResource resource = Client.create().resource(urls.getNode());
       
        ClientResponse response = resource
                .accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity(jsonPostBuilder.toString())
                .post( ClientResponse.class );
        
      	String dataToReturn = null;
        int statusCode = response.getStatus();
      	if(statusCode == 201) {
      		dataToReturn = Notifications.CREATE_NODE_SUCCESS.getValue();
      	} else {
      		dataToReturn = Notifications.CREATE_NODE_FAILED.getValue();
      	}
        response.close();
        return dataToReturn;
	}

	protected String createNodeWithPropertiesAndLabel(Map<String, Object> properties, String label) {
		StringBuilder jsonPostBuilder = buildCreateNodeJSON(properties);
        WebResource resource = Client.create().resource(urls.getNode());
        ClientResponse response = resource
                .accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity(jsonPostBuilder.toString())
                .post( ClientResponse.class );
        
        String dataToReturn = null;
        int statusCode = response.getStatus();
    	if(statusCode == 201) {
    		String nodeLocation = response.getLocation().toString();
    		String status = addLabelToNode(label, nodeLocation);
    		if(status.equals(Notifications.ADD_NODE_LABEL_SUCCESS.getValue())) {
    			dataToReturn = Notifications.CREATE_NODE_SUCCESS.getValue();
    		} else {
    			dataToReturn = Notifications.ADD_NODE_LABEL_FAILED.getValue();
    		}
    	} else {
    		dataToReturn = "Error Code: " + statusCode + ": " +Notifications.CREATE_NODE_FAILED.getValue();
    	}
        response.close();
        return dataToReturn;
	}
	
	protected String addLabelToNode(String label, String nodeLocation) {
		String dataToReturn = null;
		label = label.trim().toUpperCase();
		StringBuilder jsonPostBuilder = new StringBuilder();
		jsonPostBuilder.append("\"" +label+ "\"");
		WebResource resource = Client.create().resource(urls.getNodeWithLabel(nodeLocation));
		
		ClientResponse response = resource
	                .accept( MediaType.APPLICATION_JSON )
	                .type( MediaType.APPLICATION_JSON )
	                .entity(jsonPostBuilder.toString())
	                .post( ClientResponse.class );
		
		int statusCode = response.getStatus();
		if(statusCode == 204) {
			dataToReturn = Notifications.ADD_NODE_LABEL_SUCCESS.getValue();
		} else {
			dataToReturn = Notifications.ADD_NODE_LABEL_FAILED.getValue();
		}
		return dataToReturn;
	}
	
	protected List<String> findNodes(Map<String, Object> properties, String label) {
		List<String> dataToReturn = new ArrayList<>();
		label = label.trim().toUpperCase();
		WebResource resource = Client.create().resource(urls.getCypher());
        
        String query = findNodesQueryBuilder(properties, label);
        StringBuilder jsonPostBuilder =  buildFindNodeJSON(properties, query);
        ClientResponse response = resource
                .accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity(jsonPostBuilder.toString())
                .post( ClientResponse.class );
        String responseResult = response.getEntity(String.class);
        try {
			List<Map<String, Object>> result = cypher.getCypherQueryResult(responseResult);
			for(Map<String, Object> row: result) {
				String id = row.get("ID").toString();
				dataToReturn.add(urls.getNode()+ "/" +id);
			}
		} catch (ClientHandlerException | UniformInterfaceException | JSONException e) {
			System.out.println("INVALID QUERY: " + responseResult);
			e.printStackTrace();
		}
        
        response.close();
		return dataToReturn;
	}
	
	protected List<String> findNodes(Map<String, Object> properties) {
		List<String> dataToReturn = new ArrayList<>();
		WebResource resource = Client.create().resource(urls.getCypher());
       
        String query = "MATCH (n) RETURN id(n) AS ID;";
        StringBuilder jsonPostBuilder = buildFindNodeJSON(properties, query);
        
        ClientResponse response = resource
                .accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity(jsonPostBuilder.toString())
                .post( ClientResponse.class );
        
        String responseResult = response.getEntity(String.class);
        try {
			List<Map<String, Object>> result = cypher.getCypherQueryResult(responseResult);
			for(Map<String, Object> row: result) {
				String id = row.get("ID").toString();
				dataToReturn.add(urls.getNode()+ "/" +id);
			}
		} catch (ClientHandlerException | UniformInterfaceException | JSONException e) {
			System.out.println("INVALID QUERY: " + responseResult);
			e.printStackTrace();
		}
        
        response.close();
		return dataToReturn;
	}
	
	private String findNodesQueryBuilder(Map<String, Object> properties, String label) {
		StringBuilder query = new StringBuilder();
		query.append("MATCH (n:"+label+ " {"); /*" ) RETURN id(n) AS ID;");*/
		for(String eachProperty: properties.keySet()) {
			query.append(eachProperty+":"+"{"+eachProperty+"}, ");
		}
		
		query.replace(query.lastIndexOf(","), query.length(), "");
		
		query.append("}) RETURN id(n) AS ID;");
		return query.toString();
	}
	
	private StringBuilder buildFindNodeJSON(Map<String, Object> properties, String query) {
		StringBuilder jsonPostBuilder = new StringBuilder();
		jsonPostBuilder.append("{\"query\" : " + "\"" +query+ "\"," +
        				  "\"params\" : {");
        
        for(Entry<String, Object> each: properties.entrySet()) {
        	String propertyName = "\"" +each.getKey()+ "\"";
        	String value = "";
        	if(each.getValue()!=null) {
        		String valueToEnter = each.getValue().toString();
        		valueToEnter = valueToEnter.replaceAll("\"", "\\\\" + "\"");
        		value = "\"" +valueToEnter+"\"".trim();
        	}
        	jsonPostBuilder.append(propertyName+ ":" +value+ ", ");
        }
        
        jsonPostBuilder.replace(jsonPostBuilder.lastIndexOf(","), jsonPostBuilder.length(), "");
        jsonPostBuilder.append("}}");
        return jsonPostBuilder;
	}
	
	private StringBuilder buildCreateNodeJSON(Map<String, Object> properties) {
		StringBuilder jsonPostBuilder = new StringBuilder();
        jsonPostBuilder.append("{");
        
        for(Entry<String, Object> each: properties.entrySet()) {
        	String propertyName = "\"" +each.getKey()+ "\"";
        	String value = "";
        	if(each.getValue()!=null) {
        		String valueToEnter = each.getValue().toString();
        		valueToEnter = valueToEnter.replaceAll("\"", "\\\\" + "\"");
        		value = "\"" +valueToEnter+"\"".trim();
        	}
        	
        	jsonPostBuilder.append(propertyName+ ":" +value+ ", ");
        }
        jsonPostBuilder.replace(jsonPostBuilder.lastIndexOf(","), jsonPostBuilder.length(), "");
        jsonPostBuilder.append("}");
		
        return jsonPostBuilder;
	}
	
	
}
