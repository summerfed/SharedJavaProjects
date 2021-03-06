package com.svi.tools.neo4j.rest.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Neo4jRestService {
	private UrlLocations urls;
	private NodeUtilities nodeUtils;
	private CypherUtilities cypher;
	private Relationships relationshipUtils;
	private Index indexUtils;
	public Neo4jRestService(String neo4jServerUrl) { 
			urls = new UrlLocations(neo4jServerUrl);
			nodeUtils = new NodeUtilities(urls);
			cypher = new CypherUtilities(urls);
			relationshipUtils = new Relationships(urls);
			indexUtils = new Index(urls);
	}
	
	public String createNodeWithProperties(Map<String, Object> properties) {
		return nodeUtils.createNodeWithProperties(properties);
	}
	
	public String createNodeWithPropertiesAndLabel(Map<String, Object> properties, String label) {
		return nodeUtils.createNodeWithPropertiesAndLabel(properties, label);
	}
	
	public String addLabelToNode(String label, String nodeLocation) {
		return nodeUtils.addLabelToNode(label, nodeLocation);
	}
	
	public List<String> findNodes(Map<String, Object> properties) {
		return nodeUtils.findNodes(properties);
	}
	
	public List<String> findNodes(Map<String, Object> properties, String label) {
		return nodeUtils.findNodes(properties, label);
	}
	
/*	public List<Map<String, Object>> sendReadCypherQuery(String query, Map<String, Object> properties) {
		return cypher.sendCypherQuery(query, properties);
	}*/
	
	public List<Map<String, Object>> sendCypherQuery(String query, Map<String, Object> properties) {
		return cypher.sendCypherQuery(query, properties);
	}
	
	public List<Map<String, Object>> sendCypherQuery(String query) {
		return cypher.sendCypherQuery(query);
	}
	
	/**
	 * Send query without result from database, just boolean to know if query executed successfully
	 * @param query
	 * @param properties
	 * @return
	 */
	public boolean sendCypherBooleanResult(String query, Map<String, Object> properties) {
		return cypher.sendCypherQueryBooleanResult(query, properties);
	}
	
	
	/**
	 * Send query without result from database, just boolean to know if query executed successfully
	 * @param query
	 * @return
	 */
	public boolean sendCypherBooleanResult(String query) {
		return cypher.sendCypherQueryBooleanResult(query);
	}
	
	public String createRelationship(String startingNodeUrl, String endingNodeUrl, String relationshipType) {
        return relationshipUtils.createRelationship(startingNodeUrl, endingNodeUrl, relationshipType);
	}
	
	public String createRelationship(List<String> startingNodes, List<String> endingNodes, String relationshipType) {
		return relationshipUtils.createRelationship(startingNodes, endingNodes, relationshipType);
	}
	
	public void createRelationship(String firstNodeLabel, Map<String, Object> firstNodeProperties, String secondNodeLabel, Map<String, Object> secondNodeProperties, String relationshipType) {
		relationshipUtils.createRelationship(firstNodeLabel, firstNodeProperties, secondNodeLabel, secondNodeProperties, relationshipType);
	}
	
	public Set<String> createIndex(String label, Collection<String> properties) {
		return indexUtils.createIndex(label, properties);
	}
	
	public List<String> checkIndex(String label) {
		return indexUtils.getIndexes(label);
	}
	
}
