package com.svi.bpo.tool.dto;

public class NodeDto {
	
	private String nodeId;
	private String nodeName;
	private String cluster;
	private String stdUnitOfMeasure="";
	private double cost;
	private int allowedWaitingTime;
	private int allowedProcessTime;
	private int targetOutputCount;
	private int allowedErrorCount;
	
	// Can delete
	private String nodeExceptionOf;
	private String nextNode;
	private String errorNode;
	private String status;
	private String operationHrs;
	private String altNode;
//	private String cost="0";
	private String allowedMinWait="0";
	private String allowedMaxWait="0";
	private String allowedAveWait="0";
	private String allowedMinProc="0";
	private String allowedMaxProc="0";
	private String allowedAveProc="0";
	private String targetOutput="0";
	private String allowedError="0";
	private String cumElemCount="0";
	private String cumElemWithErrCount="0";
	private String waitMinActual="0";
	private String waitMaxActual="0";
	private String waitAveActual="0";
	private String procMinActual="0";
	private String procMaxActual="0";
	private String procAveActual="0";
	private String cumOutput="0";
	private String cumError="0";
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperationHrs() {
		return operationHrs;
	}

	public void setOperationHrs(String operationHrs) {
		this.operationHrs = operationHrs;
	}

	public String getAltNode() {
		return altNode;
	}

	public void setAltNode(String altNode) {
		this.altNode = altNode;
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

	public String getTargetOutput() {
		return targetOutput;
	}

	public void setTargetOutput(String targetOutput) {
		this.targetOutput = targetOutput;
	}

	public String getAllowedError() {
		return allowedError;
	}

	public void setAllowedError(String allowedError) {
		this.allowedError = allowedError;
	}

	public String getCumElemCount() {
		return cumElemCount;
	}

	public void setCumElemCount(String cumElemCount) {
		this.cumElemCount = cumElemCount;
	}

	public String getCumElemWithErrCount() {
		return cumElemWithErrCount;
	}

	public void setCumElemWithErrCount(String cumElemWithErrCount) {
		this.cumElemWithErrCount = cumElemWithErrCount;
	}

	public String getWaitMinActual() {
		return waitMinActual;
	}

	public void setWaitMinActual(String waitMinActual) {
		this.waitMinActual = waitMinActual;
	}

	public String getWaitMaxActual() {
		return waitMaxActual;
	}

	public void setWaitMaxActual(String waitMaxActual) {
		this.waitMaxActual = waitMaxActual;
	}

	public String getWaitAveActual() {
		return waitAveActual;
	}

	public void setWaitAveActual(String waitAveActual) {
		this.waitAveActual = waitAveActual;
	}

	public String getProcMinActual() {
		return procMinActual;
	}

	public void setProcMinActual(String procMinActual) {
		this.procMinActual = procMinActual;
	}

	public String getProcMaxActual() {
		return procMaxActual;
	}

	public void setProcMaxActual(String procMaxActual) {
		this.procMaxActual = procMaxActual;
	}

	public String getProcAveActual() {
		return procAveActual;
	}

	public void setProcAveActual(String procAveActual) {
		this.procAveActual = procAveActual;
	}

	public String getCumOutput() {
		return cumOutput;
	}

	public void setCumOutput(String cumOutput) {
		this.cumOutput = cumOutput;
	}

	public String getCumError() {
		return cumError;
	}

	public void setCumError(String cumError) {
		this.cumError = cumError;
	}

	@Override
	public String toString() {
		return "NodeDto [nodeId=" + nodeId + ", nodeName=" + nodeName + "]";
	}

	public int getAllowedWaitingTime() {
		return allowedWaitingTime;
	}

	public void setAllowedWaitingTime(int allowedWaitingTime) {
		this.allowedWaitingTime = allowedWaitingTime;
	}

	public int getAllowedProcessTime() {
		return allowedProcessTime;
	}

	public void setAllowedProcessTime(int allowedProcessTime) {
		this.allowedProcessTime = allowedProcessTime;
	}

	public int getTargetOutputCount() {
		return targetOutputCount;
	}

	public void setTargetOutputCount(int targetOutputCount) {
		this.targetOutputCount = targetOutputCount;
	}

	public int getAllowedErrorCount() {
		return allowedErrorCount;
	}

	public void setAllowedErrorCount(int allowedErrorCount) {
		this.allowedErrorCount = allowedErrorCount;
	}
	
	
}
