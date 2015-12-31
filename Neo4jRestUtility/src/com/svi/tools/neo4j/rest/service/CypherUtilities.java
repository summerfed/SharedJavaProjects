package com.svi.tools.neo4j.rest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CypherUtilities {
	UrlLocations urls;
	protected CypherUtilities(UrlLocations urls) {
		this.urls = urls;
	}
	
	protected List<Map<String, Object>> sendCypherQuery(String query, Map<String, Object> properties) {
		List<Map<String, Object>> dataToReturn = new ArrayList<>();
		
		WebResource resource = Client.create().resource(urls.getCypher());
       
		JSONObject jsonPostData = parseToJSON(query, properties);
        ClientResponse response = resource
                .accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity(jsonPostData.toString())
                .post( ClientResponse.class );
        
        String result = response.getEntity(String.class);
        response.close();
		try {
			dataToReturn = getCypherQueryResult(result);
		} catch (JSONException e) {
			System.out.println("INVALID QUERY: " + query);
			System.out.println(result);
		}
        return dataToReturn;
    }
	
	protected List<Map<String, Object>> sendCypherQuery(String query) {
		List<Map<String, Object>> dataToReturn = new ArrayList<>();
		
		WebResource resource = Client.create().resource(urls.getCypher());
       
		JSONObject jsonPostData = parseToJSON(query);
        
        ClientResponse response = resource
                .accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity(jsonPostData.toString())
                .post( ClientResponse.class );
        
        String result = response.getEntity(String.class);
        response.close();
        
		try {
			dataToReturn = getCypherQueryResult(result);
		} catch (JSONException e) {
			System.out.println("INVALID QUERY");
			System.out.println(result);
		}
        return dataToReturn;
    }
	
	protected boolean sendCypherQueryBooleanResult(String query) {
		
		WebResource resource = Client.create().resource(urls.getCypher());
       
		JSONObject jsonPostData = parseToJSON(query);
		
	    ClientResponse response = resource
	                .accept( MediaType.APPLICATION_JSON )
	                .type( MediaType.APPLICATION_JSON )
	                .entity(jsonPostData.toString())
	                .post( ClientResponse.class );
	   	int statusCode = response.getStatus();
	   
	    boolean dataToReturn = checkPostConnection(statusCode, response);
        response.close();
        return dataToReturn;
    }
	
	protected boolean sendCypherQueryBooleanResult(String query, Map<String, Object> properties) {
		WebResource resource = Client.create().resource(urls.getCypher());
		
        JSONObject jsonPostData = parseToJSON(query, properties);
	    ClientResponse response = resource
	                .accept( MediaType.APPLICATION_JSON )
	                .type( MediaType.APPLICATION_JSON )
	                .entity(jsonPostData.toString())
	                .post( ClientResponse.class );
	    System.out.println(response.getEntity(String.class));
	    int statusCode = response.getStatus();
        boolean dataToReturn = checkPostConnection(statusCode, response);
        response.close();
        return dataToReturn;
    }

	private boolean checkPostConnection(int statusCode, ClientResponse response) {
		if(statusCode == 200) {
        	return true;
        } else {
        	System.out.println("FAILED: " + statusCode +" - " + response.getEntity(String.class));
        }
        
        return false;
	}

	private JSONObject parseToJSON(String query) {
		JSONObject jsonPostData = new JSONObject();
        JSONObject jsonProperties = new JSONObject();
        try {
			jsonPostData.put("query", query);
			jsonPostData.put("params", jsonProperties);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonPostData;
	}
	
	private JSONObject parseToJSON(String query, Map<String, Object> properties) {
		JSONObject jsonPostData = new JSONObject();
        JSONObject jsonProperties = new JSONObject();
        try {
			jsonPostData.put("query", query);
			
			 for(Entry<String, Object> each: properties.entrySet()) {
             	String propertyName = each.getKey();
             	Object value = null;
             	Object tempValue = each.getValue();
             	
             	if(tempValue!=null) {
             		if(tempValue instanceof Collection) {
						value = new JSONArray((Collection<?>)tempValue);
             		} else {
             			value = tempValue;
             		}
             	}
             	jsonProperties.put(propertyName, value);
             }
			 jsonPostData.put("params", jsonProperties);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonPostData;
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private String replaceIllegalCharacters(Entry<String, Object> each) {
		String value;
		String valueToEnter = each.getValue().toString();
		valueToEnter = valueToEnter.replaceAll("\"", "\\\\" + "\"");
		valueToEnter = valueToEnter.replace("\\", "\\\\");
		value = valueToEnter;
		if(value.isEmpty() || value.charAt(0) != '[') {
			value = "\"" + value + "\"";
		}
		
		return value;
	}
	
	protected List<Map<String, Object>> getCypherQueryResult(String result) throws JSONException{
		List<Map<String, Object>> dataToReturn = new ArrayList<>();
	
		JSONObject resultObject = new JSONObject(result);
		JSONArray rows = resultObject.getJSONArray("data");
		JSONArray columns = resultObject.getJSONArray("columns");
		for(int rowCount = 0; rowCount<rows.length(); rowCount++) {
			JSONArray rowFields = rows.getJSONArray(rowCount);
			Map<String, Object> rowMap = new LinkedHashMap<>();
			for(int fieldCount=0; fieldCount<rowFields.length(); fieldCount++) {
				String column = columns.getString(fieldCount);
				Object field = rowFields.get(fieldCount);
				if(field instanceof JSONObject) {
					JSONObject properties = ((JSONObject) field).getJSONObject("data");
					Iterator<?> propertyKeys = properties.keys();
					while(propertyKeys.hasNext()) {
						String propertyKey = (String) propertyKeys.next();
						Object propertyValue = properties.get(propertyKey);
						rowMap.put(column+"."+propertyKey, propertyValue);
					}
				} else {
					rowMap.put(column, field);
				}
				
			}
			dataToReturn.add(rowMap);
		}
		
        return dataToReturn;
	}
}
