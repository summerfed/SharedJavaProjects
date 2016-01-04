package com.svi.bpo.tool.dto;

public class ElementDto {
	private String elementID;
	private String nodeName;
	private String workerID;
	private int priority;// change to int by jm
	private String gfsKey;
	private String status;
	private String waitStart;
	private String procStart;
	private String procEnd;
	private String waitDuration="0";
	private String procDuration="0";
	private String outputCount="0";
	private String errorCount="0";
	private String extra1;
	private String extra2;
	private String extra3;
	
	public String getElementID() {
		return elementID;
	}
	public void setElementID(String elementID) {
		this.elementID = elementID;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getWorkerID() {
		return workerID;
	}
	public void setWorkerID(String workerID) {
		this.workerID = workerID;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getGfsKey() {
		return gfsKey;
	}
	public void setGfsKey(String gfsKey) {
		this.gfsKey = gfsKey;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWaitStart() {
		return waitStart;
	}
	public void setWaitStart(String waitStart) {
		this.waitStart = waitStart;
	}
	public String getProcStart() {
		return procStart;
	}
	public void setProcStart(String procStart) {
		this.procStart = procStart;
	}
	public String getProcEnd() {
		return procEnd;
	}
	public void setProcEnd(String procEnd) {
		this.procEnd = procEnd;
	}
	public String getWaitDuration() {
		return waitDuration;
	}
	public void setWaitDuration(String waitDuration) {
		this.waitDuration = waitDuration;
	}
	public String getProcDuration() {
		return procDuration;
	}
	public void setProcDuration(String procDuration) {
		this.procDuration = procDuration;
	}
	public String getOutputCount() {
		return outputCount;
	}
	public void setOutputCount(String outputCount) {
		this.outputCount = outputCount;
	}
	public String getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(String errorCount) {
		this.errorCount = errorCount;
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

}
