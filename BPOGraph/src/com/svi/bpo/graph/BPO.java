/**
 * Copyright (c) 2015, Software Ventures Internation Inc. and/or its affiliates. All rights reserved.
 * @title BPO Graph Version
 * @version 4.0
 */
package com.svi.bpo.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.svi.tools.neo4j.rest.service.Neo4jRestService;

/**
 * @author fmanangan
 * <br>
 * BPO Graph Version 4.0
 * <br>
 * <br>
 * <b>Sample Instantiation:</b>
 * <pre>
 * BPO bpo = new BPO("http://localhost:7474");
 * bpo.insertElement(parameters...);
 * </pre>
 */
public class BPO {
	private Neo4jRestService neo4j;
	private String graphUrl;
	private NodeFunctions nodeFunction;
	private ElementFunctions elementFunction;
	private ReportFunctions reportFunction;
	private ExceptionNodeFunctions exceptionNodeFunction;
	
	/**
	 * This class consist of methods to use BPO Graph, Methods are divided into different types
	 * such as:
	 * <ul>
	 * 	<li><b>CREATE: </b>Methods to Create Data Elements in BPO</li>
	 * 	<li><b>READ: </b>Methods to Read Data Elements from BPO</li>
	 * 	<li><b>UPDATE: </b>Methods to Update Data Elements from BPO</li>
	 * 	<li><b>DELETE: </b>Methods to Delete Data Elements from BPO</li>
	 * 	<li><b>REPORTS: </b>Methods to Get Reports, Performance Metrics of Data Elements in BPO</li>
	 * </ul>
	 * 
	 * DISCLAIMER: The tool is currently in Development Phase, Methods may change anytime based on the changing requirement
	 * @author: Fed 
	 * @since: 4.0
	 */
	   
	/**
	 * 
	 * @param graphUrl = URL of Graph Instance, for ex. "http://localhost:7474", "http://52.8.90.55:7474"
	 */
	public BPO(String graphUrl) {
		this.graphUrl = graphUrl;
		createBPOInstance();
	}
	
	
	private void createBPOInstance() {
		neo4j = new Neo4jRestService(this.graphUrl);
		buildIndex();
		
		nodeFunction = new NodeFunctions(neo4j, this);
		elementFunction = new ElementFunctions(neo4j, this);
		reportFunction = new ReportFunctions(this);
		exceptionNodeFunction = new ExceptionNodeFunctions(neo4j, this);
	}
	
	public NodeFunctions getNodeFunctions() {
		return nodeFunction;
	}
	
	public ElementFunctions getElementFunctions() {
		return elementFunction;
	}
	
	public ReportFunctions getReportFunctions() {
		return reportFunction;
	}
	
	public ExceptionNodeFunctions getExceptionNodeFunctions() {
		return exceptionNodeFunction;
	}
	
	/**
	 * Create Index for Node and Element Id for fast searching
	 */
	private void buildIndex() {
		List<String> nodeIndex = neo4j.checkIndex("NODE");
		List<String> elementIndex = neo4j.checkIndex("ELEMENT");
		
		if(!nodeIndex.contains("id")) {
			Set<String> tmp = new HashSet<>();
			tmp.add("id");
			neo4j.createIndex("NODE", tmp);
		}
		
		if(!elementIndex.contains("id")) {
			Set<String> tmp = new HashSet<>();
			tmp.add("id");
			neo4j.createIndex("ELEMENT", tmp);
		}
	}
	
	/**
	 * 
	 * @return Graph Instance Endpoint
	 */
	public String getEndpoint() {
		return this.graphUrl;
	}

	public void setEndpoint(String endPnt) {
		this.graphUrl = endPnt;
		createBPOInstance();
	}
	
	/********************
	 * CREATE FUNCTIONS *
	 ********************/
	
	

	
	
	
		
	/******************
	 * READ FUNCTIONS *
	 ******************/
	
	
	
	
	
	
	
	
	
	
	
	
	 
	
	/********************
	 * UPDATE FUNCTIONS *
	 ********************/
	
	
	
	
	
	
	
	
	
	/*//
	 * public String computeTargetCompletionTime(String cluster) {
		return nodeFunction.computeTargetCompletionTime(cluster);
	}*/
	
	/*public String computeTargetCompletionTime() {
		return nodeFunction.computeTargetCompletionTime();
	}*/
	
	/*public String computeEstimatedCompletionTime() {
		return nodeFunction.computeEstimatedCompletionTime();
	}*/
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	/*//TODO OPTIONAL?
	 * protected boolean incrementTotalWaitedElement(String nodeId, int count) {
		return nodeFunction.incrementTotalWaitedElement(nodeId, count);
	}
	
	protected boolean incrementTotalWaitingDuration(String nodeId, int count) {
		return nodeFunction.incrementTotalWaitingDuration(nodeId, count);
	}
	
	protected boolean incrementTotalProcessElement(String nodeId, int count) {
		return nodeFunction.incrementTotalProcessElement(nodeId, count);
	}
	
	protected boolean incrementTotalProcessDuration(String nodeId, int count) {
		return nodeFunction.incrementTotalProcessDuration(nodeId, count);
	}*/
	
	
	/********************
	 * DELETE FUNCTIONS *
	 ********************/
	
	
	
	
	
	
	/**
	 * <b>WARNING</b>: This method will Delete all data in the graph
	 * @return
	 */
	public boolean clearBPO() {
		String query = "MATCH (n)-[r]-(m) DELETE r,n,m; ";
		boolean deleteNodeRels = neo4j.sendCypherBooleanResult(query);
		
		query = "MATCH (n) DELETE n;";
		boolean deleteNodes = neo4j.sendCypherBooleanResult(query);
		
		if(!deleteNodeRels || !deleteNodes) {
			return false;
		} else {
			return true;
		}
	}

	// REPORT FUNCTION
	
	
	
	
	

	
	
}
