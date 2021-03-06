package com.svi.bpo.graph.obj;

public class ElementObject {
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
	private String workerId;//added by jm
	private String normalFlowLocation = "N/A";
	private int processingState;
	private String processingStateDescription;
	private String arrivalTime;
	private String completionTime;
	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}
	/**
	 * @return the elementId
	 */
	public String getElementId() {
		return elementId;
	}
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @return the extra1
	 */
	public String getExtra1() {
		return extra1;
	}
	/**
	 * @return the extra2
	 */
	public String getExtra2() {
		return extra2;
	}
	/**
	 * @return the extra3
	 */
	public String getExtra3() {
		return extra3;
	}
	/**
	 * @return the filePointer
	 */
	public String getFilePointer() {
		return filePointer;
	}
	/**
	 * @return the targetCompletionDuration
	 */
	public long getTargetCompletionDuration() {
		return targetCompletionDuration;
	}
	/**
	 * @return the estimatedCompletionDuration
	 */
	public long getEstimatedCompletionDuration() {
		return estimatedCompletionDuration;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @param elementId the elementId to set
	 */
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param extra1 the extra1 to set
	 */
	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}
	/**
	 * @param extra2 the extra2 to set
	 */
	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}
	/**
	 * @param extra3 the extra3 to set
	 */
	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}
	/**
	 * @param filePointer the filePointer to set
	 */
	public void setFilePointer(String filePointer) {
		this.filePointer = filePointer;
	}
	/**
	 * @param targetCompletionDuration the targetCompletionDuration to set
	 */
	public void setTargetCompletionDuration(long targetCompletionDuration) {
		this.targetCompletionDuration = targetCompletionDuration;
	}
	/**
	 * @param estimatedCompletionDuration the estimatedCompletionDuration to set
	 */
	public void setEstimatedCompletionDuration(long estimatedCompletionDuration) {
		this.estimatedCompletionDuration = estimatedCompletionDuration;
	}
	
	//added by jm
	public void setWorkerId(String workerId){
		this.workerId = workerId;
	}
	public String getWorkerId(){
		return this.workerId ;
	}
	
	public String getNormalFlowLocation() {
		return normalFlowLocation;
	}
	public void setNormalFlowLocation(String normalFlowLocation) {
		this.normalFlowLocation = normalFlowLocation;
	}
	public int getProcessingState() {
		return processingState;
	}
	public void setProcessingState(int processingState) {
		this.processingState = processingState;
	}
	public String getProcessingDescription() {
		return processingStateDescription;
	}
	public void setProcessingStateDescription(String processingDescription) {
		this.processingStateDescription = processingDescription;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getCompletionTime() {
		return completionTime;
	}
	public void setCompletionTime(String completionTime) {
		this.completionTime = completionTime;
	}
}
