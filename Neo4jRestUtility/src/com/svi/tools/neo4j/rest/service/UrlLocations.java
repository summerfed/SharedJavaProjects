package com.svi.tools.neo4j.rest.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UrlLocations {
	private String node;
	private String node_index;
	private String relationship_index;
	private String extensions_info;
	private String relationship_types;
	private String batch;
	private String cypher;
	private String indexes;
	private String constraints;
	private String transaction;
	private String node_labels;
	private String neo4j_version;
	
	protected UrlLocations(String neo4jUrlInstance) {
		neo4jUrlInstance = neo4jUrlInstance.trim();
		if(!neo4jUrlInstance.isEmpty()) {
			if(neo4jUrlInstance.charAt(neo4jUrlInstance.length()-1) != '/') {
				neo4jUrlInstance += "/";
			}
			setUrls(neo4jUrlInstance);
		}
	}
	
	private void setUrls(String neo4jUrlInstance) {
		WebResource resource = Client.create().resource(neo4jUrlInstance + "db/data/");
		try {
			ClientResponse response = resource
	                .accept( MediaType.APPLICATION_JSON )
	                .type( MediaType.APPLICATION_JSON )
	                .get( ClientResponse.class );
			
			if (response.getStatus() != 200) {
				   throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus() + " Details - " + response.getEntity(String.class));
				}
		 
				String resultObject = response.getEntity(String.class).trim();
				response.close();
				if(resultObject!=null) {
					Map<String, String> urlMap = new HashMap<>();
					try {
						JSONObject result = new JSONObject(resultObject);
						Iterator<?> resultKeys = result.keys();
						while(resultKeys.hasNext()) {
							String currentKey = resultKeys.next().toString();
							String value = result.getString(currentKey);
							urlMap.put(currentKey, value);
						}
						setUrlByMap(urlMap);
						
					} catch (JSONException e) {
						e.printStackTrace();
					} {
						
					}
				}
		} catch(ClientHandlerException e) {
			System.out.println(e.getLocalizedMessage() + " Please Check Connection With Neo4j Server");
		}
	}
	
	private  void setUrlByMap(Map<String, String> urlMap) {
		node = urlMap.get("node");
		node_index = urlMap.get("node_index");
		relationship_index = urlMap.get("relationship_index");
		extensions_info = urlMap.get("extensions_info");
		relationship_types = urlMap.get("relationship_types");
		batch = urlMap.get("batch");
		cypher = urlMap.get("cypher");
		indexes = urlMap.get("indexes");
		constraints = urlMap.get("constraints");
		transaction = urlMap.get("transaction");
		node_labels = urlMap.get("node_labels");
		neo4j_version = urlMap.get("neo4j_version");
	}
	
	protected  String getNode() {
		return node;
	}

	protected  String getNode_index() {
		return node_index;
	}

	protected  String getRelationship_index() {
		return relationship_index;
	}

	protected  String getExtensions_info() {
		return extensions_info;
	}

	protected  String getRelationship_types() {
		return relationship_types;
	}

	protected  String getBatch() {
		return batch;
	}

	protected  String getCypher() {
		return cypher;
	}

	protected  String getIndexes() {
		return indexes;
	}

	protected  String getConstraints() {
		return constraints;
	}

	protected  String getTransaction() {
		return transaction;
	}
	
	protected  String getNodeWithLabel(String nodeUri) {
		return nodeUri+"/labels";
	}

	protected  String getNode_labels() {
		return node_labels;
	}

	protected  String getNeo4j_version() {
		return neo4j_version;
	}
	
	
	/*public String getConfigurationPath() {
		return Directories.configPath;
	}
	
	public String getTemplatePath() {
		return Directories.templatePath;
	}*/
}
