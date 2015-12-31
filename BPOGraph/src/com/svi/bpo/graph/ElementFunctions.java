package com.svi.bpo.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.svi.bpo.graph.obj.ElementObject;
import com.svi.bpo.graph.utils.DataUtilities;
import com.svi.tools.neo4j.rest.service.Neo4jRestService;

public class ElementFunctions {
	
	private static final String RELATIONSHIP_LABEL_TASK_IN_PROCESS = "TASK_IN_PROCESS";
	private static final String RELATIONSHI_LABEL_BPO_INCOMPLETE_TASK_AT = "BPO_INCOMPLETE_TASK_AT";
	private static final String NOTIFICATION_WORKER_REMOVED_SUCCESSFULLY = "Worker Removed Successfully";
	private static final String NOTIFICATION_FAILED_NO_WORKER_ASSIGNED_TO_ELEMENT = "Failed, No Worker Assigned To Element";
	private static final String NOTIFICATION_FAILED_THERE_IS_WORKER_ASSIGNED_TO_THE_ELEMENT = "Failed, There is Worker Assigned to the Element";
	private static final String NOTIFICATION_WORKER_ADDED_SUCCESSFULLY = "Worker Added Successfully";
	protected static final String ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION = "estimatedCompletionDuration";
	private static final String ELEMENT_ATTR_TARGET_COMPLETION_DURATION = "targetCompletionDuration";
	protected static final String RELATIONSHIP_LABEL_COMPLETED_TASK = "BPO_COMPLETED_TASK";
	protected static final String RELATIONSHIP_LABEL_TASK_AT = "BPO_TASK_AT";
	protected static final String NODE_LABEL_ELEMENT = "BPO_ELEMENT";
	protected static final String NOTIFICATION_INVALID_PRIORITY_RANGE = "Insert Element Failed: Element Priority must be in 0-9 range";
	protected static final String ELEMENT_ATTR_PROCESS_DURATION = "processDuration";
	protected static final String ELEMENT_ATTR_END_PROCESSING_TIME = "endProcessingTime";
	protected static final String ELEMENT_ATTR_START_PROCESSING_TIME = "startProcessingTime";
	protected static final String ELEMENT_ATTR_START_WAITING_TIME = "startWaitingTime";
	protected static final String ELEMENT_ATTR_WAITING_DURATION = "waitingDuration";
	protected static final String ELEMENT_ATTR_TOTAL_ERROR = "totalError";
	protected static final String ELEMENT_ATTR_TOTAL_OUPUT = "totalOuput";
	protected static final String ELEMENT_ATTR_EXTRA3 = "extra3";
	protected static final String ELEMENT_ATTR_EXTRA2 = "extra2";
	protected static final String ELEMENT_ATTR_EXTRA1 = "extra1";
	protected static final String ELEMENT_ATTR_FILE_POINTER = "filePointer";
	protected static final String ELEMENT_ATTR_STATUS = "status";
	protected static final String ELEMENT_ATTR_WORKER_ID = "workerId";
	protected static final String ELEMENT_ATTR_PRIORITY = "priority";
	protected static final String ELEMENT_ATTR_ELEMENT_ID = "elementId";
	protected static final String ELEMENT_VAR_CAN_MEET_DEADLINE = "canMeetDeadline";
	
	protected static final String CONSTANT_STATUS_IN_PROCESS = "P";
	protected static final String CONSTANT_STATUS_WAITING = "W";
	protected static final String CONSTANT_STATUS_COMPLETE = "C";
	protected static final String CONSTANT_STATUS = "status";
	protected static final String CONSTANT_NEXT_NODE_ID = "nextNodeId";

	protected static final String NOTIFICATION_ELEMENT_ADDED_SUCCESSFULY = "Element Added Successfuly";
	protected static final String NOTIFICATION_CHANGE_WORKED_ID_SUCCESSFULLY = "Change Worked Id Successfully";
	protected static final String NOTIFICATION_ELEMENT_DELETED_SUCCESSFULLY = "Element Deleted Successfully";
	protected static final String NOTIFICATION_ELEMENT_IS_NOT_IN_PROGRESS = "Element is not in-progress";
	protected static final String NOTIFICATION_FAILED_CHECK_CONNECTION = "Failed, Check Connection";
	protected static final String NOTIFICATION_CHANGE_ELEMENT_PRIORITY_SUCCESS = "CHANGE ELEMENT PRIORITY SUCCESS";
	protected static final String NOTIFICATION_ELEMENT_DOES_NOT_EXIST = "Element Does Not Exist";
	protected static final String NOTIFICATION_ELEMENT_ALREADY_EXIST = "Element Already Exist";
	protected static final String NOTIFICATION_NODE_ID_DOES_NOT_EXIST = "Node Id Does Not Exist";

	private Neo4jRestService neo4j;
	private BPO bpo;
	
	protected ElementFunctions(Neo4jRestService neo4j, BPO bpo) {
		this.neo4j = neo4j;
		this.bpo = bpo;
	}
	
	/********************
	 * CREATE FUNCTIONS *
	 ********************/
	/**
	 * Insert Element to BPO
	 * @param elementId = Unique to the entire BPO
	 * @param priority = 0 - 9, 9 = Highest, 0 = Lowest
	 * @param nodeId = Uniqued ID of Node
	 * @param fileLocation = location of files for the element, it may be a filesystem path or a GFS Pointer
	 * @param extra1
	 * @param extra2
	 * @param extra3
	 * @param totalOuput
	 * @param totalError
	 * @return
	 */
	public String insertElement(String elementId, int priority, String nodeId, String filePointer, 
			String extra1, String extra2, String extra3, int totalOuput, int totalError) {
		
		if(!checkIfValidPriority(priority)) {
			return NOTIFICATION_INVALID_PRIORITY_RANGE;
		}
		
		if(!bpo.getNodeFunctions().isNodeExist(nodeId)) {
			return NOTIFICATION_NODE_ID_DOES_NOT_EXIST;
		} else if(isElementExist(elementId)) {
			
			return NOTIFICATION_ELEMENT_ALREADY_EXIST;
		} 
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(ELEMENT_ATTR_PRIORITY, priority);
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_FILE_POINTER, filePointer);
		properties.put(ELEMENT_ATTR_EXTRA1, extra1);
		properties.put(ELEMENT_ATTR_EXTRA2, extra2);
		properties.put(ELEMENT_ATTR_EXTRA3, extra3);
		properties.put(ELEMENT_ATTR_TOTAL_OUPUT, totalOuput);
		properties.put(ELEMENT_ATTR_TOTAL_ERROR, totalError);
		properties.put(ELEMENT_ATTR_STATUS, CONSTANT_STATUS_WAITING);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}}) "
				+ "MATCH (report:"+NodeFunctions.NODE_LABEL_BPO_REPORT+")-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]->node "
				+ "MATCH (cluster:"+NodeFunctions.NODE_LABEL_CLUSTER+")<-[:"+NodeFunctions.RELATIONSHIP_LABEL_BPO_NODE_IN+"]-node "
				+ "CREATE (element:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}) "
				+ "SET element."+ELEMENT_ATTR_PRIORITY+"={"+ELEMENT_ATTR_PRIORITY+"} "
				+ "SET element."+ELEMENT_ATTR_STATUS+"={"+ELEMENT_ATTR_STATUS+"} "
				+ "SET element."+ELEMENT_ATTR_FILE_POINTER+"={"+ELEMENT_ATTR_FILE_POINTER+"} " 
				+ "SET element."+ELEMENT_ATTR_EXTRA1+"={"+ELEMENT_ATTR_EXTRA1+"} " 
				+ "SET element."+ELEMENT_ATTR_EXTRA2+"={"+ELEMENT_ATTR_EXTRA2+"} " 
				+ "SET element."+ELEMENT_ATTR_EXTRA3+"={"+ELEMENT_ATTR_EXTRA3+"} " 
				+ "SET element."+ELEMENT_ATTR_TOTAL_OUPUT+"={"+ELEMENT_ATTR_TOTAL_OUPUT+"} " 
				+ "SET element."+ELEMENT_ATTR_TOTAL_ERROR+"={"+ELEMENT_ATTR_TOTAL_ERROR+"} "
				+ "SET element."+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+"=cluster."+NodeFunctions.CLUSTER_ATTR_TARGET_COMPLETION_TIME+" "
				+ "SET element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"=cluster."+NodeFunctions.CLUSTER_ATTR_TARGET_COMPLETION_TIME+" "
				+ "CREATE element-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]->node "
				+ "SET r."+ELEMENT_ATTR_START_WAITING_TIME+"=TIMESTAMP() "
				+ "SET r."+ELEMENT_ATTR_START_PROCESSING_TIME+"='' "
				+ "SET r."+ELEMENT_ATTR_END_PROCESSING_TIME+"='' "
				+ "SET report."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+"=report."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+"+1 "
				+ "SET report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"+1;");
		
		boolean result = neo4j.sendCypherBooleanResult(qb.toString(), properties);
		
		if(result) {
			return NOTIFICATION_ELEMENT_ADDED_SUCCESSFULY;
		} else {
			return NOTIFICATION_FAILED_CHECK_CONNECTION;
		}
	}

	/**
	 * Insert Element to BPO
	 * @param elementId
	 * @param priority
	 * @param nodeId
	 * @param workerId
	 * @return
	 */
	public String insertElement(String elementId, int priority, String nodeId) {
		if(!checkIfValidPriority(priority)) {
			return NOTIFICATION_INVALID_PRIORITY_RANGE;
		}
		
		if(!bpo.getNodeFunctions().isNodeExist(nodeId)) {
			return NOTIFICATION_NODE_ID_DOES_NOT_EXIST;
		} else if(isElementExist(elementId)) {
			return NOTIFICATION_ELEMENT_ALREADY_EXIST;
		} 
		
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(ELEMENT_ATTR_PRIORITY, priority);
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_STATUS, CONSTANT_STATUS_WAITING);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}}) "
				+ "MATCH (cluster:"+NodeFunctions.NODE_LABEL_CLUSTER+")<-[:"+NodeFunctions.RELATIONSHIP_LABEL_BPO_NODE_IN+"]-node "
				+ " MATCH (report:"+NodeFunctions.NODE_LABEL_BPO_REPORT+")-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]->node "

				+ "MATCH (report:"+NodeFunctions.NODE_LABEL_BPO_REPORT+")-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]->node "

				+ "CREATE (element:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}) "
				+ "SET element."+ELEMENT_ATTR_PRIORITY+"={"+ELEMENT_ATTR_PRIORITY+"} "
				+ "SET element."+ELEMENT_ATTR_STATUS+"={"+ELEMENT_ATTR_STATUS+"} "
					+ "CREATE element-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]->node "
				+ "SET r."+ELEMENT_ATTR_START_WAITING_TIME+"=TIMESTAMP() "
				+ "SET r."+ELEMENT_ATTR_START_PROCESSING_TIME+"='' "
				+ "SET r."+ELEMENT_ATTR_END_PROCESSING_TIME+"='' "
				
			
				+ "SET element."+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+"=cluster."+NodeFunctions.CLUSTER_ATTR_TARGET_COMPLETION_TIME+" "
				+ "SET element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"=cluster."+NodeFunctions.CLUSTER_ATTR_TARGET_COMPLETION_TIME+" "
				+ "SET report."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+"=report."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+"+1 "
				+ "SET report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"+1;");
		
		
		boolean result = neo4j.sendCypherBooleanResult(qb.toString(), properties);
		
		if(result) {
			return NOTIFICATION_ELEMENT_ADDED_SUCCESSFULY;
		} else {
			return NOTIFICATION_FAILED_CHECK_CONNECTION;
		}
	}
	
	/**
	 * Insert Element to BPO
	 * @param elementId
	 * @param priority
	 * @param nodeId
	 * @param workerId
	 * @return
	 */
	public String insertElement(String elementId, int priority, String nodeId,String workerId) {//workerID added by jm
		if(!checkIfValidPriority(priority)) {
			return NOTIFICATION_INVALID_PRIORITY_RANGE;
		}
		
		if(!bpo.getNodeFunctions().isNodeExist(nodeId)) {
			return NOTIFICATION_NODE_ID_DOES_NOT_EXIST;
		} else if(isElementExist(elementId)) {
			return NOTIFICATION_ELEMENT_ALREADY_EXIST;
		} 
		String worker ="SET r."+ELEMENT_ATTR_WORKER_ID+"={"+ELEMENT_ATTR_WORKER_ID+"} ";// added by jm
		if (workerId.equals("")){// added by jm
			worker = "";// 
		}
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(ELEMENT_ATTR_PRIORITY, priority);
		properties.put(ELEMENT_ATTR_WORKER_ID, workerId);// added by jm
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_STATUS, CONSTANT_STATUS_WAITING);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}}) "
				+ "MATCH (cluster:"+NodeFunctions.NODE_LABEL_CLUSTER+")<-[:"+NodeFunctions.RELATIONSHIP_LABEL_BPO_NODE_IN+"]-node "


				

				+ "MATCH (report:BPO_NODE_REPORT)-[:REPORTING_OF]->node "


				+ "MATCH (report:"+NodeFunctions.NODE_LABEL_BPO_REPORT+")-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]->node "

				+ "CREATE (element:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}) "
				+ "SET element."+ELEMENT_ATTR_PRIORITY+"={"+ELEMENT_ATTR_PRIORITY+"} "
				+ "SET element."+ELEMENT_ATTR_STATUS+"={"+ELEMENT_ATTR_STATUS+"} "
				+ "CREATE element-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]->node "
				+ "SET r."+ELEMENT_ATTR_START_WAITING_TIME+"=TIMESTAMP() "
				+ "SET r."+ELEMENT_ATTR_START_PROCESSING_TIME+"='' "
				+ "SET r."+ELEMENT_ATTR_END_PROCESSING_TIME+"='' "
				+ worker//added by jm
			
				+ "SET element."+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+"=cluster."+NodeFunctions.CLUSTER_ATTR_TARGET_COMPLETION_TIME+" "
				+ "SET element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"=cluster."+NodeFunctions.CLUSTER_ATTR_TARGET_COMPLETION_TIME+" "
				+ "SET report."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+"=report."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+"+1 "
				+ "SET report."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+"=report."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+"+1 "
				+ "SET report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"+1;");
		
		System.out.println("QUERY INSERT ELEMENT: " + qb.toString());
		boolean result = neo4j.sendCypherBooleanResult(qb.toString(), properties);
		
		if(result) {
			return NOTIFICATION_ELEMENT_ADDED_SUCCESSFULY;
		} else {
			return NOTIFICATION_FAILED_CHECK_CONNECTION;
		}
	}

	/******************
	 * READ FUNCTIONS *
	 ******************/	
	
	public String viewElementTable() {
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (element:"+NODE_LABEL_ELEMENT+")-[task:"+RELATIONSHIP_LABEL_TASK_AT+"]->(node:"+NodeFunctions.NODE_LABEL_BPO_NODE+") RETURN element."+ELEMENT_ATTR_ELEMENT_ID+" As "+ELEMENT_ATTR_ELEMENT_ID+",element."+ELEMENT_ATTR_PRIORITY+" As "+ELEMENT_ATTR_PRIORITY+",");
		qb.append("node."+NodeFunctions.NODE_ATTR_NODE_ID+" As "+NodeFunctions.NODE_ATTR_NODE_ID+",task."+ELEMENT_ATTR_WORKER_ID+" AS "+ELEMENT_ATTR_WORKER_ID+",task."+ELEMENT_ATTR_START_WAITING_TIME+" As "+ ELEMENT_ATTR_START_WAITING_TIME+",");
		qb.append("task."+ELEMENT_ATTR_START_PROCESSING_TIME+" As "+ELEMENT_ATTR_START_PROCESSING_TIME+",task."+ELEMENT_ATTR_END_PROCESSING_TIME+" AS "+ELEMENT_ATTR_END_PROCESSING_TIME+",element."+ELEMENT_ATTR_EXTRA1+" As "+ ELEMENT_ATTR_EXTRA1+",");
		qb.append("element."+ELEMENT_ATTR_EXTRA2+" As "+ELEMENT_ATTR_EXTRA2+",element."+ELEMENT_ATTR_EXTRA3+" AS "+ELEMENT_ATTR_EXTRA3+",element."+ELEMENT_ATTR_FILE_POINTER+" As "+ ELEMENT_ATTR_FILE_POINTER+",");
		qb.append("element."+ELEMENT_ATTR_TOTAL_OUPUT+" As " + ELEMENT_ATTR_TOTAL_OUPUT+ ",element."+ELEMENT_ATTR_TOTAL_ERROR+" As "+ELEMENT_ATTR_TOTAL_ERROR+",element."+ ELEMENT_ATTR_STATUS+" As " + ELEMENT_ATTR_STATUS );// NOTE: ELEMENT STATUS IS JUST ADDED 
		//Element Table: [[[ELEM0, NODE1, 0, W, , 11/26/2015 15:50:03, null, null, null, null, null, null, 2611125, 0, 0, 0], [ELEM1, NODE1, 0, W, , 11/26/2015 15:50:03, null, null, null, null, null, null, 2611125, 0, 0, 0], [ELEM2, NODE1, 0, W, , 11/26/2015 15:50:03, null, null, null, null, null, null, 2611125, 0, 0, 0], [ELEM3, NODE1, 0, W, , 11/26/2015 15:50:03, null, null, null, null, null, null, 2611125, 0, 0, 0], [ELEM4, NODE1, 0, W, , 11/26/2015 15:50:03, null, null, null, null, null, null, 2611125, 0, 0, 0]], [], [], [[elem1, hey, 0, W, W1, 11/26/2015 16:33:34, null, null, null, , , , 0, 0, 0, 0]]]
		//Element Table: [[], [[elem1, hey2, 0, W, , 11/26/2015 17:07:47, null, null, null, extra1, extra2, extra3, 0, 0, 0, 0]], []]

		String elemetlist = neo4j.sendCypherQuery(qb.toString()).toString();
	//	System.out.println("elemetlist: " + elemetlist);
		return elemetlist;
	}
	
	/**
	 * Check If Element Exist
	 * @param elementId
	 * @return TRUE if exist, FALSE if not
	 */
	public boolean isElementExist(String elementId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}) RETURN COUNT(*) AS data");
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
	 * Check if Element is currently in "Process" or "Waiting" in Node
	 * @param nodeId
	 * @param elementId
	 * @return
	 */
	public boolean isElementInNode(String nodeId, String elementId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[:"+RELATIONSHIP_LABEL_TASK_AT+"|"+RELATIONSHIP_LABEL_TASK_IN_PROCESS+"]-(:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}) RETURN COUNT(*) AS data");
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
	 * Internal use
	 * @param elementId
	 * @return Status of Element
	 */
	private String getElementStatus(String elementId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (element:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}) RETURN element.status AS status;");
		List<Map<String, Object>> data = neo4j.sendCypherQuery(qb.toString(), properties);
		
		if(data.size()>0) {
			return (String) data.get(0).get(ELEMENT_ATTR_STATUS);
		}
		
		return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
	}
	
	/**
	 * @param elementId
	 * @param nodeId
	 * @param workerId
	 * @return NULL if any of the parameter is incorrect
	 */
	public ElementObject getElement(String elementId, String nodeId, String workerId) {
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(ELEMENT_ATTR_WORKER_ID, workerId);
		properties.put(CONSTANT_STATUS_IN_PROCESS, CONSTANT_STATUS_IN_PROCESS);
		properties.put(ELEMENT_ATTR_STATUS, CONSTANT_STATUS_WAITING);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[r:"+RELATIONSHIP_LABEL_TASK_AT+" {workerId:{"+ELEMENT_ATTR_WORKER_ID+"}}]-(element:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"},"+ELEMENT_ATTR_STATUS+":{"+ELEMENT_ATTR_STATUS+"}}) "
				+ "MATCH (report:"+NodeFunctions.NODE_LABEL_BPO_REPORT+")-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]->node "
				+ "MATCH (cluster:"+NodeFunctions.NODE_LABEL_CLUSTER+")<-[:"+NodeFunctions.RELATIONSHIP_LABEL_BPO_NODE_IN+"]-node "
				+ "CREATE element-[taskInProcess:"+RELATIONSHIP_LABEL_TASK_IN_PROCESS+"]->node "
				+ "SET element.status = {"+CONSTANT_STATUS_IN_PROCESS+"} "
				+ "SET r."+ELEMENT_ATTR_START_PROCESSING_TIME+"=TIMESTAMP() "
				+ "SET r."+ELEMENT_ATTR_WAITING_DURATION+"=r."+ELEMENT_ATTR_START_PROCESSING_TIME+"-"+"r."+ELEMENT_ATTR_START_WAITING_TIME+" "
				+ "SET taskInProcess = r "
				+ "SET element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"=element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"-(node."+NodeFunctions.NODE_ATTR_ALLOWED_WAITING_DURATION+"-r."+ELEMENT_ATTR_WAITING_DURATION+") "
				+ "SET report."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_DURATION+"=report."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_DURATION+"+r."+ELEMENT_ATTR_WAITING_DURATION+" "
				+ "SET report."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_ELEMENT+"=report."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_ELEMENT+"+1 "
				+ "SET report."+NodeFunctions.REPORT_ATTR_AVERAGE_WAITING_DURATION+"=(report."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_DURATION+"/report."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+") "
				+ "SET report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=(report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"-1) "
				+ "SET report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"=(report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"+1) "
				+ "WITH r, node, element "
				+ "DELETE r "
				+ "RETURN node."+NodeFunctions.NODE_ATTR_NODE_ID+" AS nodeId, "
				+ "element."+ELEMENT_ATTR_ELEMENT_ID+" AS elementId, "
				+ "element."+ELEMENT_ATTR_PRIORITY+" AS "+ELEMENT_ATTR_PRIORITY+", "
				+ "element."+ELEMENT_ATTR_STATUS+" AS "+ELEMENT_ATTR_STATUS+", "
				+ "element."+ELEMENT_ATTR_EXTRA1+" AS "+ELEMENT_ATTR_EXTRA1+", "
				+ "element."+ELEMENT_ATTR_EXTRA2+" AS "+ELEMENT_ATTR_EXTRA2+", "
				+ "element."+ELEMENT_ATTR_EXTRA3+" AS "+ELEMENT_ATTR_EXTRA3+", "
				+ "element."+ELEMENT_ATTR_FILE_POINTER+" AS "+ELEMENT_ATTR_FILE_POINTER+", "
				+ "element."+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+" AS "+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+", "
				+ "element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+" AS "+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+";");
		ElementObject element = null;
		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(qb.toString(), properties);
		for(int i=0; i<dataReturned.size(); i++) {
			Map<String, Object> data = dataReturned.get(i);
			String ndeId =  data.get("nodeId").toString();
			String elemId =  data.get("elementId").toString();
			int priority = (int) data.get(ELEMENT_ATTR_PRIORITY);
			String status =  data.get(ELEMENT_ATTR_STATUS).toString();
			String extra1 =  data.get(ELEMENT_ATTR_EXTRA1).toString();
			String extra2 =  data.get(ELEMENT_ATTR_EXTRA2).toString();
			String extra3 =  data.get(ELEMENT_ATTR_EXTRA3).toString();
			String filePointer =  data.get(ELEMENT_ATTR_FILE_POINTER).toString();
			Number tmpTCD = (Number)data.get(ELEMENT_ATTR_TARGET_COMPLETION_DURATION);
			long targetCompletionDuration = tmpTCD.longValue();
			Number tmpECD = (Number)data.get(ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION);
			long estimatedCompletionDuration = tmpECD.longValue();
			
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
	
	/**
	 * View Assigned Elements to Specific Worker
	 * @param elementId
	 * @param nodeId
	 * @param workerId
	 * @return
	 */
	public ElementObject[] viewElementsOfWorker(String elementId, String nodeId, String workerId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(ELEMENT_ATTR_WORKER_ID, workerId);
		
		String extWhere = "AND element."+ELEMENT_ATTR_ELEMENT_ID+"={"+ELEMENT_ATTR_ELEMENT_ID+"} ";
		if(elementId.contains("*")) {
			extWhere =" ";
		}
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]-(element:"+NODE_LABEL_ELEMENT+") "
				+ "WHERE r."+ELEMENT_ATTR_WORKER_ID+"={"+ELEMENT_ATTR_WORKER_ID+"} "+extWhere
				+ "RETURN node."+NodeFunctions.NODE_ATTR_NODE_ID+" AS nodeId, "
				+ "element."+ELEMENT_ATTR_ELEMENT_ID+" AS elementId, "
				+ "element."+ELEMENT_ATTR_PRIORITY+" AS "+ELEMENT_ATTR_PRIORITY+", "
				+ "element."+ELEMENT_ATTR_STATUS+" AS "+ELEMENT_ATTR_STATUS+", "
				+ "element."+ELEMENT_ATTR_EXTRA1+" AS "+ELEMENT_ATTR_EXTRA1+", "
				+ "element."+ELEMENT_ATTR_EXTRA2+" AS "+ELEMENT_ATTR_EXTRA2+", "
				+ "element."+ELEMENT_ATTR_EXTRA3+" AS "+ELEMENT_ATTR_EXTRA3+", "
				+ "element."+ELEMENT_ATTR_FILE_POINTER+" AS "+ELEMENT_ATTR_FILE_POINTER+", "
				+ "element."+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+" AS "+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+", "
				+ "element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+" AS "+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+";");
		
		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(qb.toString(), properties);
		ElementObject[] dataToReturn = new ElementObject[dataReturned.size()];
		
		for(int i=0; i<dataReturned.size(); i++) {
			Map<String, Object> data = dataReturned.get(i);
			String ndeId =  data.get(NodeFunctions.NODE_ATTR_NODE_ID).toString();
			String elemId =  data.get(ELEMENT_ATTR_ELEMENT_ID).toString();
			int priority = (int) data.get(ELEMENT_ATTR_PRIORITY);
			String status =  data.get(ELEMENT_ATTR_STATUS).toString();
			String extra1 =  data.get(ELEMENT_ATTR_EXTRA1).toString();
			String extra2 =  data.get(ELEMENT_ATTR_EXTRA2).toString();
			String extra3 =  data.get(ELEMENT_ATTR_EXTRA3).toString();
			String filePointer =  data.get(ELEMENT_ATTR_FILE_POINTER).toString();
			Number tmpTCD = (Number)data.get(ELEMENT_ATTR_TARGET_COMPLETION_DURATION);
			long targetCompletionDuration = tmpTCD.longValue();
			Number tmpECD = (Number)data.get(ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION);
			long estimatedCompletionDuration = tmpECD.longValue();
			
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
			
			dataToReturn[i] = element;
		}
		return dataToReturn;
	}
	
	/**
	 * View Elements with Assigned Worker
	 * @param elementId, put * to get All Elements
	 * @param nodeId
	 * @return
	 */
	public ElementObject[] viewElementsHasWorker(String elementId, String nodeId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(CONSTANT_STATUS_WAITING, CONSTANT_STATUS_WAITING);
		
		String extWhere = "AND element."+ELEMENT_ATTR_ELEMENT_ID+"={"+ELEMENT_ATTR_ELEMENT_ID+"} ";
		if(elementId.contains("*")) {
			extWhere =" ";
		}
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]-(element:"+NODE_LABEL_ELEMENT+") "
				+ "WHERE HAS(r."+ELEMENT_ATTR_WORKER_ID+") "+extWhere
				+ "RETURN node."+NodeFunctions.NODE_ATTR_NODE_ID+" AS nodeId, "
				+ "element."+ELEMENT_ATTR_ELEMENT_ID+" AS elementId, "
				+ "element."+ELEMENT_ATTR_PRIORITY+" AS "+ELEMENT_ATTR_PRIORITY+", "
				+ "element."+ELEMENT_ATTR_STATUS+" AS "+ELEMENT_ATTR_STATUS+", "
				+ "element."+ELEMENT_ATTR_EXTRA1+" AS "+ELEMENT_ATTR_EXTRA1+", "
				+ "element."+ELEMENT_ATTR_EXTRA2+" AS "+ELEMENT_ATTR_EXTRA2+", "
				+ "element."+ELEMENT_ATTR_EXTRA3+" AS "+ELEMENT_ATTR_EXTRA3+", "
				+ "element."+ELEMENT_ATTR_FILE_POINTER+" AS "+ELEMENT_ATTR_FILE_POINTER+", "
				+ "element."+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+" AS "+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+", "
				+ "element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+" AS "+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+";");
		
		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(qb.toString(), properties);
		ElementObject[] dataToReturn = new ElementObject[dataReturned.size()];
		
		for(int i=0; i<dataReturned.size(); i++) {
			Map<String, Object> data = dataReturned.get(i);
			String ndeId =  data.get(NodeFunctions.NODE_ATTR_NODE_ID).toString();
			String elemId =  data.get(ELEMENT_ATTR_ELEMENT_ID).toString();
			int priority = (int) data.get(ELEMENT_ATTR_PRIORITY);
			String status =  data.get(ELEMENT_ATTR_STATUS).toString();
			String extra1 =  data.get(ELEMENT_ATTR_EXTRA1).toString();
			String extra2 =  data.get(ELEMENT_ATTR_EXTRA2).toString();
			String extra3 =  data.get(ELEMENT_ATTR_EXTRA3).toString();
			String filePointer =  data.get(ELEMENT_ATTR_FILE_POINTER).toString();
			Number tmpTCD = (Number)data.get(ELEMENT_ATTR_TARGET_COMPLETION_DURATION);
			long targetCompletionDuration = tmpTCD.longValue();
			Number tmpECD = (Number)data.get(ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION);
			long estimatedCompletionDuration = tmpECD.longValue();
			
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
			
			dataToReturn[i] = element;
		}
		return dataToReturn;
	}
	
	/**
	 * View Elements with No Worker Assigned
	 * @param elementId, put * to get All Elements
	 * @param nodeId
	 * @return
	 */
	public ElementObject[] viewElementsNoWorker(String elementId, String nodeId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(CONSTANT_STATUS_WAITING, CONSTANT_STATUS_WAITING);
		
		String extWhere = "AND element."+ELEMENT_ATTR_ELEMENT_ID+"={"+ELEMENT_ATTR_ELEMENT_ID+"} ";
		if(elementId.contains("*")) {
			extWhere =" ";
		}
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]-(element:"+NODE_LABEL_ELEMENT+") "
				+ "WHERE NOT HAS(r."+ELEMENT_ATTR_WORKER_ID+") "+extWhere
				+ "RETURN node."+NodeFunctions.NODE_ATTR_NODE_ID+" AS nodeId, "
				+ "element."+ELEMENT_ATTR_ELEMENT_ID+" AS elementId, "
				+ "element."+ELEMENT_ATTR_PRIORITY+" AS "+ELEMENT_ATTR_PRIORITY+", "
				+ "element."+ELEMENT_ATTR_STATUS+" AS "+ELEMENT_ATTR_STATUS+", "
				+ "element."+ELEMENT_ATTR_EXTRA1+" AS "+ELEMENT_ATTR_EXTRA1+", "
				+ "element."+ELEMENT_ATTR_EXTRA2+" AS "+ELEMENT_ATTR_EXTRA2+", "
				+ "element."+ELEMENT_ATTR_EXTRA3+" AS "+ELEMENT_ATTR_EXTRA3+", "
				+ "element."+ELEMENT_ATTR_FILE_POINTER+" AS "+ELEMENT_ATTR_FILE_POINTER+", "
				+ "element."+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+" AS "+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+", "
				+ "element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+" AS "+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+";");
		
		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(qb.toString(), properties);
		ElementObject[] dataToReturn = new ElementObject[dataReturned.size()];
		
		for(int i=0; i<dataReturned.size(); i++) {
			Map<String, Object> data = dataReturned.get(i);
			String ndeId =  data.get(NodeFunctions.NODE_ATTR_NODE_ID).toString();
			String elemId =  data.get(ELEMENT_ATTR_ELEMENT_ID).toString();
			int priority = (int) data.get(ELEMENT_ATTR_PRIORITY);
			String status =  data.get(ELEMENT_ATTR_STATUS).toString();
			String extra1 =  data.get(ELEMENT_ATTR_EXTRA1).toString();
			String extra2 =  data.get(ELEMENT_ATTR_EXTRA2).toString();
			String extra3 =  data.get(ELEMENT_ATTR_EXTRA3).toString();
			String filePointer =  data.get(ELEMENT_ATTR_FILE_POINTER).toString();
			Number tmpTCD = (Number)data.get(ELEMENT_ATTR_TARGET_COMPLETION_DURATION);
			long targetCompletionDuration = tmpTCD.longValue();
			Number tmpECD = (Number)data.get(ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION);
			long estimatedCompletionDuration = tmpECD.longValue();
			
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
			
			dataToReturn[i] = element;
		}
		return dataToReturn;
	}
	
	/**
	 * View Elements in Node regardless of their state
	 * @param nodeId, Put * to view for all nodes
	 * @param startPage gets the start page for the element
	 * @param limit the return  you want to limit, put 0 if you want to return all
	 * @return
	 */
	public ElementObject[] viewElementsInNode(String nodeId, int startPage, int limit) {
	int x = ((startPage-1)*limit);
	if (startPage == 0){
		x = 0 ;
	}
		String page = " SKIP " + x + " LIMIT " + limit;
		if (limit == 0)
		{
			page = " SKIP " + x;
		}
		
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (node:"+NodeFunctions.NODE_LABEL_BPO_NODE+")<-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]-(element:"+NODE_LABEL_ELEMENT+") ");
		qb.append("MATCH node-[:"+NodeFunctions.RELATIONSHIP_LABEL_BPO_NODE_IN +"]->(cluster:"+NodeFunctions.NODE_LABEL_CLUSTER+" )");
		
		if(!nodeId.contains("*")) {
			qb.append("WHERE node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} ");
		}
		qb.append("RETURN node."+NodeFunctions.NODE_ATTR_NODE_ID+" AS nodeId, ");
		qb.append("element."+ELEMENT_ATTR_ELEMENT_ID+" AS elementId, ");
		qb.append("element."+ELEMENT_ATTR_PRIORITY+" AS "+ELEMENT_ATTR_PRIORITY+", ");
		qb.append("element."+ELEMENT_ATTR_STATUS+" AS "+ELEMENT_ATTR_STATUS+", ");
		qb.append("element."+ELEMENT_ATTR_EXTRA1+" AS "+ELEMENT_ATTR_EXTRA1+", ");
		qb.append("element."+ELEMENT_ATTR_EXTRA2+" AS "+ELEMENT_ATTR_EXTRA2+", ");
		qb.append("element."+ELEMENT_ATTR_EXTRA3+" AS "+ELEMENT_ATTR_EXTRA3+", ");
		qb.append("element."+ELEMENT_ATTR_FILE_POINTER+" AS "+ELEMENT_ATTR_FILE_POINTER+", ");
		qb.append("element."+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+" AS "+ELEMENT_ATTR_TARGET_COMPLETION_DURATION+", ");
		qb.append("r."+ELEMENT_ATTR_WORKER_ID+" AS "+ELEMENT_ATTR_WORKER_ID+", ");
		
		qb.append("element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+" AS "+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION +",");
		qb.append("CASE element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+" <= cluster."+NodeFunctions.CLUSTER_ATTR_TARGET_COMPLETION_TIME +" WHEN TRUE THEN TRUE WHEN FALSE THEN FALSE END AS "+ ELEMENT_VAR_CAN_MEET_DEADLINE + page +";");

		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(qb.toString(), properties);
		ElementObject[] dataToReturn = new ElementObject[dataReturned.size()];
		
		for(int i=0; i<dataReturned.size(); i++) {
			Map<String, Object> data = dataReturned.get(i);
			String ndeId =  data.get(NodeFunctions.NODE_ATTR_NODE_ID).toString();
			String elemId =  data.get(ELEMENT_ATTR_ELEMENT_ID).toString();
			int priority = DataUtilities.toInteger(data.get(ELEMENT_ATTR_PRIORITY));
			String status =  data.get(ELEMENT_ATTR_STATUS).toString();
			String extra1 =  data.get(ELEMENT_ATTR_EXTRA1).toString();
			String extra2 =  data.get(ELEMENT_ATTR_EXTRA2).toString();
			String extra3 =  data.get(ELEMENT_ATTR_EXTRA3).toString();
			String filePointer =  data.get(ELEMENT_ATTR_FILE_POINTER).toString();
			long targetCompletionDuration = DataUtilities.toLongValue(data.get(ELEMENT_ATTR_TARGET_COMPLETION_DURATION));
			long estimatedCompletionDuration = DataUtilities.toLongValue(data.get(ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION));
			boolean canMeetDeadline = DataUtilities.toBoolean(data.get(ELEMENT_VAR_CAN_MEET_DEADLINE));
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
			element.setWorkerId(data.get(ELEMENT_ATTR_WORKER_ID).toString());
			element.setCanMeetDeadline(canMeetDeadline);
			dataToReturn[i] = element;
		}
		return dataToReturn;
	}
	
	/********************
	 * UPDATE FUNCTIONS *
	 ********************/
	
	/**
	 * Move Element from One Node to Another
	 * @param elemId
	 * @param currentNodeId
	 * @param nextNodeId
	 * @return Status of Request
	 */
	public String transferElement(String elemId, String currentNodeId, String nextNodeId) {
		if(!isElementExist(elemId)){
			return "NO ELEMENT EXIST";
		}
		else if(!bpo.getNodeFunctions().isNodeExist(currentNodeId)){
			return "CURRENT NODE DOES NOT EXIST";
		}
		else if(!bpo.getNodeFunctions().isNodeExist(nextNodeId)){
			return "NEXT NODE DOES NOT EXIST";
		}
		else if(!isElementInNode(currentNodeId,elemId)){
			return "ELEMENT IS NOT IN CURRENT NODE";
		}
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elemId);
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, currentNodeId);
	//	properties.put("NEXT", currQueue);
		StringBuilder query = new StringBuilder();
		query.append("MATCH (elem1:"+NODE_LABEL_ELEMENT+"{"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}})-[r:COMPLETED_TASK]->(n:"+NodeFunctions.NODE_LABEL_BPO_NODE+"{"+NodeFunctions.NODE_ATTR_NODE_ID+":'"+nextNodeId+"'}) return (COUNT(r)) As data");
		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(query.toString(), properties);
		
		if(dataReturned!=null) {
			int data = (int) dataReturned.get(0).get("data");
			if(data>0) {
				return "ELEMENT ALREADY COMPLETED IN NEXT NODE";
			}
		}
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (element:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}})-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]->(:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}}) ");
		qb.append(" MATCH (n:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":'"+nextNodeId+"'})");
		qb.append(" CREATE (element)-[rn:"+RELATIONSHIP_LABEL_TASK_AT+"]->(n)");
		qb.append(" SET rn=r ");
		qb.append(" WITH r");
		qb.append(" DELETE r");
		neo4j.sendCypherQuery(qb.toString(), properties);
		
		
		return "TRANFER_ELEMENT_COMPLETE";
	}
	
	/**
	 * Edit Element Priority
	 * @param nodeId
	 * @param elementId
	 * @param priority
	 * @return Status
	 *//*
	public String editElementPriority(String nodeId, String elementId, int priority) {
		if(!bpo.getNodeFunctions().isNodeExist(nodeId)) {
			return NOTIFICATION_NODE_ID_DOES_NOT_EXIST;
		} else if(!isElementInNode(nodeId, elementId)) {
			return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
		}
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(ELEMENT_ATTR_PRIORITY, priority);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {id:{nodeId}})<-[:"+RELATIONSHIP_LABEL_TASK_AT+"]-(element:"+NODE_LABEL_ELEMENT+" {id:{elementId}}) SET element.priority={priority};");
		
		boolean dataReturned = neo4j.sendCypherBooleanResult(qb.toString(), properties);
		
		if(!dataReturned) {
			return NOTIFICATION_FAILED_CHECK_CONNECTION;
		}
		return NOTIFICATION_CHANGE_ELEMENT_PRIORITY_SUCCESS;
	}*/
	
	/**
	 * Edit Element Worker Id
	 * @param nodeId
	 * @param elementId
	 * @param workerId
	 * @return Status
	 *//*
	public String editElementWorkerId(String nodeId, String elementId, String workerId) {
		if(!bpo.getNodeFunctions().isNodeExist(nodeId)) {
			return NOTIFICATION_NODE_ID_DOES_NOT_EXIST;
		} else if(!isElementInNode(nodeId, elementId)) {
			return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
		}
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(ELEMENT_ATTR_WORKER_ID, workerId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]-(:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}) "
				+ "SET r.workerId={"+ELEMENT_ATTR_WORKER_ID+"};");
		
		boolean dataReturned = neo4j.sendCypherBooleanResult(qb.toString(), properties);
		
		if(!dataReturned) {
			return NOTIFICATION_FAILED_CHECK_CONNECTION;
		}
		return NOTIFICATION_CHANGE_WORKED_ID_SUCCESSFULLY;
	}*/
	
	/**
	 * @param elementId = Element Id to Complete
	 * @param currentNodeId = Node Id of where the element is in process
	 * @param nextNodeId = Next Node Id of where the element will be transfered to continue processing
	 * @param workerId = Worker Id of worker in next node
	 * @param filePointer  = Edit File Pointer
	 * @param extra1 = Edit Extra1
	 * @param extra2 = Edit Extra2
	 * @param extra3 = Edit Extra3
	 * @param totalOuput = Increment Output Count of Current Node
	 * @param totalError = Increment Total Error Count of Current Node, <b>Put 0</b> If Error Count not belongs to current node and Call: 
	 * <br>
	 * <b>incrementNodeErrorCount(String nodeId, int errorCount)</b>, and put the nodeId of node who produced the error
	 * @return String Notification
	 */
	public String completeElement(String elementId, String nodeId, String nextNodeId, String workerId, 
			String gfsKey, String extra1, String extra2, String extra3, int totalOuput, int totalError) {
		
		if(isElementExist(elementId)) {
			if(!isElementInNode(nodeId, elementId)) {
				return "Element is not in Node: " + nodeId;
			} 
		} else {
			return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
		}
		
		String status = getElementStatus(elementId);
		boolean dbQueryStat = false;
		if(status.equals(CONSTANT_STATUS_WAITING)) {
			return NOTIFICATION_ELEMENT_IS_NOT_IN_PROGRESS;
		} else if(status.equals(CONSTANT_STATUS_IN_PROCESS)) {
			if(bpo.getNodeFunctions().isNodeExist(nextNodeId)) {
				if(totalOuput>0) {
					if(!bpo.getNodeFunctions().incrementNodeOutputCount(nodeId, totalOuput)) {
						return NOTIFICATION_FAILED_CHECK_CONNECTION;
					}
				}
				
				if(totalError>0) {
					if(!bpo.getNodeFunctions().incrementNodeErrorCount(nodeId, totalError)) {
						return NOTIFICATION_FAILED_CHECK_CONNECTION;
					}
				}
				 
				Map<String, Object> properties = new HashMap<>();
				properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
				properties.put(CONSTANT_NEXT_NODE_ID, nextNodeId);
				properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
				properties.put(ELEMENT_ATTR_WORKER_ID, workerId);
				properties.put(ELEMENT_ATTR_FILE_POINTER, gfsKey);
				properties.put(ELEMENT_ATTR_EXTRA1, extra1);
				properties.put(ELEMENT_ATTR_EXTRA2, extra2);
				properties.put(ELEMENT_ATTR_EXTRA3, extra3);
				properties.put(ELEMENT_ATTR_STATUS, CONSTANT_STATUS_WAITING);
				StringBuilder qb = new StringBuilder();

				qb.append("MATCH (firstNode:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[completedRel:"+RELATIONSHIP_LABEL_TASK_AT+"]-(element:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}), (nextNode:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+CONSTANT_NEXT_NODE_ID+"}}) "
						+ "MATCH firstNode<-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]-(firstNodeReport:"+NodeFunctions.NODE_LABEL_BPO_REPORT+") "
						+ "MATCH nextNode<-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]-(nextNodeReport:"+NodeFunctions.NODE_LABEL_BPO_REPORT+") "
						+ "CREATE element-[nextNodeRel:"+RELATIONSHIP_LABEL_TASK_AT+" {"+ELEMENT_ATTR_WORKER_ID+":{"+ELEMENT_ATTR_WORKER_ID+"}}]->nextNode "
						+ "CREATE element-[completedTask:COMPLETED_TASK]->firstNode "
						+ "SET element."+ELEMENT_ATTR_FILE_POINTER+" = {"+ELEMENT_ATTR_FILE_POINTER+"} "
						+ "SET element."+ELEMENT_ATTR_EXTRA1+" = {"+ELEMENT_ATTR_EXTRA1+"} "
						+ "SET element."+ELEMENT_ATTR_EXTRA2+" = {"+ELEMENT_ATTR_EXTRA2+"} "
						+ "SET element."+ELEMENT_ATTR_EXTRA3+" = {"+ELEMENT_ATTR_EXTRA3+"} "
						+ "SET element."+ELEMENT_ATTR_STATUS+" = {"+ELEMENT_ATTR_STATUS+"} " 
						+ "SET completedRel."+ELEMENT_ATTR_END_PROCESSING_TIME+"=TIMESTAMP() "
						+ "SET nextNodeRel."+ELEMENT_ATTR_START_WAITING_TIME+"=TIMESTAMP() "
						+ "SET completedRel."+ELEMENT_ATTR_PROCESS_DURATION+"=completedRel."+ELEMENT_ATTR_END_PROCESSING_TIME+"-completedRel."+ELEMENT_ATTR_START_PROCESSING_TIME+" "
						+ "SET completedTask=completedRel "
						+ "SET element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"=element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"-(node."+NodeFunctions.NODE_ATTR_ALLOWED_PROCESS_DURATION+"-completedRel."+ELEMENT_ATTR_PROCESS_DURATION+") "
						+ "WITH completedRel, firstNodeReport, nextNodeReport "
						+ "SET firstNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_DURATION+"=firstNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_DURATION+"+completedRel."+ELEMENT_ATTR_PROCESS_DURATION+" "
						+ "SET nextNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+"=nextNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+"+1 "
						+ "SET firstNodeReport."+NodeFunctions.REPORT_ATTR_AVERAGE_PROCESS_DURATION+"=(firstNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_DURATION+"/firstNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_ELEMENT+") "
						+ "SET firstNodeReport."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"=(firstNodeReport."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"-1) "
						+ "SET nextNodeReport."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=(nextNodeReport."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"+1) "
						+ "DELETE completedRel;");
				dbQueryStat = neo4j.sendCypherBooleanResult(qb.toString(), properties);
			} else {
				return "Node: " + nextNodeId + " Does Not Exist";
			}
		}
		
		if(dbQueryStat) {
			return "COMPLETE SUCCESS";
		} else {
			return NOTIFICATION_FAILED_CHECK_CONNECTION;
		}
	}
	

	/**
	 * @param elementId = Element Id to Complete
	 * @param currentNodeId = Node Id of where the element is in process
	 * @param nextNodeId = Next Node Id of where the element will be transfered to continue processing
	 * @param workerId = Worker Id of worker in next node
	 * @param totalOuput = Increment Output Count of Current Node
	 * @param totalError = Increment Total Error Count of Current Node, <b>Put 0</b> If Error Count not belongs to current node and Call: 
	 * <br>
	 * <b>incrementNodeErrorCount(String nodeId, int errorCount)</b>, and put the nodeId of node who produced the error
	 * @return String Notification
	 */
	public String completeElement(String elementId, String nodeId, String nextNodeId, String workerId, int totalOuput, int totalError) {
		if(isElementExist(elementId)) {
			if(!isElementInNode(nodeId, elementId)) {
				return "Element is not in Node: " + nodeId;
			} 
		} else {
			return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
		}
		
		String status = getElementStatus(elementId);
		boolean dbQueryStat = false;
		if(status.equals(CONSTANT_STATUS_WAITING)) {
			return NOTIFICATION_ELEMENT_IS_NOT_IN_PROGRESS;
		} else if(status.equals(CONSTANT_STATUS_IN_PROCESS)) {
			if(bpo.getNodeFunctions().isNodeExist(nextNodeId)) {
				if(totalOuput>0) {
					if(!bpo.getNodeFunctions().incrementNodeOutputCount(nodeId, totalOuput)) {
						return NOTIFICATION_FAILED_CHECK_CONNECTION;
					}
				}
				
				if(totalError>0) {
					if(!bpo.getNodeFunctions().incrementNodeErrorCount(nodeId, totalError)) {
						return NOTIFICATION_FAILED_CHECK_CONNECTION;
					}
				}
				 
				Map<String, Object> properties = new HashMap<>();
				properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
				properties.put(CONSTANT_NEXT_NODE_ID, nextNodeId);
				properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
				properties.put(ELEMENT_ATTR_WORKER_ID, workerId);
				properties.put(ELEMENT_ATTR_TOTAL_OUPUT, totalOuput);
				properties.put(ELEMENT_ATTR_TOTAL_ERROR, totalError);
				properties.put(ELEMENT_ATTR_STATUS, CONSTANT_STATUS_WAITING);
				StringBuilder qb = new StringBuilder();

				qb.append("MATCH (firstNode:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[completedRel:"+RELATIONSHIP_LABEL_TASK_AT+"]-(element:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}), (nextNode:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+CONSTANT_NEXT_NODE_ID+"}}) "
						+ "MATCH firstNode<-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]-(firstNodeReport:"+NodeFunctions.NODE_LABEL_BPO_REPORT+") "
						+ "MATCH nextNode<-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]-(nextNodeReport:"+NodeFunctions.NODE_LABEL_BPO_REPORT+") "
						+ "CREATE element-[nextNodeRel:"+RELATIONSHIP_LABEL_TASK_AT+" {"+ELEMENT_ATTR_WORKER_ID+":{"+ELEMENT_ATTR_WORKER_ID+"}}]->nextNode "
						+ "CREATE element-[completedTask:"+RELATIONSHIP_LABEL_COMPLETED_TASK+"]->firstNode "
						+ "SET element."+ELEMENT_ATTR_STATUS+" = {"+ELEMENT_ATTR_STATUS+"} " 
						+ "SET completedRel."+ELEMENT_ATTR_END_PROCESSING_TIME+"=TIMESTAMP() "
						+ "SET nextNodeRel."+ELEMENT_ATTR_START_WAITING_TIME+"=TIMESTAMP() "
						+ "SET completedRel."+ELEMENT_ATTR_PROCESS_DURATION+"=completedRel."+ELEMENT_ATTR_END_PROCESSING_TIME+"-completedRel."+ELEMENT_ATTR_START_PROCESSING_TIME+" "
						+ "SET completedTask=completedRel "
						+ "SET element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"=element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"-(firstNode."+NodeFunctions.NODE_ATTR_ALLOWED_PROCESS_DURATION+"-completedRel."+ELEMENT_ATTR_PROCESS_DURATION+") "
						+ "WITH completedRel, firstNodeReport, nextNodeReport "
						+ "SET firstNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_DURATION+"=firstNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_DURATION+"+completedRel."+ELEMENT_ATTR_PROCESS_DURATION+" "
						+ "SET nextNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+"=nextNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_WAITING_ELEMENT+"+1 "
						+ "SET firstNodeReport."+NodeFunctions.REPORT_ATTR_AVERAGE_PROCESS_DURATION+"=(firstNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_DURATION+"/firstNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_ELEMENT+") "
						+ "SET firstNodeReport."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"=(firstNodeReport."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"-1) "
						+ "SET nextNodeReport."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=(nextNodeReport."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"+1) "
						+ "DELETE completedRel;");
				dbQueryStat = neo4j.sendCypherBooleanResult(qb.toString(), properties);
			} else {
				return "Node: " + nextNodeId + " Does Not Exist";
			}
		}
		
		if(dbQueryStat) {
			return "COMPLETE SUCCESS";
		} else {
			return NOTIFICATION_FAILED_CHECK_CONNECTION;
		}
	}
	
	/**
	 * Assign Worker to Element
	 * @param nodeId
	 * @param elementId
	 * @param workerId
	 * @return Status
	 */
	public String assignWorkerToElement(String nodeId, String elementId, String workerId) {
		if(!bpo.getElementFunctions().isElementExist(elementId)) {
			return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
		} else if(!isElementInNode(nodeId, elementId)) {
			return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
		}
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(ELEMENT_ATTR_WORKER_ID, workerId);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]-(:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}) ");
		qb.append("WHERE NOT HAS (r."+ELEMENT_ATTR_WORKER_ID+") ");
		qb.append("SET r."+ELEMENT_ATTR_WORKER_ID+"={"+ELEMENT_ATTR_WORKER_ID+"} ");
		qb.append("RETURN COUNT(r) AS count;");
		
		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(qb.toString(), properties);
		
		if(dataReturned.size()>0) {
			int data = (int) dataReturned.get(0).get("count");
			if(data>0) {
				return NOTIFICATION_WORKER_ADDED_SUCCESSFULLY;
			} 
		}
		return NOTIFICATION_FAILED_THERE_IS_WORKER_ASSIGNED_TO_THE_ELEMENT;
	}
	
	/**
	 * Remove Worker from Element
	 * @param nodeId
	 * @param elementId
	 * @return Status
	 */
	public String removeAssignedWorkerToElement(String nodeId, String elementId) {
		if(!bpo.getElementFunctions().isElementExist(elementId)) {
			return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
		} else if(!isElementInNode(nodeId, elementId)) {
			return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
		}
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]-(:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}) ");
		qb.append("WHERE HAS (r."+ELEMENT_ATTR_WORKER_ID+") ");
		qb.append("REMOVE r."+ELEMENT_ATTR_WORKER_ID+" ");
		qb.append("RETURN COUNT(r) AS count;");
		
		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(qb.toString(), properties);
		
		if(dataReturned.size()>0) {
			int data = (int) dataReturned.get(0).get("count");
			if(data>0) {
				return NOTIFICATION_WORKER_REMOVED_SUCCESSFULLY;
			}
		}
		return NOTIFICATION_FAILED_NO_WORKER_ASSIGNED_TO_ELEMENT;
	}
	
	/**
	 * Replace Worker to Element
	 * @param nodeId
	 * @param elementId
	 * @param workerId
	 * @return Status
	 */
	public String replaceWorkerOfElement(String nodeId, String elementId, String workerId) {
		if(!bpo.getElementFunctions().isElementExist(elementId)) {
			return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
		} else if(!isElementInNode(nodeId, elementId)) {
			return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
		}
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(ELEMENT_ATTR_WORKER_ID, workerId);
		
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]-(:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}) ");
		qb.append("SET r."+ELEMENT_ATTR_WORKER_ID+"={"+ELEMENT_ATTR_WORKER_ID+"} ");
		qb.append("RETURN COUNT(r) AS count;");
		
		List<Map<String, Object>> dataReturned = neo4j.sendCypherQuery(qb.toString(), properties);
		
		if(dataReturned.size()>0) {
			int data = (int) dataReturned.get(0).get("count");
			if(data>0) {
				return "Worker Replaced Successfully";
			} 
		}
		return "Failed, No Worker to Replace";
	}
	/**
	 * Complete Element no Next Node
	 * @param elementId
	 * @param nodeId
	 * @param workerId
	 * @param totalOuput
	 * @param totalError
	 * @return
	 */
	public String completeElement(String elementId, String nodeId, String workerId, int totalOuput, int totalError) {
		if(isElementExist(elementId)) {
			if(!isElementInNode(nodeId, elementId)) {
				return "Element is not in Node: " + nodeId;
			} 
		} else {
			return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
		}
		
		String status = getElementStatus(elementId);
		boolean dbQueryStat = false;
		if(status.equals(CONSTANT_STATUS_WAITING)) {
			return NOTIFICATION_ELEMENT_IS_NOT_IN_PROGRESS;
		} else if(status.equals(CONSTANT_STATUS_IN_PROCESS)) {
			if(totalOuput>0) {
				if(!bpo.getNodeFunctions().incrementNodeOutputCount(nodeId, totalOuput)) {
					return NOTIFICATION_FAILED_CHECK_CONNECTION;
				}
			}
			
			if(totalError>0) {
				if(!bpo.getNodeFunctions().incrementNodeErrorCount(nodeId, totalError)) {
					return NOTIFICATION_FAILED_CHECK_CONNECTION;
				}
			}
			 
			Map<String, Object> properties = new HashMap<>();
			properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
			properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
			properties.put(ELEMENT_ATTR_WORKER_ID, workerId);
			properties.put(ELEMENT_ATTR_TOTAL_OUPUT, totalOuput);
			properties.put(ELEMENT_ATTR_TOTAL_ERROR, totalError);
			properties.put(ELEMENT_ATTR_STATUS, CONSTANT_STATUS_COMPLETE);
			StringBuilder qb = new StringBuilder();

			qb.append("MATCH (firstNode:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[completedRel:"+RELATIONSHIP_LABEL_TASK_IN_PROCESS+"]-(element:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}}) "
					+ "MATCH firstNode<-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]-(firstNodeReport:"+NodeFunctions.NODE_LABEL_BPO_REPORT+") "
					+ "CREATE element-[completedTask:"+RELATIONSHIP_LABEL_COMPLETED_TASK+"]->firstNode "
					+ "SET element."+ELEMENT_ATTR_STATUS+" = {"+ELEMENT_ATTR_STATUS+"} " 
					+ "SET completedRel."+ELEMENT_ATTR_END_PROCESSING_TIME+"=TIMESTAMP() "
					+ "SET completedRel."+ELEMENT_ATTR_PROCESS_DURATION+"=completedRel."+ELEMENT_ATTR_END_PROCESSING_TIME+"-completedRel."+ELEMENT_ATTR_START_PROCESSING_TIME+" "
					+ "SET completedTask=completedRel "
					+ "SET element."+ELEMENT_ATTR_ESTIMATED_COMPLETION_DURATION+"=0 "
					+ "WITH completedRel, firstNodeReport " 
					+ "SET firstNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_DURATION+"=firstNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_DURATION+"+completedRel."+ELEMENT_ATTR_PROCESS_DURATION+" "
					+ "SET firstNodeReport."+NodeFunctions.REPORT_ATTR_AVERAGE_PROCESS_DURATION+"=(firstNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_DURATION+"/firstNodeReport."+NodeFunctions.REPORT_ATTR_TOTAL_PROCESS_ELEMENT+") "
					+ "SET firstNodeReport."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"=(firstNodeReport."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"-1) "
					+ "DELETE completedRel;");
			dbQueryStat = neo4j.sendCypherBooleanResult(qb.toString(), properties);
		}
		
		if(dbQueryStat) {
			return "COMPLETE SUCCESS";
		} else {
			return NOTIFICATION_FAILED_CHECK_CONNECTION;
		}
	}
	
	/**
	 * Update Element Attributes Sample usage:
	 * <pre>
	 * Map<Element, Object> prop = new HashMap<>();
	 * prop.put(ElementAttributes.EXTRA1, "Value");
	 * bpo.getElementFunctions().updateElementAttributes("Element1", prop);
	 * </pre>
	 * @param elementId
	 * @param editablePropertyMap
	 * @return
	 */
	public String updateElementAttributes(String elementId, Map<ElementAttributes, Object> editablePropertyMap) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> properties = new HashMap<>();
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		for(Entry<ElementAttributes, Object> each: editablePropertyMap.entrySet()) {
			String propertyName = each.getKey().toString();
			Object propertyValue = each.getValue();
			properties.put(propertyName, propertyValue);
		}
		sb.append("MATCH (element:"+NODE_LABEL_ELEMENT+") ");
		sb.append("WHERE element."+ELEMENT_ATTR_ELEMENT_ID+"={"+ELEMENT_ATTR_ELEMENT_ID+"} ");
		for(String eachProperty: properties.keySet()) {
			sb.append("SET element."+eachProperty+"={"+eachProperty+"} ");
		}
		sb.append("RETURN COUNT(element) AS count;");
		List<Map<String, Object>> data = neo4j.sendCypherQuery(sb.toString(), properties);
		if(data.size()>0) {
			int result = (int) data.get(0).get("count");
			if(result>0) {
				return "Element Update Success!";
			}
		}
		return NOTIFICATION_ELEMENT_DOES_NOT_EXIST;
	}
	
	/**
	 * Return Element to Node and Remove the Assigned Worker
	 * @param nodeId
	 * @param elementId
	 * @param workerId
	 * @return
	 */
	public String returnElementRemoveWorker(String nodeId, String elementId, String workerId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_WORKER_ID, workerId);
		properties.put(CONSTANT_STATUS_WAITING, CONSTANT_STATUS_WAITING);
		
		StringBuilder qb = new StringBuilder();
		
		qb.append("MATCH (element:"+NODE_LABEL_ELEMENT+")-[currentTask:"+RELATIONSHIP_LABEL_TASK_AT+"]->(node:"+NodeFunctions.NODE_LABEL_BPO_NODE+") ");
		qb.append("MATCH node<-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NodeFunctions.NODE_LABEL_BPO_REPORT+") ");
		qb.append("WHERE node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} ");
		qb.append("AND element."+ELEMENT_ATTR_ELEMENT_ID+"={"+ELEMENT_ATTR_ELEMENT_ID+"} ");
		qb.append("AND currentTask."+ELEMENT_ATTR_WORKER_ID+"={"+ELEMENT_ATTR_WORKER_ID+"} ");
		qb.append("SET currentTask."+ELEMENT_ATTR_END_PROCESSING_TIME+"=TIMESTAMP() ");
		qb.append("SET currentTask."+ELEMENT_ATTR_PROCESS_DURATION+"=currentTask."+ELEMENT_ATTR_END_PROCESSING_TIME+"-currentTask."+ELEMENT_ATTR_START_PROCESSING_TIME+" ");
		qb.append("CREATE element-[incompleteTask:"+RELATIONSHI_LABEL_BPO_INCOMPLETE_TASK_AT+"]->node ");
		qb.append("SET incompleteTask=currentTask ");
		qb.append("SET report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"+1 ");
		qb.append("SET report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"=report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"-1 ");
		qb.append("WITH currentTask, element ");
		qb.append("SET currentTask."+ELEMENT_ATTR_START_WAITING_TIME+"=TIMESTAMP() ");
		qb.append("SET currentTask."+ELEMENT_ATTR_START_PROCESSING_TIME+"='' ");
		qb.append("SET currentTask."+ELEMENT_ATTR_END_PROCESSING_TIME+"='' ");
		qb.append("REMOVE currentTask."+ELEMENT_ATTR_WORKER_ID+" ");
		qb.append("REMOVE currentTask."+ELEMENT_ATTR_WAITING_DURATION+" ");
		qb.append("REMOVE currentTask."+ELEMENT_ATTR_PROCESS_DURATION+" ");
		qb.append("SET element."+ELEMENT_ATTR_STATUS+"={"+CONSTANT_STATUS_WAITING+"} ");
		qb.append("RETURN COUNT(currentTask) AS count;");
		int result = (int) neo4j.sendCypherQuery(qb.toString(), properties).get(0).get("count");
		if(result>0) {
			return "Element Returned Successfully";
		}
		
		return "Failed!, Either Node, Element or Worker Does Not Exist";
	}
	
	/**
	 * Return Element and Retain the Current Worker
	 * @param nodeId
	 * @param elementId
	 * @param workerId
	 * @return
	 */
	public String returnElementRetainWorker(String nodeId, String elementId, String workerId) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		properties.put(ELEMENT_ATTR_WORKER_ID, workerId);
		properties.put(CONSTANT_STATUS_WAITING, CONSTANT_STATUS_WAITING);
		
		StringBuilder qb = new StringBuilder();
		
		qb.append("MATCH (element:"+NODE_LABEL_ELEMENT+")-[currentTask:"+RELATIONSHIP_LABEL_TASK_AT+"]->(node:"+NodeFunctions.NODE_LABEL_BPO_NODE+") ");
		qb.append("MATCH node<-[:"+NodeFunctions.RELATIONSHIP_LABEL_REPORTING_OF+"]-(report:"+NodeFunctions.NODE_LABEL_BPO_REPORT+") ");
		qb.append("WHERE node."+NodeFunctions.NODE_ATTR_NODE_ID+"={"+NodeFunctions.NODE_ATTR_NODE_ID+"} ");
		qb.append("AND element."+ELEMENT_ATTR_ELEMENT_ID+"={"+ELEMENT_ATTR_ELEMENT_ID+"} ");
		qb.append("AND currentTask."+ELEMENT_ATTR_WORKER_ID+"={"+ELEMENT_ATTR_WORKER_ID+"} ");
		qb.append("SET currentTask."+ELEMENT_ATTR_END_PROCESSING_TIME+"=TIMESTAMP() ");
		qb.append("SET currentTask."+ELEMENT_ATTR_PROCESS_DURATION+"=currentTask."+ELEMENT_ATTR_END_PROCESSING_TIME+"-currentTask."+ELEMENT_ATTR_START_PROCESSING_TIME+" ");
		qb.append("CREATE element-[incompleteTask:"+RELATIONSHI_LABEL_BPO_INCOMPLETE_TASK_AT+"]->node ");
		qb.append("SET incompleteTask=currentTask ");
		qb.append("SET report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"=report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_WAITING_ELEMENTS+"+1 ");
		qb.append("SET report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"=report."+NodeFunctions.REPORT_ATTR_CURRENT_TOTAL_IN_PROCESS_ELEMENTS+"-1 ");
		qb.append("WITH currentTask, element ");
		qb.append("SET currentTask."+ELEMENT_ATTR_START_WAITING_TIME+"=TIMESTAMP() ");
		qb.append("SET currentTask."+ELEMENT_ATTR_START_PROCESSING_TIME+"='' ");
		qb.append("SET currentTask."+ELEMENT_ATTR_END_PROCESSING_TIME+"='' ");
		qb.append("REMOVE currentTask."+ELEMENT_ATTR_WAITING_DURATION+" ");
		qb.append("REMOVE currentTask."+ELEMENT_ATTR_PROCESS_DURATION+" ");
		qb.append("SET element."+ELEMENT_ATTR_STATUS+"={"+CONSTANT_STATUS_WAITING+"};");
		
		int result = (int) neo4j.sendCypherQuery(qb.toString(), properties).get(0).get("count");
		if(result>0) {
			return "Element Returned Successfully";
		}
		
		return "Failed!, Either Node, Element or Worker Does Not Exist";
	}
	
	
	/******************
	 * DELETE FUNCTIONS *
	 ******************/
	
	/**
	 * Delete Element
	 * @param elementId
	 * @param nodeId
	 * @return
	 */
	public String deleteElement(String elementId, String nodeId) {
		if(!isElementExist(elementId)) {
			return NOTIFICATION_ELEMENT_DOES_NOT_EXIST; 
		} else if(!bpo.getNodeFunctions().isNodeExist(nodeId)) {
			return NOTIFICATION_NODE_ID_DOES_NOT_EXIST;
		} else if(!isElementInNode(nodeId, elementId)) {
			return "Element Not in Node: " + nodeId;
		}
		
		Map<String, Object> properties = new HashMap<>();
		properties.put(ELEMENT_ATTR_ELEMENT_ID, elementId);
		properties.put(NodeFunctions.NODE_ATTR_NODE_ID, nodeId);
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (:"+NodeFunctions.NODE_LABEL_BPO_NODE+" {"+NodeFunctions.NODE_ATTR_NODE_ID+":{"+NodeFunctions.NODE_ATTR_NODE_ID+"}})<-[r:"+RELATIONSHIP_LABEL_TASK_AT+"]-(element:"+NODE_LABEL_ELEMENT+" {"+ELEMENT_ATTR_ELEMENT_ID+":{"+ELEMENT_ATTR_ELEMENT_ID+"}})-[:"+RELATIONSHIP_LABEL_COMPLETED_TASK+"]->() DELETE r;");
		boolean dbQueryStat = neo4j.sendCypherBooleanResult(qb.toString(), properties);
		if(dbQueryStat) {
			return NOTIFICATION_ELEMENT_DELETED_SUCCESSFULLY;
		} else {
			return NOTIFICATION_FAILED_CHECK_CONNECTION;
		}
	}
	
	
	
	/*********************
	 * CHECKER FUNCTIONS *
	 *********************/
	private boolean checkIfValidPriority(int priority) {
		if(priority<0 && priority>9) {
			return false; 
		} 
		return true;
	}
}