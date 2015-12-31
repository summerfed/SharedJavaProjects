package com.svi.bpo.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.svi.bpo.graph.notifications.BPONotifications;
import com.svi.bpo.graph.obj.ExceptionNodeObject;
import com.svi.bpo.graph.utils.DataUtilities;
import com.svi.tools.neo4j.rest.service.Neo4jRestService;

public class ExceptionNodeFunctions {
	protected static final String VAR_EXCEPTION_CODE_COLLECTION = "exceptionCodeCollection";
	protected static final String RELATIONSHIP_LABEL_EXCEPTION_TO = "EXCEPTION_TO";
	protected static final String NODE_PROCESS_STATUS = "nodeProcessStatus";
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
	protected static final String NODE_LABEL_BPO_EXCEPTION_NODE = "BPO_EXCEPTION_NODE";
	protected static final String NODE_LABEL_CLUSTER = "BPO_CLUSTER";
	protected static final String NODE_LABEL_BPO_REPORT = "BPO_NODE_REPORT";
	
	/*******************
	 * NODE ATTRIBUTES *
	 *******************/
	protected static final String NODE_ATTR_ERROR_NODE = "errorNode";// need
	protected static final String NODE_ATTR_ALLOWED_ERROR = "allowedError";// need
	protected static final String NODE_ATTR_TARGET_OUTPUT = "targetOutput";// need
	protected static final String EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION = "allowedWaitingDuration";
	protected static final String EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION = "allowedProcessDuration";
	protected static final String NODE_ATTR_STD_UNIT_MEASURE = "stdUnitOfMeasure";// need
	protected  static final String NODE_ATTR_CLUSTER = "cluster";// need
	protected  static final String EXCEPTION_NODE_ATTR_NAME = "exceptionName";//need
	protected  static final String EXCEPTION_NODE_ATTR_ID = "exceptionCode";// need
	protected  static final String NODE_ATTR_COST = "cost";
	
	protected  static final String CLUSTER_ATTR_ID = "clusterId";
	protected  static final String CLUSTER_ATTR_TARGET_COMPLETION_TIME = "targetCompletionTime";
	protected  static final String CLUSTER_ATTR_ESTIMATED_COMPLETION_TIME = "targetEstimatedTime";
	
	/*******************
	 * NOTIFICATIONS ***
	 *******************/
	protected static final String NOTIFICATION_NODE_DELETED_SUCCESSFULLY = "Node Deleted Successfully";
	protected static final String NOTIFICATION_NODE_ID_DOES_NOT_EXIST = "Node Id Does Not Exist";
	
	protected static final String CONSTANT_PIPE = "|";
	
	private Neo4jRestService neo4j;
	
	private BPO bpo;
	
	public ExceptionNodeFunctions(Neo4jRestService neo4j, BPO bpo) {
		this.neo4j = neo4j;
		this.bpo = bpo;
	}
	
	/********************
	 * CREATE FUNCTIONS *
	 ********************/
	
	/**
	 * Insert Exception Node
	 * @param nodeId
	 * @param exceptionCode
	 * @param exceptionName
	 * @param allowedWaitingTime
	 * @param allowedProcessTime
	 * @return 
	 * <ul>
	 * 	<li>BPONotifications.NODE_DOES_NOT_EXIST = Node Does Not Exist</li>
	 * 	<li>BPONotifications.EXCEPTION_NODE_INSERT_SUCCESS = Exception Node Inserted Successfully</li>
	 * 	<li>BPONotifications.FAILED_CHECK_CONNECTION = Graph Database Connection Error</li>
	 * </ul>
	 */
	public BPONotifications insertExceptionNode(String nodeId, String exceptionCode, String exceptionName, long allowedWaitingTime, long allowedProcessTime) {
		// Convert Seconds to Milliseconds
		allowedWaitingTime = allowedWaitingTime * 1000;
		allowedProcessTime = allowedProcessTime * 1000;
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(EXCEPTION_NODE_ATTR_ID, exceptionCode);
		properties.put(EXCEPTION_NODE_ATTR_NAME, exceptionName);
		properties.put(EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION, allowedWaitingTime);
		properties.put(EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION, allowedProcessTime);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}}) ");
		qb.append("MERGE (exception:" +NODE_LABEL_BPO_EXCEPTION_NODE+ " {"+EXCEPTION_NODE_ATTR_ID+":{"+EXCEPTION_NODE_ATTR_ID+"}}) ");
		qb.append("ON CREATE SET exception."+EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION+"={"+EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION+"}, exception."+EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION+"={"+EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION+"}, ");
		qb.append("ON MATCH SET exception."+EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION+"={"+EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION+"}, exception."+EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION+"={"+EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION+"}, ");
		qb.append("exception."+EXCEPTION_NODE_ATTR_NAME+"={"+EXCEPTION_NODE_ATTR_NAME+"}, ");
		qb.append("exception."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+" = 0, ");
		qb.append("exception."+REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+" = 0 ");
		qb.append("MERGE node-[r:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->exception ");
		qb.append("RETURN COUNT(r) AS data;");
		
		List<Map<String, Object>> dbResult = neo4j.sendCypherQuery(qb.toString(), properties);
		if(dbResult.size()>0) {
			int tmp = DataUtilities.toInteger(dbResult.get(0).get("data"));
			if(tmp == 1) {
				return BPONotifications.EXCEPTION_NODE_INSERT_SUCCESS;
			} else if(tmp == 0) {
				if(!bpo.getNodeFunctions().isNodeExist(nodeId)) {
					return BPONotifications.NODE_DOES_NOT_EXIST;
				}
			}
		}
		
		return BPONotifications.FAILED_CHECK_CONNECTION;
	}
	
	/**
	 * 
	 * @param nodeId
	 * @param currentExceptionCode
	 * @param followingExceptionCodes
	 * @return
	 * <ul>
	 * 	<li>BPONotifications.EXCEPTION_FLOW_CREATE_SUCCESS = Flow Creation Success</li>
	 * 	<li>BPONotifications.EXCEPTION_LIST_DOES_NOT_EXIST = Any Exception Node Does Not Exist</li>
	 * </ul>
	 */
	public BPONotifications buildExceptionFlow(String nodeId, String currentExceptionCode, String... followingExceptionCodes) {
		List<String> tmp = new LinkedList<>(Arrays.asList(followingExceptionCodes));
		tmp.add(currentExceptionCode);
		if(checkIfExceptionCodesAllExist(nodeId, tmp)) {
			Map<String, Object> properties = new HashMap<>();
			properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
			properties.put(EXCEPTION_NODE_ATTR_ID, currentExceptionCode);
			StringBuilder qb = new StringBuilder();
			qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+")-[:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->(exceptionNode:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
			qb.append("WHERE node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} AND exceptionNode."+EXCEPTION_NODE_ATTR_ID+"={"+EXCEPTION_NODE_ATTR_ID+"} ");
			qb.append("WITH node, exceptionNode ");
			int flowExceptionSize = followingExceptionCodes.length;
			
			StringBuilder mergeQuery = new StringBuilder();
			mergeQuery.append("MERGE exceptionNode-[:"+"NEXT_FLOW"+"]->exception0 ");
			for(int i=0; i<flowExceptionSize; i++) {
				String tmpVar = "exception"+i;
				properties.put(tmpVar, followingExceptionCodes[i]);
				qb.append("MATCH node-[:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->("+tmpVar+":"+NODE_LABEL_BPO_EXCEPTION_NODE+" {"+EXCEPTION_NODE_ATTR_ID+":{"+tmpVar+"}}) ");
				if(i>0) {
					String prevException = "exception"+(i-1);
					mergeQuery.append("MERGE "+prevException+"-[:"+"NEXT_FLOW"+"]->"+tmpVar+" ");
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
	
	
	/******************
	 * READ FUNCTIONS *
	 ******************/
	
	/**
	 * View Exception Nodes
	 * @param nodeId, Put * to get all exceptions in all nodes
	 * @return
	 */
	public ExceptionNodeObject[] viewExceptionNodes(String nodeId) {
		Map<String, Object> properties = new HashMap<>();//node-[:"+RELATIONSHIP_LABEL_BPO_NODE_IN+"]->cluster
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+")-[:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->(exception:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
		if(!nodeId.contains("*")) {
			qb.append("WHERE node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} ");
		}
		
		qb.append("RETURN exception."+EXCEPTION_NODE_ATTR_ID+" AS "+EXCEPTION_NODE_ATTR_ID+", ");
		qb.append("exception."+EXCEPTION_NODE_ATTR_NAME+" AS "+ EXCEPTION_NODE_ATTR_NAME+", ");
		qb.append("exception."+EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION+" AS "+ EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION+", ");
		qb.append("exception."+EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION+" AS "+ EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION+", ");
		qb.append("exception."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+" AS "+ REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+", ");
		qb.append("exception."+REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+" AS "+ REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+";");
		
		List<Map<String, Object>> dbResult = neo4j.sendCypherQuery(qb.toString(), properties);
		ExceptionNodeObject[] dataToReturn = new ExceptionNodeObject[dbResult.size()];
		for(int i=0; i<dbResult.size(); i++) {
			Map<String, Object> eachNode = dbResult.get(i);
			String exceptionCode = DataUtilities.toStringValue(eachNode.get(EXCEPTION_NODE_ATTR_ID));
			String exceptionName = DataUtilities.toStringValue(eachNode.get(EXCEPTION_NODE_ATTR_NAME));
			long allowedWaitingDuration = DataUtilities.toLongValue(eachNode.get(EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION));
			long allowedProcessDuration = DataUtilities.toLongValue(eachNode.get(EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION));
			long currentTotalWaitingElements = DataUtilities.toLongValue(eachNode.get(REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS));
			long currentTotalInProcessElements = DataUtilities.toLongValue(eachNode.get(REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS));
			ExceptionNodeObject exceptionNode = new ExceptionNodeObject();
			exceptionNode.setExceptionCode(exceptionCode);
			exceptionNode.setExceptionName(exceptionName);
			exceptionNode.setAllowedWaitingDuration(allowedWaitingDuration);
			exceptionNode.setAllowedProcessDuration(allowedProcessDuration);
			exceptionNode.setCurrentTotalWaitingElements(currentTotalWaitingElements);
			exceptionNode.setCurrentTotalInProcessElements(currentTotalInProcessElements);
			
			dataToReturn[i] = exceptionNode;
		}
		
		return dataToReturn;
	}
	/**
	 * Check if Exception Node Exist by Matching @param exceptionCode with Existing Nodes 
	 * @param exceptionId
	 * @return TRUE or FALSE
	 */
	public boolean isExceptionNodeExist(String nodeId, String exceptionId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(EXCEPTION_NODE_ATTR_ID, exceptionId);
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+")-[:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->(exception:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
		qb.append("WHERE node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} AND exception."+EXCEPTION_NODE_ATTR_ID+"={"+EXCEPTION_NODE_ATTR_ID+"} ");
		qb.append("RETURN COUNT(*) AS data;");
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
	
	private boolean checkIfExceptionCodesAllExist(String nodeId, List<String> exceptionCodesToCheck) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(VAR_EXCEPTION_CODE_COLLECTION, exceptionCodesToCheck);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+")-[:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->(exception:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
		qb.append("WHERE node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} AND exception."+EXCEPTION_NODE_ATTR_ID+" IN {"+VAR_EXCEPTION_CODE_COLLECTION+"} ");
		qb.append("WITH COLLECT (exception."+EXCEPTION_NODE_ATTR_ID+") AS exceptionCodes ");
		qb.append("RETURN ALL(x IN {"+VAR_EXCEPTION_CODE_COLLECTION+"} WHERE x IN exceptionCodes) AS result;");
		
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

	/********************
	 * UPDATE FUNCTIONS *
	 ********************/
	private void moveElementToException(String elementId, String nodeId, String exceptionCode) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ElementFunctions.ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(EXCEPTION_NODE_ATTR_ID, exceptionCode);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (element:"+ElementFunctions.NODE_LABEL_ELEMENT+")");
		qb.append("-[r]->");
		qb.append("(node:"+NodeFunctions.NODE_LABEL_BPO_NODE+")");
		qb.append("-[:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->");
		qb.append("(exception:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
		qb.append("WHERE element."+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+"={"+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+"} ");
		qb.append("AND node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} ");
		qb.append("AND exception."+EXCEPTION_NODE_ATTR_ID+"={"+EXCEPTION_NODE_ATTR_ID+"} ");
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
	/*//TODO SOON
	 * public String updateNodeAttributes(String nodeId, Map<NodeAttributes, Object> editablePropertyMap) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> properties = new HashMap<>();
		properties.put(EXCEPTION_NODE_ATTR_ID, nodeId);
		for(Entry<NodeAttributes, Object> each: editablePropertyMap.entrySet()) {
			String propertyName = each.getKey().toString();
			Object propertyValue = each.getValue();
			properties.put(propertyName, propertyValue);
		}
		sb.append("MATCH (node:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
		sb.append("WHERE node."+EXCEPTION_NODE_ATTR_ID+"={"+EXCEPTION_NODE_ATTR_ID+"} ");
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
	}*/
	
	/********************
	 * DELETE FUNCTIONS *
	 ********************/
	
	/**
	 * Delete Node
	 * @param nodeId
	 * @return Status
	 */
	//TODO SOON
	/*public String deleteNode(String nodeId) {
		if(!isExceptionNodeExist("",nodeId)) {
			return NOTIFICATION_NODE_ID_DOES_NOT_EXIST;
		}
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(EXCEPTION_NODE_ATTR_ID, nodeId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NODE_LABEL_BPO_EXCEPTION_NODE+" {"+EXCEPTION_NODE_ATTR_ID+":{"+EXCEPTION_NODE_ATTR_ID+"}})<-[r]-() DELETE r,node");
		boolean dbQueryStat1 = neo4j.sendCypherBooleanResult(qb.toString(), properties);
		
		String query = "MATCH (node:"+NODE_LABEL_BPO_EXCEPTION_NODE+" {"+EXCEPTION_NODE_ATTR_ID+":{"+EXCEPTION_NODE_ATTR_ID+"}}) DELETE node;";
		boolean dbQueryStat2 = neo4j.sendCypherBooleanResult(query, properties);
		if(dbQueryStat2 || dbQueryStat1) {
			return NOTIFICATION_NODE_DELETED_SUCCESSFULLY;
		} else {
			return NOTIFICATION_FAILED_CHECK_CONNECTION;
		}
	}*/

	/********************
	 * REPORT FUNCTIONS 
	 ********************/
}
