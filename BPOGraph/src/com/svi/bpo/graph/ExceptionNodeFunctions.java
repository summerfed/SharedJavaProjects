package com.svi.bpo.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.svi.bpo.graph.notifications.BPONotifications;
import com.svi.bpo.graph.obj.ElementObject;
import com.svi.bpo.graph.obj.ExceptionNodeObject;
import com.svi.bpo.graph.utils.DataUtilities;
import com.svi.tools.neo4j.rest.service.Neo4jRestService;

public class ExceptionNodeFunctions {
	private static final String EXCEPTION_ATTR_UUID = "exceptionUUID";
	protected static final String CONSTANT_NEXT_EXCEPTION_CODE = "nextExceptionCode";
	protected static final String RELATIONSHIP_LABEL_TAGGED_AS_EXCEPTION = "TAGGED_AS_EXCEPTION";
	protected static final String VAR_EXCEPTION_CODE_COLLECTION = "exceptionCodeCollection";
	protected static final String RELATIONSHIP_LABEL_EXCEPTION_TO = "EXCEPTION_TO";
	
	protected static final String REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS = "currentTotalInProcessElements";
	protected static final String REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS = "currentTotalWaitingElements";
//	protected static final String REPORT_ATTR_TOTAL_PROCESS_DURATION = "totalProcessDuration";
//	protected static final String REPORT_ATTR_TOTAL_PROCESS_ELEMENT = "totalProcessElement";
//	protected static final String REPORT_ATTR_AVERAGE_PROCESS_DURATION = "averageProcessDuration";
//	protected static final String REPORT_ATTR_TOTAL_WAITING_DURATION = "totalWaitingDuration";
	protected static final String REPORT_ATTR_TOTAL_WAITING_ELEMENT = "totalWaitingElement";
	protected static final String REPORT_ATTR_AVERAGE_WAITING_DURATION = "averageWaitingDuration";
//	protected static final String REPORT_ATTR_TOTAL_ERROR_COUNT = "totalErrorCount";
//	protected static final String REPORT_ATTR_TOTAL_OUTPUT_COUNT = "totalOutputCount";

	protected static final String NODE_LABEL_BPO_EXCEPTION_NODE = "BPO_EXCEPTION_NODE";
	
	/*******************
	 * NODE ATTRIBUTES *
	 *******************/
	protected static final String EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION = "allowedWaitingDuration";
	protected static final String EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION = "allowedProcessDuration";
	protected static final String EXCEPTION_NODE_ATTR_NAME = "exceptionName";//need
	protected static final String EXCEPTION_NODE_ATTR_ID = "exceptionCode";// need
	
	protected static final String CONSTANT_STATUS_EXCEPTION_WAITING = "EW";
	protected static final String CONSTANT_STATUS_EXCEPTION_IN_PROCESS = "EP";
	protected static final String CONSTANT_STATUS_EXCEPTION_COMPLETE = "EC";
	
	
	/*******************
	 * NOTIFICATIONS ***
	 *******************/
	
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
		qb.append("ON CREATE SET exception."+EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION+"={"+EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION+"}, ");
		qb.append("exception."+EXCEPTION_NODE_ATTR_NAME+"={"+EXCEPTION_NODE_ATTR_NAME+"}, ");//added by jm
		qb.append("exception."+EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION+"={"+EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION+"}, ");
		qb.append("exception."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"=0, ");
		qb.append("exception."+REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=0 ");
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
	public BPONotifications buildFlow(String nodeId, String currentExceptionCode, String... followingExceptionCodes) {
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
	
	public ElementObject[] viewExceptionElements(String nodeId, String exceptionCode) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(EXCEPTION_NODE_ATTR_ID, exceptionCode);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (exception:BPO_EXCEPTION_NODE) ");
		qb.append("MATCH path1=(:BPO_EXCEPTION_NODE)-[:NEXT_FLOW*0..]->exception ");
		qb.append("MATCH path2=exception-[:NEXT_FLOW*0..]->(:BPO_EXCEPTION_NODE) ");
		qb.append("WHERE exception."+EXCEPTION_NODE_ATTR_ID+"={"+EXCEPTION_NODE_ATTR_ID+"} ");
		qb.append("WITH exception, MAX(LENGTH(path1))+1 AS currentIdx, MAX(LENGTH(path2)) AS totalIdx ");
		qb.append("MATCH (cluster:"+NodeFunctions.NODE_LABEL_CLUSTER+")<-[:"+NodeFunctions.RELATIONSHIP_LABEL_BPO_NODE_IN+"]-(node:"+NodeFunctions.NODE_LABEL_BPO_NODE+")-[:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->(exception:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
		qb.append("MATCH (element:"+ElementFunctions.NODE_LABEL_ELEMENT+")-[r]->exception ");
		qb.append("RETURN r."+ElementFunctions.ELEMENT_ATTR_WORKER_ID+" AS "+ElementFunctions.ELEMENT_ATTR_WORKER_ID+", node."+NodeFunctions.NODE_ATTR_NODE_ID+" AS "+NodeFunctions.NODE_ATTR_NODE_ID+", element, currentIdx + ' Out of ' + (totalIdx+currentIdx) AS currentProcessLocation,");
		qb.append("CASE element."+ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+" <= cluster."+NodeFunctions.CLUSTER_ATTR_TARGET_COMPLETION_TIME +" WHEN TRUE THEN TRUE WHEN FALSE THEN FALSE END AS "+ ElementFunctions.ELEMENT_VAR_CAN_MEET_DEADLINE +";");
		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(qb.toString(), properties);
		ElementObject[] dataToReturn = new ElementObject[dataReturned.size()];
		String nodeKey = "element.";
		for(int i=0; i<dataReturned.size(); i++) {
			Map<String, Object> data = dataReturned.get(i);
			
			String ndeId =  DataUtilities.toStringValue(data.get(NodeFunctions.NODE_ATTR_NODE_ID));
			String elemId =  DataUtilities.toStringValue(data.get(nodeKey+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID));
			String workerId = DataUtilities.toStringValue(data.get(ElementFunctions.ELEMENT_ATTR_WORKER_ID));
			int priority = DataUtilities.toInteger(data.get(nodeKey+ElementFunctions.ELEMENT_ATTR_PRIORITY));
			String status =  DataUtilities.toStringValue(data.get(nodeKey+ElementFunctions.ELEMENT_ATTR_STATUS));
			String extra1 =  DataUtilities.toStringValue(data.get(nodeKey+ElementFunctions.ELEMENT_ATTR_EXTRA1));
			String extra2 =  DataUtilities.toStringValue(data.get(nodeKey+ElementFunctions.ELEMENT_ATTR_EXTRA2));
			String extra3 =  DataUtilities.toStringValue(data.get(nodeKey+ElementFunctions.ELEMENT_ATTR_EXTRA3));
			String filePointer =  DataUtilities.toStringValue(data.get(nodeKey+ElementFunctions.ELEMENT_ATTR_FILE_POINTER));
			long targetCompletionDuration = DataUtilities.toLongValue(data.get(nodeKey+ElementFunctions.ELEMENT_ATTR_TARGET_COMPLETION_DURATION));
			long estimatedCompletionDuration = DataUtilities.toLongValue(data.get(nodeKey+ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION));
			boolean canMeetDeadline = DataUtilities.toBoolean(data.get(ElementFunctions.ELEMENT_VAR_CAN_MEET_DEADLINE));
			String normalFlowLocation = DataUtilities.toStringValue(data.get("currentProcessLocation"));
			ElementObject element =  new ElementObject();
			element.setElementId(elemId);
			element.setNodeId(ndeId);
			element.setPriority(priority);
			element.setStatus(status);
			element.setExtra1(extra1);
			element.setExtra2(extra2);
			element.setExtra3(extra3);
			element.setFilePointer(filePointer);
			element.setTargetCompletionDuration(targetCompletionDuration);
			element.setEstimatedCompletionDuration(estimatedCompletionDuration);
			element.setWorkerId(workerId);
			
			element.setNormalFlowLocation(normalFlowLocation);
			dataToReturn[i] = element;
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
	 * UPDATE FUNCTIONS 
	 ********************/
	public BPONotifications moveElementToException(String elementId, String nodeId, String exceptionCode) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ElementFunctions.ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(EXCEPTION_NODE_ATTR_ID, exceptionCode);
		properties.put(ElementFunctions.ELEMENT_ATTR_STATUS, CONSTANT_STATUS_EXCEPTION_WAITING);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (element:"+ElementFunctions.NODE_LABEL_ELEMENT+")");
		qb.append("-[r:"+ElementFunctions.RELATIONSHIP_LABEL_TASK_IN_PROCESS+"]->");
		qb.append("(node:"+NodeFunctions.NODE_LABEL_BPO_NODE+")");
		qb.append("-[:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->");
		qb.append("(exception:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
		qb.append("MATCH node<-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]-(nodeReport:"+NodeFunctions.NODE_LABEL_BPO_REPORT+") ");
		qb.append("WHERE element."+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+"={"+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+"} ");
		qb.append("AND node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} ");
		qb.append("AND exception."+EXCEPTION_NODE_ATTR_ID+"={"+EXCEPTION_NODE_ATTR_ID+"} ");
		qb.append("MERGE element-[exceptionTag:"+RELATIONSHIP_LABEL_TAGGED_AS_EXCEPTION+"]->node ");
		qb.append("SET element."+ElementFunctions.ELEMENT_ATTR_STATUS+" = {"+ElementFunctions.ELEMENT_ATTR_STATUS+"} ");
		qb.append("SET element."+ElementFunctions.ELEMENT_ATTR_TARGET_COMPLETION_DURATION+"=element."+ElementFunctions.ELEMENT_ATTR_TARGET_COMPLETION_DURATION+" + ");
		qb.append("(exception."+EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION+"+exception."+EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION+") ");
		qb.append("SET element."+ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"=element."+ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+" + ");
		qb.append("(exception."+EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION+"+exception."+EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION+") ");
		qb.append("SET r."+ElementFunctions.RELATIONSHIP_ATTR_END_PROCESSING_TIME+"=TIMESTAMP() ");
		qb.append("SET r."+ElementFunctions.RELATIONSHIP_ATTR_PROCESS_DURATION+"=r."+ElementFunctions.RELATIONSHIP_ATTR_END_PROCESSING_TIME+"-r."+ElementFunctions.RELATIONSHIP_ATTR_START_PROCESSING_TIME+" ");
		qb.append("SET exceptionTag = r ");
		qb.append("MERGE element-[exceptionTaskAt:"+ElementFunctions.RELATIONSHIP_LABEL_TASK_AT+"]->exception ");
		qb.append("SET exceptionTaskAt."+ElementFunctions.RELATIONSHIP_ATTR_START_WAITING_TIME+"=TIMESTAMP() ");
		qb.append("SET exceptionTaskAt."+ElementFunctions.RELATIONSHIP_ATTR_START_PROCESSING_TIME+"='' ");
		qb.append("SET exceptionTaskAt."+ElementFunctions.RELATIONSHIP_ATTR_END_PROCESSING_TIME+"='' ");
		qb.append("SET nodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_DURATION+"=nodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_DURATION+"+r."+ElementFunctions.RELATIONSHIP_ATTR_PROCESS_DURATION+" ");
		qb.append("SET nodeReport."+NodeFunctions.REPORT_ATTR_AVERAGE_PROCESS_DURATION+"=(nodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_DURATION+"/nodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_ELEMENT+") ");
		qb.append("SET nodeReport."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"=(nodeReport."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"-1) ");
		qb.append("SET exception."+REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=(exception."+REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"+1) ");
		qb.append("SET exceptionTag."+EXCEPTION_ATTR_UUID+"=r."+ElementFunctions.RELATIONSHIP_ATTR_END_PROCESSING_TIME+" ");
		qb.append("SET exceptionTaskAt."+EXCEPTION_ATTR_UUID+"=r."+ElementFunctions.RELATIONSHIP_ATTR_END_PROCESSING_TIME+" ");
		qb.append("DELETE r ");
		qb.append("RETURN COUNT(element) AS count; ");
		
		List<Map<String, Object>> dbResult = neo4j.sendCypherQuery(qb.toString(), properties);
		if(dbResult.size()>0) {
			int status = DataUtilities.toInteger(dbResult.get(0).get("count"));
			if(status>0) {
				return BPONotifications.EXCEPTION_TAG_SUCCESSFUL;
			} else {
				return BPONotifications.EXCEPTION_TAG_ELEMENT_NOT_PROCESS;
			}
		}
		return BPONotifications.FAILED_CHECK_CONNECTION;
	}
	
	public ElementObject getExceptionElement(String nodeId, String exceptionCode, String elementId, String workerId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ElementFunctions.ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(EXCEPTION_NODE_ATTR_ID, exceptionCode);
		properties.put(ElementFunctions.ELEMENT_ATTR_WORKER_ID, workerId);
		properties.put(CONSTANT_STATUS_EXCEPTION_IN_PROCESS, CONSTANT_STATUS_EXCEPTION_IN_PROCESS);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+")-[:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->(exception:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
		qb.append("MATCH (element:"+ElementFunctions.NODE_LABEL_ELEMENT+")-[task:"+ElementFunctions.RELATIONSHIP_LABEL_TASK_AT+"]->exception ");
		qb.append("WHERE node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} ");
		qb.append("AND element."+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+"={"+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+"} ");
		qb.append("AND task."+ElementFunctions.ELEMENT_ATTR_WORKER_ID+"={"+ElementFunctions.ELEMENT_ATTR_WORKER_ID+"} ");
		qb.append("AND exception."+EXCEPTION_NODE_ATTR_ID+"={"+EXCEPTION_NODE_ATTR_ID+"} ");
		qb.append("MERGE element-[taskInProcess:"+ElementFunctions.RELATIONSHIP_LABEL_TASK_IN_PROCESS+"]->exception ");
		qb.append("SET element."+ElementFunctions.ELEMENT_ATTR_STATUS+" = {"+CONSTANT_STATUS_EXCEPTION_IN_PROCESS+"} ");
		qb.append("SET task."+ElementFunctions.RELATIONSHIP_ATTR_START_PROCESSING_TIME+"=TIMESTAMP() ");
		qb.append("SET task."+ElementFunctions.RELATIONSHIP_ATTR_WAITING_DURATION+"=task."+ElementFunctions.RELATIONSHIP_ATTR_START_PROCESSING_TIME+"-"+"task."+ElementFunctions.RELATIONSHIP_ATTR_START_WAITING_TIME+" ");
		qb.append("SET taskInProcess = task ");
		qb.append("SET exception."+REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=(exception."+REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"-1) ");
		qb.append("SET exception."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"=(exception."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"+1) ");
		qb.append("SET element."+ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"=(element."+ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+" - ");
		qb.append("exception."+EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION+") + ");
		qb.append("task."+ElementFunctions.RELATIONSHIP_ATTR_WAITING_DURATION+" ");
		qb.append("WITH task, node, element ");
		qb.append("DELETE task ");
		qb.append("RETURN node."+NodeFunctions.NODE_ATTR_NODE_ID+" AS nodeId, ");
		qb.append("element."+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+" AS elementId, ");
		qb.append("element."+ElementFunctions.ELEMENT_ATTR_PRIORITY+" AS "+ElementFunctions.ELEMENT_ATTR_PRIORITY+", ");
		qb.append("element."+ElementFunctions.ELEMENT_ATTR_STATUS+" AS "+ElementFunctions.ELEMENT_ATTR_STATUS+", ");
		qb.append("element."+ElementFunctions.ELEMENT_ATTR_EXTRA1+" AS "+ElementFunctions.ELEMENT_ATTR_EXTRA1+", ");
		qb.append("element."+ElementFunctions.ELEMENT_ATTR_EXTRA2+" AS "+ElementFunctions.ELEMENT_ATTR_EXTRA2+", ");
		qb.append("element."+ElementFunctions.ELEMENT_ATTR_EXTRA3+" AS "+ElementFunctions.ELEMENT_ATTR_EXTRA3+", ");
		qb.append("element."+ElementFunctions.ELEMENT_ATTR_FILE_POINTER+" AS "+ElementFunctions.ELEMENT_ATTR_FILE_POINTER+", ");
		qb.append("element."+ElementFunctions.ELEMENT_ATTR_TARGET_COMPLETION_DURATION+" AS "+ElementFunctions.ELEMENT_ATTR_TARGET_COMPLETION_DURATION+", ");
		qb.append("element."+ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+" AS "+ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+";");
		ElementObject element = null;
		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(qb.toString(), properties);
		for(int i=0; i<dataReturned.size(); i++) {
			Map<String, Object> data = dataReturned.get(i);
			String ndeId =  data.get("nodeId").toString();
			String elemId =  data.get("elementId").toString();
			int priority = (int) data.get(ElementFunctions.ELEMENT_ATTR_PRIORITY);
			String status =  data.get(ElementFunctions.ELEMENT_ATTR_STATUS).toString();
			String extra1 =  data.get(ElementFunctions.ELEMENT_ATTR_EXTRA1).toString();
			String extra2 =  data.get(ElementFunctions.ELEMENT_ATTR_EXTRA2).toString();
			String extra3 =  data.get(ElementFunctions.ELEMENT_ATTR_EXTRA3).toString();
			String filePointer =  data.get(ElementFunctions.ELEMENT_ATTR_FILE_POINTER).toString();
			long targetCompletionDuration = DataUtilities.toLongValue(data.get(ElementFunctions.ELEMENT_ATTR_TARGET_COMPLETION_DURATION));
			long estimatedCompletionDuration = DataUtilities.toLongValue(data.get(ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION));
			
			element = new ElementObject();
			element.setElementId(elemId);
			element.setNodeId(ndeId);
			element.setPriority(priority);
			element.setStatus(status);
			element.setExtra1(extra1);
			element.setExtra2(extra2);
			element.setExtra3(extra3);
			element.setFilePointer(filePointer);
			element.setTargetCompletionDuration(targetCompletionDuration);
			element.setEstimatedCompletionDuration(estimatedCompletionDuration);
		}
		return element;
	}
	
	//TODO RETURN
	public void completeExceptionElement(String nodeId, String currentExceptionCode, String nextExceptionCode, String elementId, String workerId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ElementFunctions.ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(EXCEPTION_NODE_ATTR_ID, currentExceptionCode);
		properties.put(CONSTANT_NEXT_EXCEPTION_CODE, nextExceptionCode);
		properties.put(ElementFunctions.ELEMENT_ATTR_WORKER_ID, workerId);
		properties.put(ElementFunctions.ELEMENT_ATTR_STATUS, ElementFunctions.CONSTANT_STATUS_WAITING);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+") ");
		qb.append("MATCH node-[current:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->(firstExceptionNode:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
		qb.append("MATCH node-[next:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->(nextExceptionNode:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
		qb.append("MATCH (element:"+ElementFunctions.NODE_LABEL_ELEMENT+")-[completedRel:"+ElementFunctions.RELATIONSHIP_LABEL_TASK_IN_PROCESS+"]->firstExceptionNode ");
		qb.append("MATCH element-[taggedException:"+RELATIONSHIP_LABEL_TAGGED_AS_EXCEPTION+"]->node ");
		qb.append("WHERE node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} ");
		qb.append("AND element."+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+"={"+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+"} ");
		qb.append("AND completedRel."+ElementFunctions.ELEMENT_ATTR_WORKER_ID+"={"+ElementFunctions.ELEMENT_ATTR_WORKER_ID+"} ");
		qb.append("AND firstExceptionNode."+EXCEPTION_NODE_ATTR_ID+"={"+EXCEPTION_NODE_ATTR_ID+"} ");
		qb.append("AND nextExceptionNode."+EXCEPTION_NODE_ATTR_ID+"={"+CONSTANT_NEXT_EXCEPTION_CODE+"} ");
		qb.append("CREATE element-[nextNodeRel:"+ElementFunctions.RELATIONSHIP_LABEL_TASK_AT+"]->nextExceptionNode ");
		qb.append("CREATE element-[completedTask:"+ElementFunctions.RELATIONSHIP_LABEL_COMPLETED_TASK+"]->firstExceptionNode ");
		qb.append("SET element."+ElementFunctions.ELEMENT_ATTR_STATUS+" = {"+ElementFunctions.ELEMENT_ATTR_STATUS+"} ");
		qb.append("SET completedRel."+ElementFunctions.RELATIONSHIP_ATTR_END_PROCESSING_TIME+"=TIMESTAMP() ");
		qb.append("SET nextNodeRel."+ElementFunctions.RELATIONSHIP_ATTR_START_WAITING_TIME+"=TIMESTAMP() ");
		qb.append("SET nextNodeRel."+ElementFunctions.RELATIONSHIP_ATTR_START_PROCESSING_TIME+"='' ");
		qb.append("SET nextNodeRel."+ElementFunctions.RELATIONSHIP_ATTR_END_PROCESSING_TIME+"='' ");
		qb.append("SET nextNodeRel."+EXCEPTION_ATTR_UUID+"=taggedException."+EXCEPTION_ATTR_UUID+" ");
		qb.append("SET completedRel."+ElementFunctions.RELATIONSHIP_ATTR_PROCESS_DURATION+"=completedRel."+ElementFunctions.RELATIONSHIP_ATTR_END_PROCESSING_TIME+"-completedRel."+ElementFunctions.RELATIONSHIP_ATTR_START_PROCESSING_TIME+" ");
		qb.append("SET completedTask=completedRel ");
		qb.append("SET element."+ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"=element."+ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+" + ");
		qb.append("(nextExceptionNode."+EXCEPTION_NODE_ATTR_ALLOWED_PROCESS_DURATION+"+nextExceptionNode."+EXCEPTION_NODE_ATTR_ALLOWED_WAITING_DURATION+") + ");
		qb.append("completedRel."+ElementFunctions.RELATIONSHIP_ATTR_PROCESS_DURATION+" ");
		qb.append("WITH completedRel, firstExceptionNode, nextExceptionNode ");
		qb.append("SET firstExceptionNode."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"=(firstExceptionNode."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"-1) ");
		qb.append("SET nextExceptionNode."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=(nextExceptionNode."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"+1) ");
		qb.append("DELETE completedRel;");
		neo4j.sendCypherQuery(qb.toString(), properties);
	}
	
	//TODO RETURN
	public void completeExceptionElement(String nodeId, String currentExceptionCode, String elementId, String workerId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ElementFunctions.ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(EXCEPTION_NODE_ATTR_ID, currentExceptionCode);
		properties.put(ElementFunctions.ELEMENT_ATTR_WORKER_ID, workerId);
		properties.put(ElementFunctions.ELEMENT_ATTR_STATUS, ElementFunctions.CONSTANT_STATUS_WAITING);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+") ");
		qb.append("MATCH (report:"+NodeFunctions.NODE_LABEL_BPO_REPORT+")-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]->node ");
		qb.append("MATCH node-[current:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->(firstExceptionNode:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
		qb.append("MATCH (element:"+ElementFunctions.NODE_LABEL_ELEMENT+")-[completedRel:"+ElementFunctions.RELATIONSHIP_LABEL_TASK_IN_PROCESS+"]->firstExceptionNode ");
		qb.append("MATCH element-[taggedException:"+RELATIONSHIP_LABEL_TAGGED_AS_EXCEPTION+"]->node ");
		qb.append("WHERE node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} ");
		qb.append("AND element."+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+"={"+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+"} ");
		qb.append("AND completedRel."+ElementFunctions.ELEMENT_ATTR_WORKER_ID+"={"+ElementFunctions.ELEMENT_ATTR_WORKER_ID+"} ");
		qb.append("AND firstExceptionNode."+EXCEPTION_NODE_ATTR_ID+"={"+EXCEPTION_NODE_ATTR_ID+"} ");
		qb.append("CREATE element-[resolvedException:"+"RESOLVED_EXCEPTION"+"]->node ");
		qb.append("CREATE element-[completedTask:"+ElementFunctions.RELATIONSHIP_LABEL_COMPLETED_TASK+"]->firstExceptionNode ");
		qb.append("CREATE element-[nextNodeRel:"+ElementFunctions.RELATIONSHIP_LABEL_TASK_AT+"]->node ");
		qb.append("SET element."+ElementFunctions.ELEMENT_ATTR_STATUS+" = {"+ElementFunctions.ELEMENT_ATTR_STATUS+"} ");
		qb.append("SET completedRel."+ElementFunctions.RELATIONSHIP_ATTR_END_PROCESSING_TIME+"=TIMESTAMP() ");
		qb.append("SET nextNodeRel."+ElementFunctions.RELATIONSHIP_ATTR_START_WAITING_TIME+"=TIMESTAMP() ");
		qb.append("SET nextNodeRel."+ElementFunctions.RELATIONSHIP_ATTR_START_PROCESSING_TIME+"='' ");
		qb.append("SET nextNodeRel."+ElementFunctions.RELATIONSHIP_ATTR_END_PROCESSING_TIME+"='' ");
		qb.append("SET completedRel."+ElementFunctions.RELATIONSHIP_ATTR_PROCESS_DURATION+"=completedRel."+ElementFunctions.RELATIONSHIP_ATTR_END_PROCESSING_TIME+"-completedRel."+ElementFunctions.RELATIONSHIP_ATTR_START_PROCESSING_TIME+" ");
		qb.append("SET completedTask=completedRel ");
		qb.append("SET resolvedException=taggedException ");
		qb.append("SET element."+ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"=element."+ElementFunctions.ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+" + ");
		qb.append("completedRel."+ElementFunctions.RELATIONSHIP_ATTR_PROCESS_DURATION+" ");
		qb.append("WITH completedRel, firstExceptionNode, report, taggedException ");
		qb.append("SET firstExceptionNode."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"=(firstExceptionNode."+REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"-1) ");
		qb.append("SET report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"+1 ");
		qb.append("DELETE completedRel, taggedException;");
		neo4j.sendCypherQuery(qb.toString(), properties);
	}
	
		
	
	public BPONotifications assignWorkerToExceptionElement(String nodeId, String exceptionCode, String elementId, String workerId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ElementFunctions.ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(EXCEPTION_NODE_ATTR_ID, exceptionCode);
		properties.put(ElementFunctions.ELEMENT_ATTR_WORKER_ID, workerId);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+")-[:"+RELATIONSHIP_LABEL_EXCEPTION_TO+"]->(exception:"+NODE_LABEL_BPO_EXCEPTION_NODE+") ");
		qb.append("MATCH (element:"+ElementFunctions.NODE_LABEL_ELEMENT+")-[task:"+ElementFunctions.RELATIONSHIP_LABEL_TASK_AT+"]->exception ");
		qb.append("WHERE node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} ");
		qb.append("AND element."+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+"={"+ElementFunctions.ELEMENT_ATTR_ELEMENT_ID+"} ");
		qb.append("AND exception."+EXCEPTION_NODE_ATTR_ID+"={"+EXCEPTION_NODE_ATTR_ID+"} ");
		qb.append("SET task."+ElementFunctions.ELEMENT_ATTR_WORKER_ID+"={"+ElementFunctions.ELEMENT_ATTR_WORKER_ID+"} ");
		qb.append("RETURN COUNT(task) AS data;");
		
		List<Map<String, Object>> dbResult = neo4j.sendCypherQuery(qb.toString(), properties);
		if(dbResult.size()>0) {
			int statusCode = DataUtilities.toInteger(dbResult.get(0).get("data"));
			if(statusCode>0) {
				return BPONotifications.EXCEPTION_ELEMENT_WORKER_ADDED;
			}
		}
		
		return BPONotifications.EXCEPTION_EITHER_NODE_ELEMENT_DOES_NOT_EXIST;
		
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
