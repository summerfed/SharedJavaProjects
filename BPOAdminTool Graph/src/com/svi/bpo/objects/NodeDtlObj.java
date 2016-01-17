package com.svi.bpo.objects;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NodeDtlObj implements IsSerializable{

	private String nodeId;
	private String nodeName;
	private String cluster;
	private String stdUnitOfMeasure="";
	private String status;
	private double cost;
	private long allowedWaitingTime;
	private long allowedProcessingTime;
	private int targetOutputCount;
	private int AllowedErrorCount;
	private boolean canMeetTarget;
	
	private String allowedMinWait;
	private String allowedMaxWait;
	private String allowedAveWait;
	private String allowedMinProc;
	private String allowedMaxProc;
	private String allowedAveProc;
	private int targetOutput;
	private String allowedError;
	
	private String nodeExceptionOf;
	private String nextNode;
	private String errorNode;
	private String operationHours;
	private String altNode;
	private String cumuElemCount="0";
	private String cumuElemWithErrorCount="0";
	private String cumuOutput="0";
	private String cumuError="0";
	
	private String endpointId;
	private int elmtsWaiting = 0;
	private int elmtsInprogress = 0;
	private int currentElem = 0;
	private String elmtsCompleted = "0";
	
	private List<ElemDtlObj> elementList;
	/*
	 * NODE OBJECT ATTRIBUTES:
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
	protected static final String NODE_ATTR_STD_UNIT_MEASURE = "stdUnitOfMeasure";// need
	protected static final String NODE_ATTR_CLUSTER = "cluster";// need
	protected static final String NODE_ATTR_NODE_NAME = "nodeName";//need
	protected static final String NODE_ATTR_NODE_ID = "nodeId";// need
	protected static final String NODE_ATTR_COST = "cost";
	
	protected static final String CLUSTER_ATTR_ID = "clusterId";
	protected static final String CLUSTER_ATTR_TARGET_COMPLETION_TIME = "targetCompletionTime";
	protected static final String CLUSTER_ATTR_ESTIMATED_COMPLETION_TIME = "targetEstimatedTime";

	 * */
	public NodeDtlObj(){
		
	}
	
	
	
	public NodeDtlObj(String nodeId, String nodeName, String endpointId) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.endpointId = endpointId;
	}
	
	public NodeDtlObj(String nodeId, String nodeName, String endpointId,
			int elmtsWaiting, int elmtsInprogress, String cluster,
			double cost, String status, String stdUnitOfMeasure,
			String allowedMinWait, String allowedMaxWait,
			String allowedAveWait, String allowedMinProc,
			String allowedMaxProc, String allowedAveProc, int targetOutput,
			String allowedError, String errorNode, String nodeExceptionOf) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.endpointId = endpointId;
		this.elmtsWaiting = elmtsWaiting;
		this.elmtsInprogress = elmtsInprogress;
		this.cluster = cluster;
		this.cost = cost;
		this.status = status;
		this.stdUnitOfMeasure = stdUnitOfMeasure;
		this.allowedMinWait = allowedMinWait;
		this.allowedMaxWait = allowedMaxWait;
		this.allowedAveWait = allowedAveWait;
		this.allowedMinProc = allowedMinProc;
		this.allowedMaxProc = allowedMaxProc;
		this.allowedAveProc = allowedAveProc;
		this.targetOutput = targetOutput;
		this.allowedError = allowedError;
		this.errorNode = errorNode;
		this.nodeExceptionOf = nodeExceptionOf;
	}
	
	/*
	 * private String nodeId;
	private String nodeName;
	private String cluster;
	private String stdUnitOfMeasure="";
	private String status;
	private double cost;
	private int allowedWaitingTime;
	private int allowedProcessingTime;
	private int targetOutputCount;
	private int AllowedErrorCount;
	 * 
	 * */
	public NodeDtlObj(String nodeId,String nodeName,String cluster,String stdUnitOfMeasure,
			double cost, int allowedWaitingTime, int allowedProcessingTime,int targetOutputCount, int AllowedErrorCount){
		
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.cluster = cluster; 
		this.stdUnitOfMeasure = stdUnitOfMeasure;
	
		this.cost = cost;
		this.allowedWaitingTime = allowedWaitingTime;
		this.allowedProcessingTime = allowedProcessingTime;
		this.targetOutputCount = targetOutputCount;
		this.AllowedErrorCount = AllowedErrorCount;
	}
	
//	public NodeDtlObj(String nodeId, String nodeName, String endpointId,
//			String elmtsWaiting, String elmtsInprogress) {
//		this.nodeId = nodeId;
//		this.nodeName = nodeName;
//		this.endpointId = endpointId;
//		this.elmtsWaiting = elmtsWaiting;
//		this.elmtsInprogress = elmtsInprogress;
//	}

	



	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getStdUnitOfMeasure() {
		return stdUnitOfMeasure;
	}

	public void setStdUnitOfMeasure(String stdUnitOfMeasure) {
		this.stdUnitOfMeasure = stdUnitOfMeasure;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getAllowedMinWait() {
		return allowedMinWait;
	}

	public void setAllowedMinWait(String allowedMinWait) {
		this.allowedMinWait = allowedMinWait;
	}

	public String getAllowedMaxWait() {
		return allowedMaxWait;
	}

	public void setAllowedMaxWait(String allowedMaxWait) {
		this.allowedMaxWait = allowedMaxWait;
	}

	public String getAllowedAveWait() {
		return allowedAveWait;
	}

	public void setAllowedAveWait(String allowedAveWait) {
		this.allowedAveWait = allowedAveWait;
	}

	public String getAllowedMinProc() {
		return allowedMinProc;
	}

	public void setAllowedMinProc(String allowedMinProc) {
		this.allowedMinProc = allowedMinProc;
	}

	public String getAllowedMaxProc() {
		return allowedMaxProc;
	}

	public void setAllowedMaxProc(String allowedMaxProc) {
		this.allowedMaxProc = allowedMaxProc;
	}

	public String getAllowedAveProc() {
		return allowedAveProc;
	}

	public void setAllowedAveProc(String allowedAveProc) {
		this.allowedAveProc = allowedAveProc;
	}

	public int getTargetOutput() {
		return targetOutput;
	}

	public void setTargetOutput(int targetOutput) {
		this.targetOutput = targetOutput;
	}

	public String getAllowedError() {
		return allowedError;
	}

	public void setAllowedError(String allowedError) {
		this.allowedError = allowedError;
	}

	public String getNodeExceptionOf() {
		return nodeExceptionOf;
	}

	public void setNodeExceptionOf(String nodeExceptionOf) {
		this.nodeExceptionOf = nodeExceptionOf;
	}

	public String getNextNode() {
		return nextNode;
	}

	public void setNextNode(String nextNode) {
		this.nextNode = nextNode;
	}

	public String getErrorNode() {
		return errorNode;
	}

	public void setErrorNode(String errorNode) {
		this.errorNode = errorNode;
	}

	public String getOperationHours() {
		return operationHours;
	}

	public void setOperationHours(String operationHours) {
		this.operationHours = operationHours;
	}

	public String getAltNode() {
		return altNode;
	}

	public void setAltNode(String altNode) {
		this.altNode = altNode;
	}

	public String getCumElemCount() {
		return cumuElemCount;
	}

	public void setCumElemCount(String cumElemCount) {
		this.cumuElemCount = cumElemCount;
	}

	public String getCumElemWithErrorCount() {
		return cumuElemWithErrorCount;
	}

	public void setCumElemWithErrorCount(String cumElemWithErrorCount) {
		this.cumuElemWithErrorCount = cumElemWithErrorCount;
	}

	public String getCumOutput() {
		return cumuOutput;
	}

	public void setCumOutput(String cumOutput) {
		this.cumuOutput = cumOutput;
	}

	public String getCumError() {
		return cumuError;
	}

	public void setCumError(String cumError) {
		this.cumuError = cumError;
	}

	public String getEndpointId() {
		return endpointId;
	}

	public void setEndpointId(String endpointId) {
		this.endpointId = endpointId;
	}

	public int getElmtsWaiting() {
		return elmtsWaiting;
	}

	public void setElmtsWaiting(int elmtsWaiting) {
		this.elmtsWaiting = elmtsWaiting;
	}

	public int getElmtsInprogress() {
		return elmtsInprogress;
	}

	public void setElmtsInprogress(int elmtsInprogress) {
		this.elmtsInprogress = elmtsInprogress;
	}

	public String getElmtsCompleted() {
		return elmtsCompleted;
	}

	public void setElmtsCompleted(String elmtsCompleted) {
		this.elmtsCompleted = elmtsCompleted;
	}

	public List<ElemDtlObj> getElementList() {
		return elementList;
	}

	public void setElementList(List<ElemDtlObj> elementList) {
		this.elementList = elementList;
	}

	@Override
	public String toString() {
		return "NodeDtlObj [nodeId=" + nodeId + ", nodeName=" + nodeName
				+ ", endpointId=" + endpointId + ", elmtsWaiting="
				+ elmtsWaiting + ", elmtsInprogress=" + elmtsInprogress + "]";
	}
	
	public String checkElementList() {
		
		if ((elementList != null) && (elementList.size() > 0)) {
			for (ElemDtlObj elem : elementList) {
				System.out.println(elem.toString());
			}
		}
		
		return "Element List is not set";
	}

	public long getAllowedWaitingTime() {
		return allowedWaitingTime;
	}

	public void setAllowedWaitingTime(long allowedWaitingTime) {
		this.allowedWaitingTime = allowedWaitingTime;
	}

	public int getTargetOutputCount() {
		return targetOutputCount;
	}

	public void setTargetOutputCount(int targetOutputCount) {
		this.targetOutputCount = targetOutputCount;
	}

	public long getAllowedProcessingTime() {
		return allowedProcessingTime;
	}

	public void setAllowedProcessingTime(long allowedProcessingTime) {
		this.allowedProcessingTime = allowedProcessingTime;
	}

	public int getAllowedErrorCount() {
		return AllowedErrorCount;
	}

	public void setAllowedErrorCount(int allowedErrorCount) {
		AllowedErrorCount = allowedErrorCount;
	}



	public boolean isCanMeetTarget() {
		return canMeetTarget;
	}



	public void setCanMeetTarget(boolean canMeetTarget) {
		this.canMeetTarget = canMeetTarget;
	}



	public int getCurrentElem() {
		return currentElem;
	}



	public void setCurrentElem(int currentElem) {
		this.currentElem = currentElem;
	}
	
}
