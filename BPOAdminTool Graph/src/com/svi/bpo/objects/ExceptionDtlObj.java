package com.svi.bpo.objects;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ExceptionDtlObj implements IsSerializable {

	private String nodeId;
	private String exceptionName;
	private String exceptionCode;
	private long allowedWaitingDuration;
	private long allowedProcessDuration;
	private long currentTotalWaitingElements;
	private long currentTotalInProcessElements;
	
	public ExceptionDtlObj(String nodeId,String exceptionName, String exceptionCode,
			long allowedWaitingDuration, long allowedProcessDuration,
			long currentTotalWaitingElements, long currentTotalInProcessElements) {
		this.nodeId = nodeId;
		this.exceptionName = exceptionName;
		this.exceptionCode = exceptionCode;
		this.allowedWaitingDuration = allowedWaitingDuration;
		this.allowedProcessDuration = allowedProcessDuration;
		this.currentTotalWaitingElements = currentTotalWaitingElements;
		this.currentTotalInProcessElements = currentTotalInProcessElements;
	}
	public ExceptionDtlObj() {
		// TODO Auto-generated constructor stub
	}
	
	public String getExceptionName() {
		return exceptionName;
	}
	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	public long getAllowedWaitingDuration() {
		return allowedWaitingDuration;
	}
	public void setAllowedWaitingDuration(long allowedWaitingDuration) {
		this.allowedWaitingDuration = allowedWaitingDuration;
	}
	public long getAllowedProcessDuration() {
		return allowedProcessDuration;
	}
	public void setAllowedProcessDuration(long allowedProcessDuration) {
		this.allowedProcessDuration = allowedProcessDuration;
	}
	public long getCurrentTotalWaitingElements() {
		return currentTotalWaitingElements;
	}
	public void setCurrentTotalWaitingElements(long currentTotalWaitingElements) {
		this.currentTotalWaitingElements = currentTotalWaitingElements;
	}
	public long getCurrentTotalInProcessElements() {
		return currentTotalInProcessElements;
	}
	public void setCurrentTotalInProcessElements(long currentTotalInProcessElements) {
		this.currentTotalInProcessElements = currentTotalInProcessElements;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
}
