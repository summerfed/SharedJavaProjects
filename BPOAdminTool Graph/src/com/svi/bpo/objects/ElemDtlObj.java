package com.svi.bpo.objects;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ElemDtlObj implements IsSerializable {
/*
	private String elemId;
	private String currNode;
	private String workerId;
	private String priority;
	private String status;

	private String gfsKey;
	private String waitStartTime;
	private String processStartTime;
	private String processEndTime;
	private String waitDuration;
	private String processDuration;
	private String outputCount;
	private String errorCount;

	private String extra1;
	private String extra2;
	private String extra3;
*/
	private String nodeId;
	private String elementId;
	private int priority;
	private String status;
	private String extra1;
	private String extra2;
	private String extra3;
	private String filePointer;
	private long targetCompletionDuration;
	private long estimatedCompletionDuration;
	
	/*Not in the ElementObj*/
	
	private String workerId;
	private int waitStartTime ;
	private int processStartTime;
	private int processEndTime;
	private int waitDuration ;
	private int processDuration;
	private int outputCount ;
	private int errorCount ;
	
	public ElemDtlObj() {

	}
	
	
	public ElemDtlObj(String nodeId, String elementId, int priority, String status,
			String extra1, String extra2, String extra3, String filePointer, long targetCompletionDuration,
			long estimatedCompletionDuration, String workerId){// worker id added by jm
		
		this.nodeId = nodeId;
		this.elementId = elementId;
		this.priority = priority;
		this.status = status;
		this.extra1 = extra1;
		this.extra2 = extra2;
		this.extra3 = extra3;
		this.targetCompletionDuration = targetCompletionDuration;
		this.estimatedCompletionDuration = estimatedCompletionDuration;
		this.workerId = workerId;//added by jm
	}

	// public ElemDtlObj(String elemId, String status, String priority,
	// String waitStartTime, String processStartTime) {
	// this.elemId = elemId;
	// this.status = status;
	// this.priority = priority;
	// this.waitStartTime = waitStartTime;
	// this.processStartTime = processStartTime;
	// }

	// public ElemDtlObj(String elemId, String priority, String currNode, String
	// workerId) {
	// this.elemId = elemId;
	// this.priority = priority;
	// this.currNode = currNode;
	// this.workerId = workerId;
	// }

	// public ElemDtlObj(String elemId, String priority, String workerId, String
	// gfsKey,
	// String extra1, String extra2, String extra3) {
	// this.elemId = elemId;
	// this.priority = priority;
	// this.workerId = workerId;
	// this.gfsKey = gfsKey;
	// this.extra1 = extra1;
	// this.extra2 = extra2;
	// this.extra3 = extra3;
	// }
	//
	// public ElemDtlObj(String elemId, String status, String priority,
	// String waitStartTime, String processStartTime, String workerId,
	// String gfsKey, String extra1, String extra2, String extra3,
	// String currNode) {
	// this.elemId = elemId;
	// this.status = status;
	// this.priority = priority;
	// this.waitStartTime = waitStartTime;
	// this.processStartTime = processStartTime;
	// this.workerId = workerId;
	// this.gfsKey = gfsKey;
	// this.extra1 = extra1;
	// this.extra2 = extra2;
	// this.extra3 = extra3;
	// this.currNode = currNode;
	// }
	
	public ElemDtlObj(String elementId, String nodeId, String workerId,
			int priority) {

		this.elementId = elementId;
		this.nodeId = nodeId;
		this.workerId = workerId;
		this.priority = priority;
	}

	public ElemDtlObj(String elementId, String nodeId, String filePointer,
			int priority, String extra1, String extra2,String extra3) {

		this.elementId = elementId;
		this.nodeId = nodeId;
		this.priority = priority;
		this.filePointer = filePointer;
		this.extra1 = extra1;
		this.extra2 = extra2;
		this.extra3 = extra3;
		
		
	}
	public ElemDtlObj(String elementId, String nodeId, String workerId,
			int priority, String status, 
			int waitStartTime, int processStartTime,
			int processEndTime, int waitDuration, int processDuration,
			int outputCount, int errorCount, String extra1,
			String extra2, String extra3) {

		this.elementId = elementId;
		this.nodeId = nodeId;
		this.workerId = workerId;
		this.priority = priority;
		this.status = status;
	//	this.gfsKey = gfsKey;
		this.waitStartTime = waitStartTime;
		this.processStartTime = processStartTime;
		this.processEndTime = processEndTime;
		this.waitDuration = waitDuration;
		this.processDuration = processDuration;
		this.outputCount = outputCount;
		this.errorCount = errorCount;
		this.extra1 = extra1;
		this.extra2 = extra2;
		this.extra3 = extra3;
	}

	

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExtra1() {
		return extra1;
	}

	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	public String getExtra2() {
		return extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}

	public String getExtra3() {
		return extra3;
	}

	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}

	public String getFilePointer() {
		return filePointer;
	}

	public void setFilePointer(String filePointer) {
		this.filePointer = filePointer;
	}

	public long getTargetCompletionDuration() {
		return targetCompletionDuration;
	}

	public void setTargetCompletionDuration(long targetCompletionDuration) {
		this.targetCompletionDuration = targetCompletionDuration;
	}

	public long getEstimatedCompletionDuration() {
		return estimatedCompletionDuration;
	}

	public void setEstimatedCompletionDuration(long estimatedCompletionDuration) {
		this.estimatedCompletionDuration = estimatedCompletionDuration;
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public int getWaitStartTime() {
		return waitStartTime;
	}

	public void setWaitStartTime(int waitStartTime) {
		this.waitStartTime = waitStartTime;
	}

	public int getProcessStartTime() {
		return processStartTime;
	}

	public void setProcessStartTime(int processStartTime) {
		this.processStartTime = processStartTime;
	}

	public int getProcessEndTime() {
		return processEndTime;
	}

	public void setProcessEndTime(int processEndTime) {
		this.processEndTime = processEndTime;
	}

	public int getWaitDuration() {
		return waitDuration;
	}

	public void setWaitDuration(int waitDuration) {
		this.waitDuration = waitDuration;
	}

	public int getProcessDuration() {
		return processDuration;
	}

	public void setProcessDuration(int processDuration) {
		this.processDuration = processDuration;
	}

	public int getOutputCount() {
		return outputCount;
	}

	public void setOutputCount(int outputCount) {
		this.outputCount = outputCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	@Override
	public String toString() {
		return "ElemDtlObj [elemId=" + elementId + ", nodeId=" + nodeId
				+ ", workerId=" + workerId + ", priority=" + priority
				+ ", status=" + status 
				+ ", waitStartTime=" + waitStartTime + ", processStartTime="
				+ processStartTime + ", processEndTime=" + processEndTime
				+ ", waitDuration=" + waitDuration + ", processDuration="
				+ processDuration + ", outputCount=" + outputCount
				+ ", errorCount=" + errorCount + ", extra1=" + extra1
				+ ", extra2=" + extra2 + ", extra3=" + extra3 + "]";
	}

}
