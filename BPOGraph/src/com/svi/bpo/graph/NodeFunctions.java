package com.svi.bpo.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.svi.bpo.graph.notifications.BPONotifications;
import com.svi.bpo.graph.obj.ClusterObject;
import com.svi.bpo.graph.obj.NodeObject;
import com.svi.bpo.graph.utils.DataUtilities;
import com.svi.tools.neo4j.rest.service.Neo4jRestService;

public class NodeFunctions {
	private static final String RELATIONSHIP_LABEL_NEXT_FLOW = "NEXT_FLOW";
	private static final String NODE_PROCESS_STATUS = "nodeProcessStatus";
	protected static final String REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS = "currentTotalInProcessElements";
	protected static final String REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS = "currentTotalWaitingElements";
	protected static final String REPORT_ATTR_TOTAL_PROCESS_DURATION = "totalProcessDuration";
	protected static final String REPORT_ATTR_TOTAL_PROCESS_ELEMENT = "totalProcessElement";
	protected static final String REPORT_ATTR_AVERAGE_PROCESS_DURATION = "averageProcessDuration";
	protected static final String REPORT_ATTR_TOTAL_WAITING_DURATION = "totalWaitingDuration";
	protected static final String REPORT_ATTR_TOTAL_WAITING_ELEMENT = "totalWaitingElement";
	protected static final String REPORT_ATTR_AVERAGE_WAITING_DURATION = "averageWaitingDuration";
	protected static final String REPORT_ATTR_TOTAL_ERROR_COUNT = "totalErrorCount";
	protected static final String REPORT_ATTR_TOTAL_OUTPUT_COUNT = "totalOutputCount";

	protected static final String RELATIONSHIP_LABEL_BPO_NODE_IN = "BPO_NODE_IN";
	protected static final String RELATIONSHIP_LABEL_REPORTING_OF = "BPO_REPORTING_OF";
	protected static final String NODE_LABEL_BPO_NODE = "BPO_NODE";
	protected static final String NODE_LABEL_CLUSTER = "BPO_CLUSTER";
	protected static final String NODE_LABEL_BPO_REPORT = "BPO_NODE_REPORT";
	
	protected static final String VAR_EXCEPTION_CODE_COLLECTION = "exceptionCodeCollection";
	
	/*******************
	 * NODE ATTRIBUTES *
	 *******************/
	protected static final String NODE_ATTR_NODE_EXCEPTION_OF = "nodeExceptionOf";// need
	protected static final String NODE_ATTR_ERROR_NODE = "errorNode";// need
	protected static final String NODE_ATTR_ALLOWED_ERROR = "allowedError";// need
	protected static final String NODE_ATTR_TARGET_OUTPUT = "targetOutput";// need
	protected static final String NODE_ATTR_ALLOWED_WAITING_DURATION = "allowedWaitingDuration";
	protected static final String NODE_ATTR_ALLOWED_PROCESS_DURATION = "allowedProcessDuration";
//	protected static final String NODE_ATTR_ALLOWED_AVE_PROC = "allowedAveProc";// need
//	protected static final String NODE_ATTR_ALLOWED_MAX_PROC = "allowedMaxProc";// neeed
//	protected static final String NODE_ATTR_ALLOWED_MIN_PROC = "allowedMinProc";//need
//	protected static final String NODE_ATTR_ALLOWED_AVE_WAIT = "allowedAveWait";//need
//	protected static final String NODE_ATTR_ALLOWED_MAX_WAIT = "allowedMaxWait";//need
//	protected static final String NODE_ATTR_ALLOWED_MIN_WAIT = "allowedMinWait";//need
	 static final String NODE_ATTR_STD_UNIT_MEASURE = "stdUnitOfMeasure";// need
	 static final String NODE_ATTR_CLUSTER = "cluster";// need
	 static final String NODE_ATTR_NODE_NAME = "nodeName";//need
	 static final String NODE_ATTR_NODE_ID = "nodeId";// need
	 static final String NODE_ATTR_COST = "cost";
	
	 static final String CLUSTER_ATTR_ID = "clusterId";
	 static final String CLUSTER_ATTR_TARGET_COMPLETION_TIME = "targetCompletionTime";
	 static final String CLUSTER_ATTR_ESTIMATED_COMPLETION_TIME = "targetEstimatedTime";
	
	/*******************
	 * NOTIFICATIONS ***
	 *******************/
	 static final String NOTIFICATION_NODE_DELETED_SUCCESSFULLY = "Node Deleted Successfully";
	 static final String NOTIFICATION_FAILED_CHECK_CONNECTION = "Failed, Check Connection";
	 static final String NOTIFICATION_NODE_IS_ADDED_SUCCESSFULLY = "Node is Added Successfully";
	 static final String NOTIFICATION_NODE_ALREADY_EXIST = "Node Already Exist";
	 static final String NOTIFICATION_NODE_ID_DOES_NOT_EXIST = "Node Id Does Not Exist";
	
	 static final String CONSTANT_PIPE = "|";
	
	private Neo4jRestService neo4j;
	
	//To use just incase node functions need to use other functions
	@SuppressWarnings("unused")
	private BPO bpo;
	
	public NodeFunctions(Neo4jRestService neo4j, BPO bpo) {
		this.neo4j = neo4j;
		this.bpo = bpo;
	}
	
	/********************
	 * CREATE FUNCTIONS *
	 ********************/
	/*@Deprecated
	 * BPO 317
	 String insertNode(String nodeId, String nodeName, String cluster, String stdUnitOfMeasure, String allowedMinWait, 
			String allowedMaxWait, String allowedAveWait, String allowedMinProc, String allowedMaxProc, String allowedAveProc, 
			String targetOutput, String allowedError, String errorNode, String nodeExceptionOf) {
		
		if(isNodeExist(nodeId)) {
			return NOTIFICATION_NODE_ALREADY_EXIST;
		}
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		properties.put(NODE_ATTR_NODE_NAME, nodeName);
		properties.put(NODE_ATTR_CLUSTER, cluster);
		properties.put(NODE_ATTR_STD_UNIT_MEASURE, stdUnitOfMeasure);
		properties.put(NODE_ATTR_ALLOWED_MIN_WAIT, allowedMinWait);
		properties.put(NODE_ATTR_ALLOWED_MAX_WAIT, allowedMaxWait);
		properties.put(NODE_ATTR_ALLOWED_AVE_WAIT, allowedAveWait);
		properties.put(NODE_ATTR_ALLOWED_MIN_PROC, allowedMinProc);
		properties.put(NODE_ATTR_ALLOWED_MAX_PROC, allowedMaxProc);
		properties.put(NODE_ATTR_ALLOWED_AVE_PROC, allowedAveProc);
		properties.put(NODE_ATTR_TARGET_OUTPUT, targetOutput);
		properties.put(NODE_ATTR_ALLOWED_ERROR, allowedError);
		properties.put(NODE_ATTR_ERROR_NODE, errorNode);
		properties.put(NODE_ATTR_NODE_EXCEPTION_OF, nodeExceptionOf);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MERGE (node:"+"NODE"+" {"+NODE_ATTR_NODE_ID+":{"+NODE_ATTR_NODE_ID+"}}) ");
		qb.append("SET node."+NODE_ATTR_NODE_NAME+" = {"+NODE_ATTR_NODE_NAME+"} ");
		qb.append("SET node."+NODE_ATTR_CLUSTER+" = {"+NODE_ATTR_CLUSTER+"} ");
		qb.append("SET node."+NODE_ATTR_STD_UNIT_MEASURE+" = {"+NODE_ATTR_STD_UNIT_MEASURE+"} ");
		qb.append("SET node."+NODE_ATTR_ALLOWED_MIN_PROC+"= {"+NODE_ATTR_ALLOWED_MIN_PROC+"} ");
		qb.append("SET node."+NODE_ATTR_ALLOWED_MAX_WAIT+" = {"+NODE_ATTR_ALLOWED_MAX_WAIT+"} ");
		qb.append("SET node."+NODE_ATTR_ALLOWED_AVE_WAIT+" = {"+NODE_ATTR_ALLOWED_AVE_WAIT+"} ");
		qb.append("SET node."+NODE_ATTR_ALLOWED_AVE_PROC+" = {"+NODE_ATTR_ALLOWED_AVE_PROC+"} ");
		qb.append("SET node."+NODE_ATTR_ALLOWED_MAX_PROC+" = {"+NODE_ATTR_ALLOWED_MAX_PROC+"} ");
		qb.append("SET node."+NODE_ATTR_ALLOWED_AVE_PROC+" = {"+NODE_ATTR_ALLOWED_AVE_PROC+"} ");
		qb.append("SET node."+NODE_ATTR_TARGET_OUTPUT+" = {"+NODE_ATTR_TARGET_OUTPUT+"} ");
		qb.append("SET node."+NODE_ATTR_ALLOWED_ERROR+" = {"+NODE_ATTR_ALLOWED_ERROR+"} ");
		qb.append("SET node."+NODE_ATTR_ERROR_NODE+" = {"+NODE_ATTR_ERROR_NODE+"} ");
		qb.append("SET node."+NODE_ATTR_NODE_EXCEPTION_OF+" = {"+NODE_ATTR_NODE_EXCEPTION_OF+"} ");
		
		neo4j.sendCypherQuery(qb.toString(), properties);
		
		return NOTIFICATION_NODE_IS_ADDED_SUCCESSFULLY;
	}*/
	
	/**
	 * Insert Node to BPO
	 * @param nodeId - Unique Identification of the Node
	 * @param nodeName - Node Name/Description, To be display on reports
	 * @param cluster - Group of Nodes
	 * @param stdUnitOfMeasure - Unit used to describe output count for ex. (Box, Page, Transaction, Record, Lines of Code and etc.)
	 * @param cost - Cost to Process Each Element
	 * @param allowedWaitingTime - in SECONDS
	 * @param allowedProcessTime - in SECONDS
	 * @param targetOutputCount
	 * @param allowedErrorCount
	 * @return Notification of Node Creation Process
	 */
	public String insertNode(String nodeId, String nodeName, String cluster, String stdUnitOfMeasure, double cost, long allowedWaitingTime, long allowedProcessTime,
			int targetOutputCount, int allowedErrorCount) {
		
		if(isNodeExist(nodeId)) {
			return NOTIFICATION_NODE_ALREADY_EXIST;
		}
		
		// Convert Seconds to Milliseconds
		allowedWaitingTime = allowedWaitingTime * 1000;
		allowedProcessTime = allowedProcessTime * 1000;
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		properties.put(NODE_ATTR_NODE_NAME, nodeName);
		properties.put(NODE_ATTR_STD_UNIT_MEASURE, stdUnitOfMeasure);
		properties.put(NODE_ATTR_ALLOWED_WAITING_DURATION, allowedWaitingTime);
		properties.put(NODE_ATTR_ALLOWED_PROCESS_DURATION, allowedProcessTime);
		properties.put(NODE_ATTR_TARGET_OUTPUT, targetOutputCount);
		properties.put(NODE_ATTR_ALLOWED_ERROR, allowedErrorCount);
		properties.put(NODE_ATTR_COST, cost);
		properties.put(CLUSTER_ATTR_ID, cluster);
		properties.put(CLUSTER_ATTR_TARGET_COMPLETION_TIME, (allowedWaitingTime+allowedProcessTime));
		
		StringBuilder qb = new StringBuilder();
		qb.append("MERGE (cluster:" +NODE_LABEL_CLUSTER+ " {"+CLUSTER_ATTR_ID+":{"+CLUSTER_ATTR_ID+"}}) ");
		qb.append("ON CREATE SET cluster."+CLUSTER_ATTR_TARGET_COMPLETION_TIME+"=0 ");
		qb.append("MERGE (node:"+NODE_LABEL_BPO_NODE+" {"+NODE_ATTR_NODE_ID+":{"+NODE_ATTR_NODE_ID+"}}) ");
		qb.append("MERGE node-[:"+RELATIONSHIP_LABEL_BPO_NODE_IN+"]->cluster ");
		qb.append("CREATE (report:"+NODE_LABEL_BPO_REPORT+") ");
		qb.append("CREATE report-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]->node ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_OUTPUT_COUNT+"=0 ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_ERROR_COUNT+"=0 ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_PROCESS_DURATION+"=0 ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_PROCESS_ELEMENT+"=0 ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_WAITING_ELEMENT+"=0 ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_WAITING_DURATION+"=0 ");
		qb.append("SET report."+REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=0 ");
		qb.append("SET report."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"=0 ");
		qb.append("SET report."+REPORT_ATTR_AVERAGE_PROCESS_DURATION+"=0 ");
		qb.append("SET report."+REPORT_ATTR_AVERAGE_WAITING_DURATION+"=0 ");
		qb.append("SET node."+NODE_ATTR_NODE_NAME+" = {"+NODE_ATTR_NODE_NAME+"} ");
		qb.append("SET node."+NODE_ATTR_STD_UNIT_MEASURE+" = {"+NODE_ATTR_STD_UNIT_MEASURE+"} ");
		qb.append("SET node."+NODE_ATTR_ALLOWED_WAITING_DURATION+"= {"+NODE_ATTR_ALLOWED_WAITING_DURATION+"} ");
		qb.append("SET node."+NODE_ATTR_ALLOWED_PROCESS_DURATION+" = {"+NODE_ATTR_ALLOWED_PROCESS_DURATION+"} ");
		qb.append("SET node."+NODE_ATTR_TARGET_OUTPUT+" = {"+NODE_ATTR_TARGET_OUTPUT+"} ");
		qb.append("SET node."+NODE_ATTR_COST+" = {"+NODE_ATTR_COST+"} ");
		qb.append("SET node."+NODE_ATTR_ALLOWED_ERROR+" = {"+NODE_ATTR_ALLOWED_ERROR+"} ");
		qb.append("SET cluster."+CLUSTER_ATTR_TARGET_COMPLETION_TIME+"=cluster."+CLUSTER_ATTR_TARGET_COMPLETION_TIME+"+{"+CLUSTER_ATTR_TARGET_COMPLETION_TIME+"}");
		neo4j.sendCypherQuery(qb.toString(), properties);
		
		return NOTIFICATION_NODE_IS_ADDED_SUCCESSFULLY;
	}
	
	public BPONotifications buildFlow(String cluster, String... followingNodeIds) {
		List<String> tmp = new LinkedList<>(Arrays.asList(followingNodeIds));
		if(checkIfExceptionCodesAllExist(cluster, tmp)) {
			Map<String, Object> properties = new HashMap<>();
			properties.put(CLUSTER_ATTR_ID, cluster);
			StringBuilder qb = new StringBuilder();
			qb.append("MATCH (node:"+NODE_LABEL_BPO_NODE+")-[:"+RELATIONSHIP_LABEL_BPO_NODE_IN+"]->(cluster:"+NODE_LABEL_CLUSTER+") ");
			qb.append("WHERE cluster."+CLUSTER_ATTR_ID+"={"+CLUSTER_ATTR_ID+"} ");
			qb.append("WITH cluster, node ");
			int flowNodeSize = followingNodeIds.length;
			
			StringBuilder mergeQuery = new StringBuilder();
			for(int i=0; i<flowNodeSize; i++) {
				String tmpVar = "node"+i;
				properties.put(tmpVar, followingNodeIds[i]);
				qb.append("MATCH cluster<-[:"+RELATIONSHIP_LABEL_BPO_NODE_IN+"]-("+tmpVar+":"+NODE_LABEL_BPO_NODE+" {"+NODE_ATTR_NODE_ID+":{"+tmpVar+"}}) ");
				if(i>0) {
					String prevNode = "node"+(i-1);
					mergeQuery.append("MERGE "+prevNode+"-[:"+RELATIONSHIP_LABEL_NEXT_FLOW+"]->"+tmpVar+" ");
				}
			}
			qb.append(mergeQuery);
			boolean isSuccess = neo4j.sendCypherBooleanResult(qb.toString(), properties);
			if(isSuccess) {
				return BPONotifications.EXCEPTION_FLOW_CREATE_SUCCESS;
			}
		}
		
		return BPONotifications.EXCEPTION_LIST_DOES_NOT_EXIST;
	}
	
	private boolean checkIfExceptionCodesAllExist(String cluster, List<String> exceptionCodesToCheck) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(CLUSTER_ATTR_ID, cluster);
		properties.put(VAR_EXCEPTION_CODE_COLLECTION, exceptionCodesToCheck);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NODE_LABEL_BPO_NODE+")-[:"+RELATIONSHIP_LABEL_BPO_NODE_IN+"]->(cluster:"+NODE_LABEL_CLUSTER+") ");
		qb.append("WHERE cluster."+CLUSTER_ATTR_ID+"={"+CLUSTER_ATTR_ID+"} AND node."+NODE_ATTR_NODE_ID+" IN {"+VAR_EXCEPTION_CODE_COLLECTION+"} ");
		qb.append("WITH COLLECT (node."+NODE_ATTR_NODE_ID+") AS nodeIds ");
		qb.append("RETURN ALL(x IN {"+VAR_EXCEPTION_CODE_COLLECTION+"} WHERE x IN nodeIds) AS result;");
		
		List<Map<String, Object>> dbResult = neo4j.sendCypherQuery(qb.toString(), properties);
		if(dbResult.size()>0) {
			Map<String, Object> dbRow = dbResult.get(0);
			boolean result = DataUtilities.toBoolean(dbRow.get("result"));
			if(result == true) {
				return true;
			}
		}
		return false;
	}
	
	/******************
	 * READ FUNCTIONS *
	 ******************/
	
	/**
	 * This method will return List of All Node Id and Node Name
	 * @return String Array of NodeId|NodeName
	 */
	@Deprecated
	public String[] viewNodeTable() {
		String query = "MATCH (n:"+NODE_LABEL_BPO_NODE+") RETURN n."+NODE_ATTR_NODE_ID+" AS id, n."+NODE_ATTR_NODE_NAME+" AS nodeName";
		List<Map<String, Object>> data = neo4j.sendCypherQuery(query);
		int size = data.size();
		String[] dataToReturn = new String[size];
		
		for(int i=0; i<size; i++) {
			Map<String, Object> tmp = data.get(i);
			String id =  tmp.get("id").toString();
			System.out.println("ID: " + id);
			String nodeName = (String) tmp.get("nodeName");
			dataToReturn[i] = id+CONSTANT_PIPE+nodeName;
		}
		return dataToReturn;
	}
	
	/**
	 * View NodeNames
	 * @return String Array of NodeNames
	 */
	public String[] viewNodeNameList() {
		String query = "MATCH (n:"+NODE_LABEL_BPO_NODE+") RETURN n."+NODE_ATTR_NODE_NAME+" AS nodeName";
		List<Map<String, Object>> result = neo4j.sendCypherQuery(query);
		String[] dataToReturn = new String[result.size()];
		for(int i=0; i<result.size(); i++) {
			String nodeName = (String) result.get(i).get("nodeName");
			dataToReturn[i] = nodeName;
		}
		return dataToReturn;
	}
	
	/**
	 * View Node Ids
	 * @return String Array of Node Ids
	 */
	public String[] viewNodeIdList() {
		String query = "MATCH (n:"+NODE_LABEL_BPO_NODE+") RETURN n.id AS nodeId";
		List<Map<String, Object>> result = neo4j.sendCypherQuery(query);
		String[] dataToReturn = new String[result.size()];
		for(int i=0; i<result.size(); i++) {
			String nodeId = (String) result.get(i).get("nodeId");
			dataToReturn[i] = nodeId;
		}
		return dataToReturn;
	}
	
	/**
	 * Check if Node Exist by Matching @param nodeId to Existing Nodes 
	 * @param nodeId
	 * @return
	 */
	public boolean isNodeExist(String nodeId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NODE_LABEL_BPO_NODE+" {"+NODE_ATTR_NODE_ID+":{"+NODE_ATTR_NODE_ID+"}}) RETURN COUNT(*) AS data");
		String query = qb.toString();
		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(query, properties);
		if(dataReturned!=null) {
			int data = (int) dataReturned.get(0).get("data");
			if(data>0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * View All Nodes
	 * @param nodeId, Put * to get All Nodes
	 * @return Node Object
	 */
	public NodeObject[] viewNodes(String nodeId) {
		Map<String, Object> properties = new HashMap<>();//node-[:"+RELATIONSHIP_LABEL_BPO_NODE_IN+"]->cluster
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NODE_LABEL_BPO_NODE+")-[:"+RELATIONSHIP_LABEL_BPO_NODE_IN+"]->(cluster:"+NODE_LABEL_CLUSTER+") ");
		qb.append("MATCH node<-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		if(!nodeId.contains("*")) {
			qb.append("WHERE node."+NODE_ATTR_NODE_ID+"={"+NODE_ATTR_NODE_ID+"} ");
		}
		
		qb.append("RETURN node."+NODE_ATTR_NODE_ID+" AS "+NODE_ATTR_NODE_ID+", ");
		qb.append("node."+NODE_ATTR_NODE_NAME+" AS "+ NODE_ATTR_NODE_NAME+", ");
		qb.append("cluster."+CLUSTER_ATTR_ID+" AS "+ CLUSTER_ATTR_ID+", ");
		qb.append("node."+NODE_ATTR_STD_UNIT_MEASURE+" AS "+ NODE_ATTR_STD_UNIT_MEASURE+", ");
		qb.append("node."+NODE_ATTR_ALLOWED_ERROR+" AS "+ NODE_ATTR_ALLOWED_ERROR+", ");
		qb.append("node."+NODE_ATTR_TARGET_OUTPUT+" AS "+ NODE_ATTR_TARGET_OUTPUT+", ");
		qb.append("node."+NODE_ATTR_ALLOWED_WAITING_DURATION+" AS "+ NODE_ATTR_ALLOWED_WAITING_DURATION+", ");
		qb.append("node."+NODE_ATTR_ALLOWED_PROCESS_DURATION+" AS "+ NODE_ATTR_ALLOWED_PROCESS_DURATION+", ");
		qb.append("node."+NODE_ATTR_COST+" AS "+ NODE_ATTR_COST+", ");
		qb.append("report."+REPORT_ATTR_TOTAL_OUTPUT_COUNT+" AS "+ REPORT_ATTR_TOTAL_OUTPUT_COUNT+", ");
		qb.append("report."+REPORT_ATTR_TOTAL_ERROR_COUNT+" AS "+ REPORT_ATTR_TOTAL_ERROR_COUNT+", ");
		qb.append("report."+REPORT_ATTR_AVERAGE_PROCESS_DURATION+" AS "+ REPORT_ATTR_AVERAGE_PROCESS_DURATION+", ");
		qb.append("report."+REPORT_ATTR_AVERAGE_WAITING_DURATION+" AS "+ REPORT_ATTR_AVERAGE_WAITING_DURATION+", ");
		qb.append("report."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+" AS "+ REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+", ");
		qb.append("report."+REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+" AS "+ REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+", ");
		qb.append("report."+REPORT_ATTR_TOTAL_PROCESS_DURATION+" AS "+ REPORT_ATTR_TOTAL_PROCESS_DURATION+", ");
		qb.append("report."+REPORT_ATTR_TOTAL_PROCESS_ELEMENT+" AS "+ REPORT_ATTR_TOTAL_PROCESS_ELEMENT+", ");
		qb.append("report."+REPORT_ATTR_TOTAL_WAITING_DURATION+" AS "+ REPORT_ATTR_TOTAL_WAITING_DURATION+", ");
		qb.append("report."+REPORT_ATTR_TOTAL_WAITING_ELEMENT+" AS "+ REPORT_ATTR_TOTAL_WAITING_ELEMENT+", ");
		qb.append("CASE (node."+NODE_ATTR_ALLOWED_WAITING_DURATION+"+node."+NODE_ATTR_ALLOWED_PROCESS_DURATION+") >= (report."+REPORT_ATTR_AVERAGE_WAITING_DURATION+"+report."+REPORT_ATTR_AVERAGE_PROCESS_DURATION+") WHEN TRUE THEN TRUE WHEN FALSE THEN FALSE END AS "+NODE_PROCESS_STATUS+";");
		
		List<Map<String, Object>> dbResult = neo4j.sendCypherQuery(qb.toString(), properties);
		NodeObject[] dataToReturn = new NodeObject[dbResult.size()];
		for(int i=0; i<dbResult.size(); i++) {
			Map<String, Object> eachNode = dbResult.get(i);
			NodeObject node = new NodeObject();
			node.setAllowedError(DataUtilities.toInteger(eachNode.get(NODE_ATTR_ALLOWED_ERROR)));
			node.setAllowedProcessingDuration(DataUtilities.toLongValue(eachNode.get(NODE_ATTR_ALLOWED_PROCESS_DURATION)));
			node.setAllowedWaitingDuration(DataUtilities.toLongValue(eachNode.get(NODE_ATTR_ALLOWED_WAITING_DURATION)));
			node.setAverageProcessDuration(DataUtilities.toLongValue(eachNode.get(REPORT_ATTR_AVERAGE_PROCESS_DURATION)));
			node.setAverageWaitingDuration(DataUtilities.toLongValue(eachNode.get(REPORT_ATTR_AVERAGE_WAITING_DURATION)));
			node.setClusterId(eachNode.get(CLUSTER_ATTR_ID).toString());

			node.setCost(DataUtilities.toDoubleValue(eachNode.get(NODE_ATTR_COST)));
			node.setCurrentTotalInProcessElements(DataUtilities.toInteger(eachNode.get(REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS)));
			node.setCurrentTotalWaitingElements(DataUtilities.toInteger(eachNode.get(REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS)));

			node.setNodeId(eachNode.get(NODE_ATTR_NODE_ID).toString());
			node.setNodeName(eachNode.get(NODE_ATTR_NODE_NAME).toString());
			node.setCanMeetTarget(DataUtilities.toBoolean(eachNode.get(NODE_PROCESS_STATUS)));
			node.setStdUnitOfMeasure(eachNode.get(NODE_ATTR_STD_UNIT_MEASURE).toString());
			node.setTargetOutput(DataUtilities.toInteger(eachNode.get(NODE_ATTR_TARGET_OUTPUT)));
			node.setTotalErrorCount(DataUtilities.toInteger(eachNode.get(REPORT_ATTR_TOTAL_ERROR_COUNT)));
			node.setTotalOutputCount(DataUtilities.toInteger(eachNode.get(REPORT_ATTR_TOTAL_OUTPUT_COUNT)));
			node.setTotalProcessDuration(DataUtilities.toLongValue(eachNode.get(REPORT_ATTR_TOTAL_PROCESS_DURATION)));
			node.setTotalWaitingDuration(DataUtilities.toLongValue(eachNode.get(REPORT_ATTR_TOTAL_WAITING_DURATION)));
			node.setTotalWaitingElement(DataUtilities.toInteger(eachNode.get(REPORT_ATTR_TOTAL_WAITING_ELEMENT)));
			dataToReturn[i] = node;
		}
		
		return dataToReturn;
	}

	/**
	 * Get Nodes by Cluster Id
	 * @param clusterId, Put * to select all Cluster
	 * @return Array of Node Object
	 */
	public NodeObject[] viewNodeByClusterId(String clusterId) {
		Map<String, Object> properties = new HashMap<>();//node-[:"+RELATIONSHIP_LABEL_BPO_NODE_IN+"]->cluster
		properties.put(CLUSTER_ATTR_ID, clusterId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NODE_LABEL_BPO_NODE+")-[:"+RELATIONSHIP_LABEL_BPO_NODE_IN+"]->(cluster:"+NODE_LABEL_CLUSTER+") ");
		qb.append("MATCH node<-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		if(!clusterId.contains("*")) {
			qb.append("WHERE cluster."+CLUSTER_ATTR_ID+"={"+CLUSTER_ATTR_ID+"} ");
		}
		
		qb.append("RETURN node."+NODE_ATTR_NODE_ID+" AS "+NODE_ATTR_NODE_ID+", ");
		qb.append("node."+NODE_ATTR_NODE_NAME+" AS "+ NODE_ATTR_NODE_NAME+", ");
		qb.append("cluster."+CLUSTER_ATTR_ID+" AS "+ CLUSTER_ATTR_ID+", ");
		qb.append("node."+NODE_ATTR_STD_UNIT_MEASURE+" AS "+ NODE_ATTR_STD_UNIT_MEASURE+", ");
		qb.append("node."+NODE_ATTR_ALLOWED_ERROR+" AS "+ NODE_ATTR_ALLOWED_ERROR+", ");
		qb.append("node."+NODE_ATTR_TARGET_OUTPUT+" AS "+ NODE_ATTR_TARGET_OUTPUT+", ");
		qb.append("node."+NODE_ATTR_ALLOWED_WAITING_DURATION+" AS "+ NODE_ATTR_ALLOWED_WAITING_DURATION+", ");
		qb.append("node."+NODE_ATTR_ALLOWED_PROCESS_DURATION+" AS "+ NODE_ATTR_ALLOWED_PROCESS_DURATION+", ");
		qb.append("node."+NODE_ATTR_COST+" AS "+ NODE_ATTR_COST+", ");
		qb.append("report."+REPORT_ATTR_TOTAL_OUTPUT_COUNT+" AS "+ REPORT_ATTR_TOTAL_OUTPUT_COUNT+", ");
		qb.append("report."+REPORT_ATTR_TOTAL_ERROR_COUNT+" AS "+ REPORT_ATTR_TOTAL_ERROR_COUNT+", ");
		qb.append("report."+REPORT_ATTR_AVERAGE_PROCESS_DURATION+" AS "+ REPORT_ATTR_AVERAGE_PROCESS_DURATION+", ");
		qb.append("report."+REPORT_ATTR_AVERAGE_WAITING_DURATION+" AS "+ REPORT_ATTR_AVERAGE_WAITING_DURATION+", ");
		qb.append("report."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+" AS "+ REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+", ");
		qb.append("report."+REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+" AS "+ REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+", ");
		qb.append("report."+REPORT_ATTR_TOTAL_PROCESS_DURATION+" AS "+ REPORT_ATTR_TOTAL_PROCESS_DURATION+", ");
		qb.append("report."+REPORT_ATTR_TOTAL_PROCESS_ELEMENT+" AS "+ REPORT_ATTR_TOTAL_PROCESS_ELEMENT+", ");
		qb.append("report."+REPORT_ATTR_TOTAL_WAITING_DURATION+" AS "+ REPORT_ATTR_TOTAL_WAITING_DURATION+", ");
		qb.append("report."+REPORT_ATTR_TOTAL_WAITING_ELEMENT+" AS "+ REPORT_ATTR_TOTAL_WAITING_ELEMENT+", ");
		qb.append("CASE (node."+NODE_ATTR_ALLOWED_WAITING_DURATION+"+node."+NODE_ATTR_ALLOWED_PROCESS_DURATION+") >= (report."+REPORT_ATTR_AVERAGE_WAITING_DURATION+"+report."+REPORT_ATTR_AVERAGE_PROCESS_DURATION+") WHEN TRUE THEN TRUE WHEN FALSE THEN FALSE END AS "+NODE_PROCESS_STATUS+";");
		
		List<Map<String, Object>> dbResult = neo4j.sendCypherQuery(qb.toString(), properties);
		NodeObject[] dataToReturn = new NodeObject[dbResult.size()];
		for(int i=0; i<dbResult.size(); i++) {
			Map<String, Object> eachNode = dbResult.get(i);
			NodeObject node = new NodeObject();
			node.setAllowedError(DataUtilities.toInteger(eachNode.get(NODE_ATTR_ALLOWED_ERROR)));
			node.setAllowedProcessingDuration(DataUtilities.toLongValue(eachNode.get(NODE_ATTR_ALLOWED_PROCESS_DURATION)));
			node.setAllowedWaitingDuration(DataUtilities.toLongValue(eachNode.get(NODE_ATTR_ALLOWED_WAITING_DURATION)));
			node.setAverageProcessDuration(DataUtilities.toLongValue(eachNode.get(REPORT_ATTR_AVERAGE_PROCESS_DURATION)));
			node.setAverageWaitingDuration(DataUtilities.toLongValue(eachNode.get(REPORT_ATTR_AVERAGE_WAITING_DURATION)));
			node.setClusterId(eachNode.get(CLUSTER_ATTR_ID).toString());

			node.setCost(DataUtilities.toDoubleValue(eachNode.get(NODE_ATTR_COST)));
			node.setCurrentTotalInProcessElements(DataUtilities.toInteger(eachNode.get(REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS)));
			node.setCurrentTotalWaitingElements(DataUtilities.toInteger(eachNode.get(REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS)));

			node.setNodeId(eachNode.get(NODE_ATTR_NODE_ID).toString());
			node.setNodeName(eachNode.get(NODE_ATTR_NODE_NAME).toString());
			node.setCanMeetTarget(DataUtilities.toBoolean(eachNode.get(NODE_PROCESS_STATUS)));
			node.setStdUnitOfMeasure(eachNode.get(NODE_ATTR_STD_UNIT_MEASURE).toString());
			node.setTargetOutput(DataUtilities.toInteger(eachNode.get(NODE_ATTR_TARGET_OUTPUT)));
			node.setTotalErrorCount(DataUtilities.toInteger(eachNode.get(REPORT_ATTR_TOTAL_ERROR_COUNT)));
			node.setTotalOutputCount(DataUtilities.toInteger(eachNode.get(REPORT_ATTR_TOTAL_OUTPUT_COUNT)));
			node.setTotalProcessDuration(DataUtilities.toLongValue(eachNode.get(REPORT_ATTR_TOTAL_PROCESS_DURATION)));
			node.setTotalWaitingDuration(DataUtilities.toLongValue(eachNode.get(REPORT_ATTR_TOTAL_WAITING_DURATION)));
			node.setTotalWaitingElement(DataUtilities.toInteger(eachNode.get(REPORT_ATTR_TOTAL_WAITING_ELEMENT)));
			dataToReturn[i] = node;
		}
		
		return dataToReturn;
	}
	
	/**
	 * This method Will get elements who exceeded the allowed waiting time of a node. 
	 * <br> <br>
	 * @return <b>List</b> of Rows where each member is a <b>Map of Columns</b>;
	 * <br>
	 * <b><i>COLUMN KEYS: </i></b> 
	 * <br> 
	 * <ul>
		 * <li>workerId</li>
		 * <li>nodeId</li>
		 * <li>elementId</li>
	 * <ul>
	 *//*
	public List<Map<String, Object>> getElementsWithExceededWaitingTime() {
		String query = "WITH TIMESTAMP() AS currentTime "
				+ "MATCH (element:ELEMENT)-[r:TASK_AT]->(node:"+NODE_LABEL_BPO_NODE+") "
				+ "WHERE (currentTime - r.startWaitingTime)>TOINT(node.allowedMaxWait) "
				+ "RETURN r.workerId AS workerId, node."+NODE_ATTR_NODE_ID+" AS nodeId, element."+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+" AS elementId " 
				+ "UNION "
				+ "MATCH (element:ELEMENT)-[r:COMPLETED_TASK]->(node:"+NODE_LABEL_BPO_NODE+") "
				+ "WHERE r.waitingDuration>TOINT(node.allowedMaxWait) "
				+ "RETURN r.workerId AS workerId, node."+NODE_ATTR_NODE_ID+" AS nodeId, element."+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+" AS elementId;";
		return neo4j.sendCypherQuery(query);
	}*/
	
	public boolean isClusterExist(String clusterId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(CLUSTER_ATTR_ID, clusterId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NODE_LABEL_CLUSTER+" {"+CLUSTER_ATTR_ID+":{"+CLUSTER_ATTR_ID+"}}) RETURN COUNT(*) AS data");
		String query = qb.toString();
		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(query, properties);
		if(dataReturned!=null) {
			int data = (int) dataReturned.get(0).get("data");
			if(data>0) {
				return true;
			}
		}
		return false;
	}
	
	public ClusterObject[] viewClusters() {
		String query = "MATCH (cluster:"+NODE_LABEL_CLUSTER+") RETURN cluster."+CLUSTER_ATTR_TARGET_COMPLETION_TIME + " AS " +CLUSTER_ATTR_TARGET_COMPLETION_TIME +", cluster."+CLUSTER_ATTR_ID+ " AS " + CLUSTER_ATTR_ID;
		List<Map<String, Object>> dbResult = neo4j.sendCypherQuery(query);
		int resultSize = dbResult.size();
		ClusterObject[] dataToReturn = new ClusterObject[resultSize];
		for(int i=0; i<resultSize; i++) {
			Map<String, Object> each = dbResult.get(i);
			ClusterObject tmp = new ClusterObject();
			String clusterId = DataUtilities.toStringValue(each.get(CLUSTER_ATTR_ID));
			long targetCompletionTime = DataUtilities.toLongValue(each.get(CLUSTER_ATTR_TARGET_COMPLETION_TIME));
			tmp.setClusterId(clusterId);
			tmp.setTargetCompletionTime(targetCompletionTime);
			dataToReturn[i] = tmp;
		}
		return dataToReturn;
	}
	/********************
	 * UPDATE FUNCTIONS *
	 ********************/
	
	/**
	 * Increment Node Total Output Count
	 * @param nodeId
	 * @param outputCount
	 * @return TRUE if Success, False otherwise
	 */
	public boolean incrementNodeOutputCount(String nodeId, int outputCount) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		properties.put(REPORT_ATTR_TOTAL_OUTPUT_COUNT, outputCount);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NODE_LABEL_BPO_NODE+" {"+NODE_ATTR_NODE_ID+":{"+NODE_ATTR_NODE_ID+"}})<-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_OUTPUT_COUNT+"=report."+REPORT_ATTR_TOTAL_OUTPUT_COUNT+"+{"+REPORT_ATTR_TOTAL_OUTPUT_COUNT+"};");
		return neo4j.sendCypherBooleanResult(qb.toString(), properties);
	}
	
	/**
	 * Increment Node Total Error Count
	 * @param nodeId
	 * @param errorCount
	 * @return TRUE if Success, False otherwise = May Node Id does Not Exist or Database Connect Error
	 */
	public BPONotifications incrementNodeErrorCount(String nodeId, int errorCount) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		properties.put(REPORT_ATTR_TOTAL_ERROR_COUNT, errorCount);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NODE_LABEL_BPO_NODE+" {"+NODE_ATTR_NODE_ID+":{"+NODE_ATTR_NODE_ID+"}})<-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_ERROR_COUNT+"=report."+REPORT_ATTR_TOTAL_ERROR_COUNT+"+{"+REPORT_ATTR_TOTAL_ERROR_COUNT+"} ");
		qb.append("RETURN COUNT(report) AS data;");
		List<Map<String, Object>> dbResult = neo4j.sendCypherQuery(qb.toString(), properties);
		if(dbResult.size()>0) {
			int status = DataUtilities.toInteger(dbResult.get(0).get("data"));
			if(status>0) {
				return BPONotifications.NODE_ERROR_COUNT_INCREMENT_SUCCESS;
			}
		}
		
		return BPONotifications.NODE_DOES_NOT_EXIST;
	}
	
	 String computeTargetCompletionTime(String cluster) {
		if(isClusterExist(cluster)) {
			Map<String, Object> properties = new HashMap<>();
			properties.put(CLUSTER_ATTR_ID, cluster);
			StringBuilder qb = new StringBuilder();
			qb.append("MATCH (cluster:"+NODE_LABEL_CLUSTER+ " {"+CLUSTER_ATTR_ID+":{"+CLUSTER_ATTR_ID+"}})<-[:"+RELATIONSHIP_LABEL_BPO_NODE_IN+"]-(node:"+NODE_LABEL_BPO_NODE+") ");
			qb.append("WITH SUM(node."+NODE_ATTR_ALLOWED_WAITING_DURATION+") AS totalWaitingTime,");
			qb.append("SUM(node."+NODE_ATTR_ALLOWED_PROCESS_DURATION+") + ");
			qb.append("SUM(node."+NODE_ATTR_ALLOWED_WAITING_DURATION+") AS targetCompletionTime, cluster ");
			qb.append("SET cluster."+CLUSTER_ATTR_TARGET_COMPLETION_TIME+"=targetCompletionTime;");
			boolean queryResult = neo4j.sendCypherBooleanResult(qb.toString(), properties);
			if(queryResult) {
				return "Target Completion Time Computed Successfully";
			} else {
				return "Failed Check Connection";
			}
		} else {
			return "Cluster: " + cluster + " Does not Exist";
		}
	}
	
	protected String computeTargetCompletionTime() {
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (cluster:"+NODE_LABEL_CLUSTER+ ")<-[:"+RELATIONSHIP_LABEL_BPO_NODE_IN+"]-(node:"+NODE_LABEL_BPO_NODE+") ");
		qb.append("WITH SUM(node."+NODE_ATTR_ALLOWED_WAITING_DURATION+") AS totalWaitingTime,");
		qb.append("SUM(node."+NODE_ATTR_ALLOWED_PROCESS_DURATION+") + ");
		qb.append("SUM(node."+NODE_ATTR_ALLOWED_WAITING_DURATION+") AS targetCompletionTime, cluster ");
		qb.append("SET cluster."+CLUSTER_ATTR_TARGET_COMPLETION_TIME+"=targetCompletionTime;");
		boolean queryResult = neo4j.sendCypherBooleanResult(qb.toString());
		if(queryResult) {
			return "Target Completion Time Computed Successfully";
		} else {
			return "Failed Check Connection";
		}
	}
	
	//TODO HERE
	protected String computeEstimatedCompletionTime() {
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (cluster:"+NODE_LABEL_CLUSTER+")<-[:"+RELATIONSHIP_LABEL_BPO_NODE_IN+"]-(node:"+NODE_LABEL_BPO_NODE+") ");
		qb.append("MATCH (report:"+NODE_LABEL_BPO_REPORT+")-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]->node ");
		qb.append("WITH SUM(report."+REPORT_ATTR_AVERAGE_WAITING_DURATION+") AS totalAverageWaitingDuration, ");
		qb.append("SUM(report."+REPORT_ATTR_AVERAGE_PROCESS_DURATION+") AS totalAverageProcessDuration, cluster  ");
		qb.append("SET cluster."+CLUSTER_ATTR_ESTIMATED_COMPLETION_TIME+"=totalAverageWaitingDuration+totalAverageProcessDuration;");
		System.out.println(qb);
		boolean queryResult = neo4j.sendCypherBooleanResult(qb.toString());
		if(queryResult) {
			return "Estimated Completion Time Computed Successfully";
		} else {
			return "Failed Check Connection";
		}
	}
	
	protected void computeIncompleteElementDuration() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ElementFunctions.CONSTANT_STATUS_WAITING, ElementFunctions.CONSTANT_STATUS_WAITING);
		properties.put(ElementFunctions.CONSTANT_STATUS_IN_PROCESS, ElementFunctions.CONSTANT_STATUS_IN_PROCESS);
		StringBuilder qb = new StringBuilder();
		qb.append("WITH TIMESTAMP() AS currentTime ");
		qb.append("MATCH (node:"+NODE_LABEL_BPO_NODE+")<-[taskAt:"+ElementFunctions.RELATIONSHIP_LABEL_TASK_AT+"]-(element:"+ElementFunctions.NODE_LABEL_ELEMENT+") ");
		qb.append("MATCH (report:"+NODE_LABEL_BPO_REPORT+")-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]->node ");
		qb.append("WHERE element."+ElementFunctions.ELEMENT_ATTR_STATUS+"={"+ElementFunctions.CONSTANT_STATUS_WAITING+"} ");
		qb.append("SET taskAt."+ElementFunctions.RELATIONSHIP_ATTR_WAITING_DURATION+" = currentTime-taskAt."+ElementFunctions.RELATIONSHIP_ATTR_START_WAITING_TIME+" ");
		qb.append("SET taskAt."+ElementFunctions.RELATIONSHIP_ATTR_PROCESS_DURATION+" = node."+NODE_ATTR_ALLOWED_PROCESS_DURATION + " ");
//		qb.append("SET report."+REPORT_ATTR_TOTAL_PROCESS_ELEMENT+"=report."+REPORT_ATTR_TOTAL_PROCESS_ELEMENT+"+1 ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_WAITING_DURATION+"=report."+REPORT_ATTR_TOTAL_WAITING_DURATION+"+taskAt."+ElementFunctions.RELATIONSHIP_ATTR_WAITING_DURATION+" ");
		qb.append("SET report."+REPORT_ATTR_AVERAGE_WAITING_DURATION+"=report."+REPORT_ATTR_TOTAL_WAITING_DURATION+"/report."+REPORT_ATTR_TOTAL_WAITING_ELEMENT+" ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_PROCESS_DURATION+"=report."+REPORT_ATTR_TOTAL_PROCESS_DURATION+"+taskAt."+ElementFunctions.RELATIONSHIP_ATTR_PROCESS_DURATION+" ");
		qb.append("SET report."+REPORT_ATTR_AVERAGE_PROCESS_DURATION+"=report."+REPORT_ATTR_TOTAL_PROCESS_DURATION+"/report."+REPORT_ATTR_TOTAL_PROCESS_ELEMENT+" ");
		qb.append("WITH currentTime, node, report, taskAt, element."+ElementFunctions.ELEMENT_ATTR_STATUS+" as status ");
		qb.append("WHERE status={"+ElementFunctions.CONSTANT_STATUS_IN_PROCESS+"} ");
		qb.append("SET taskAt."+ElementFunctions.RELATIONSHIP_ATTR_PROCESS_DURATION+"= currentTime-taskAt."+ElementFunctions.RELATIONSHIP_ATTR_START_PROCESSING_TIME+" ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_PROCESS_DURATION+"=report."+REPORT_ATTR_TOTAL_PROCESS_DURATION+"+taskAt."+ElementFunctions.RELATIONSHIP_ATTR_PROCESS_DURATION+" ");
		qb.append("SET report."+REPORT_ATTR_AVERAGE_PROCESS_DURATION+"=report."+REPORT_ATTR_TOTAL_PROCESS_DURATION+"/report."+REPORT_ATTR_TOTAL_PROCESS_ELEMENT+" ");
		neo4j.sendCypherBooleanResult(qb.toString(), properties);
	}
	
	public boolean incrementTotalWaitedElement(String nodeId, int count) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		properties.put(REPORT_ATTR_TOTAL_WAITING_ELEMENT, count);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NODE_LABEL_BPO_NODE+" {"+NODE_ATTR_NODE_ID+":{"+NODE_ATTR_NODE_ID+"}})<-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_WAITING_ELEMENT+"=report."+REPORT_ATTR_TOTAL_WAITING_ELEMENT+"+{"+REPORT_ATTR_TOTAL_WAITING_ELEMENT+"};");
		return neo4j.sendCypherBooleanResult(qb.toString(),properties);
	}
	
	public boolean incrementTotalWaitingDuration(String nodeId, int count) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		properties.put(REPORT_ATTR_TOTAL_WAITING_DURATION, count);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NODE_LABEL_BPO_NODE+" {"+NODE_ATTR_NODE_ID+":{"+NODE_ATTR_NODE_ID+"}})<-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_WAITING_DURATION+"=report."+REPORT_ATTR_TOTAL_WAITING_DURATION+"+{"+REPORT_ATTR_TOTAL_WAITING_DURATION+"};");
		return neo4j.sendCypherBooleanResult(qb.toString(),properties);
	}
	
	protected boolean incrementTotalProcessElement(String nodeId, int count) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		properties.put(REPORT_ATTR_TOTAL_PROCESS_ELEMENT, count);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NODE_LABEL_BPO_NODE+" {"+NODE_ATTR_NODE_ID+":{"+NODE_ATTR_NODE_ID+"}})<-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_PROCESS_ELEMENT+"=report."+REPORT_ATTR_TOTAL_PROCESS_ELEMENT+"+{"+REPORT_ATTR_TOTAL_PROCESS_ELEMENT+"};");
		return neo4j.sendCypherBooleanResult(qb.toString(),properties);
	}
	
	protected boolean incrementTotalProcessDuration(String nodeId, int count) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		properties.put(REPORT_ATTR_TOTAL_PROCESS_DURATION, count);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NODE_LABEL_BPO_NODE+" {"+NODE_ATTR_NODE_ID+":{"+NODE_ATTR_NODE_ID+"}})<-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		qb.append("SET report."+REPORT_ATTR_TOTAL_PROCESS_DURATION+"=report."+REPORT_ATTR_TOTAL_PROCESS_DURATION+"+{"+REPORT_ATTR_TOTAL_PROCESS_DURATION+"};");
		return neo4j.sendCypherBooleanResult(qb.toString(),properties);
	}
	
	/**
	 * Update Node Attributes Sample usage:
	 * <pre>
	 * Map<NodeAttributes, Object> prop = new HashMap<>();
	 * prop.put(NodeAttributes.NODE_NAME, "Testing");
	 * bpo.getNodeFunctions().updateNodeAttributes("Node1", prop);
	 * </pre>
	 * @param nodeId
	 * @param editablePropertyMap
	 * @return
	 */
	public String updateNodeAttributes(String nodeId, Map<NodeAttributes, Object> editablePropertyMap) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> properties = new HashMap<>();
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		for(Entry<NodeAttributes, Object> each: editablePropertyMap.entrySet()) {
			String propertyName = each.getKey().toString();
			Object propertyValue = each.getValue();
			properties.put(propertyName, propertyValue);
		}
		sb.append("MATCH (node:"+NODE_LABEL_BPO_NODE+") ");
		sb.append("WHERE node."+NODE_ATTR_NODE_ID+"={"+NODE_ATTR_NODE_ID+"} ");
		for(String eachProperty: properties.keySet()) {
			sb.append("SET node."+eachProperty+"={"+eachProperty+"} ");
		}
		sb.append("RETURN COUNT(node) AS count;");
		List<Map<String, Object>> data = neo4j.sendCypherQuery(sb.toString(), properties);
		if(data.size()>0) {
			int result = (int) data.get(0).get("count");
			if(result>0) {
				return "Node Update Success!";
			}
		}
		return NOTIFICATION_NODE_ID_DOES_NOT_EXIST;
	}
	
	/********************
	 * DELETE FUNCTIONS *
	 ********************/
	
	/**
	 * Delete Node
	 * @param nodeId
	 * @return Status
	 */
	public String deleteNode(String nodeId) {
		if(!isNodeExist(nodeId)) {
			return NOTIFICATION_NODE_ID_DOES_NOT_EXIST;
		}
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NODE_LABEL_BPO_NODE+" {"+NODE_ATTR_NODE_ID+":{"+NODE_ATTR_NODE_ID+"}})-[r]-() ");
		qb.append("MATCH node<-[r1:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		qb.append("DELETE r1, r, node, report; ");
		boolean dbQueryStat1 = neo4j.sendCypherBooleanResult(qb.toString(), properties);
		
		String query = "MATCH (node:"+NODE_LABEL_BPO_NODE+" {"+NODE_ATTR_NODE_ID+":{"+NODE_ATTR_NODE_ID+"}}) DELETE node;";
		boolean dbQueryStat2 = neo4j.sendCypherBooleanResult(query, properties);
		if(dbQueryStat2 || dbQueryStat1) {
			return NOTIFICATION_NODE_DELETED_SUCCESSFULLY;
		} else {
			return NOTIFICATION_FAILED_CHECK_CONNECTION;
		}
	}

	/********************
	 * REPORT FUNCTIONS 
	 * @return *
	 ********************/
	
	protected List<Map<String, Object>> getNodeProcessMetrics(String nodeId) {
		Map<String, Object> properties = new HashMap<>();
		boolean isAll = false;
		if(nodeId.equals("*")) {
			nodeId = ".*";
			isAll = true;
		} 
		
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NODE_LABEL_BPO_NODE+")<-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		
		if(isAll) {
			qb.append("WHERE node."+NODE_ATTR_NODE_ID+"=~{"+NODE_ATTR_NODE_ID+"} ");
		} else {
			qb.append("WHERE node."+NODE_ATTR_NODE_ID+"={"+NODE_ATTR_NODE_ID+"} ");
		}
		
		qb.append("WITH node."+NODE_ATTR_NODE_ID+" AS " + NODE_ATTR_NODE_ID +",");
		qb.append("node."+NODE_ATTR_NODE_NAME+" AS "+NODE_ATTR_NODE_NAME+",");
		qb.append("node."+NODE_ATTR_ALLOWED_PROCESS_DURATION+"+node."+NODE_ATTR_ALLOWED_WAITING_DURATION+" AS targetProcessDuration,");
		qb.append("report."+REPORT_ATTR_AVERAGE_PROCESS_DURATION+"+report."+REPORT_ATTR_AVERAGE_WAITING_DURATION+" AS estimatedProcessDuration ");
		qb.append("RETURN "+NODE_ATTR_NODE_ID+","+NODE_ATTR_NODE_NAME+",CASE estimatedProcessDuration<=targetProcessDuration WHEN TRUE THEN TRUE WHEN FALSE THEN FALSE WHEN NULL THEN FALSE END AS Status");
		return neo4j.sendCypherQuery(qb.toString(), properties);
	}
	
	protected List<Map<String, Object>> getNodeProcessDurationMetrics(String nodeId) {
		Map<String, Object> properties = new HashMap<>();
		boolean isAll = false;
		if(nodeId.equals("*")) {
			nodeId = ".*";
			isAll = true;
		} 
		
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NODE_LABEL_BPO_NODE+")<-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		
		if(isAll) {
			qb.append("WHERE node."+NODE_ATTR_NODE_ID+"=~{"+NODE_ATTR_NODE_ID+"} ");
		} else {
			qb.append("WHERE node."+NODE_ATTR_NODE_ID+"={"+NODE_ATTR_NODE_ID+"} ");
		}
		
		qb.append("RETURN node."+NODE_ATTR_NODE_ID+" AS " + NODE_ATTR_NODE_ID +",");
		qb.append("node."+NODE_ATTR_NODE_NAME+" AS "+NODE_ATTR_NODE_NAME+",");
		qb.append("node."+NODE_ATTR_ALLOWED_PROCESS_DURATION+" AS "+NODE_ATTR_ALLOWED_PROCESS_DURATION+",");
		qb.append("report."+REPORT_ATTR_AVERAGE_PROCESS_DURATION+" AS "+REPORT_ATTR_AVERAGE_PROCESS_DURATION+",");
		qb.append("CASE report."+REPORT_ATTR_AVERAGE_PROCESS_DURATION+"<=node."+NODE_ATTR_ALLOWED_PROCESS_DURATION+" WHEN TRUE THEN TRUE WHEN FALSE THEN FALSE END AS Status");
		return neo4j.sendCypherQuery(qb.toString(), properties);
	}
	
	protected List<Map<String, Object>> getNodeWaitingDurationMetrics(String nodeId) {
		Map<String, Object> properties = new HashMap<>();
		boolean isAll = false;
		if(nodeId.equals("*")) {
			nodeId = ".*";
			isAll = true;
		} 
		
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NODE_LABEL_BPO_NODE+")<-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		
		if(isAll) {
			qb.append("WHERE node."+NODE_ATTR_NODE_ID+"=~{"+NODE_ATTR_NODE_ID+"} ");
		} else {
			qb.append("WHERE node."+NODE_ATTR_NODE_ID+"={"+NODE_ATTR_NODE_ID+"} ");
		}
		
		qb.append("RETURN node."+NODE_ATTR_NODE_ID+" AS " + NODE_ATTR_NODE_ID +",");
		qb.append("node."+NODE_ATTR_NODE_NAME+" AS "+NODE_ATTR_NODE_NAME+",");
		qb.append("node."+NODE_ATTR_ALLOWED_WAITING_DURATION+" AS "+NODE_ATTR_ALLOWED_WAITING_DURATION+",");
		qb.append("report."+REPORT_ATTR_AVERAGE_WAITING_DURATION+" AS "+REPORT_ATTR_AVERAGE_WAITING_DURATION+",");
		qb.append("CASE report."+REPORT_ATTR_AVERAGE_WAITING_DURATION+"<=node."+NODE_ATTR_ALLOWED_WAITING_DURATION+" WHEN TRUE THEN TRUE WHEN FALSE THEN FALSE END AS Status");
		return neo4j.sendCypherQuery(qb.toString(), properties);
	}

	protected List<Map<String, Object>> getNodeCurrentWaitingAndInProcessElementCount(String nodeId) {
		Map<String, Object> properties = new HashMap<>();
		boolean isAll = false;
		if(nodeId.equals("*")) {
			nodeId = ".*";
			isAll = true;
		} 
		
		properties.put(NODE_ATTR_NODE_ID, nodeId);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NODE_LABEL_BPO_NODE+")<-[:"+RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NODE_LABEL_BPO_REPORT+") ");
		
		if(isAll) {
			qb.append("WHERE node."+NODE_ATTR_NODE_ID+"=~{"+NODE_ATTR_NODE_ID+"} ");
		} else {
			qb.append("WHERE node."+NODE_ATTR_NODE_ID+"={"+NODE_ATTR_NODE_ID+"} ");
		}
		
		qb.append("RETURN node."+NODE_ATTR_NODE_ID+" AS " + NODE_ATTR_NODE_ID +",");
		qb.append("node."+NODE_ATTR_NODE_NAME+" AS "+NODE_ATTR_NODE_NAME+",");
		qb.append("report."+REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+" AS "+REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+",");
		qb.append("report."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+" AS "+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+";");
		return neo4j.sendCypherQuery(qb.toString(), properties);
	}
}
