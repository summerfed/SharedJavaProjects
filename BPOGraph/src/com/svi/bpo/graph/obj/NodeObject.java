package com.svi.bpo.graph.obj;

public class NodeObject {
	private String clusterId;
	private String nodeId;// added by jm
	private String nodeName;// added by jm
	private String stdUnitOfMeasure;
	private int targetOutput;
	private int allowedError;
	private double cost;
	private long allowedWaitingDuration; // added by jm
	private long allowedProcessingDuration;// added by jm
	private String assignedWorker;
	//If Node is Meeting the Deadline
	private boolean canMeetTarget;

	//Reporting Node Properties
	private int totalOutputCount;
	private int totalErrorCount;
	private long totalProcessDuration;
	private int totalProcessedElement;
	private long totalWaitingDuration;
	private long averageWaitingDuration;
	private long averageProcessDuration;
	private int totalWaitingElement;
	private int currentTotalWaitingElements;
	private int currentTotalInProcessElements;
	/**
	 * @return the clusterId
	 */
	public String getClusterId() {
		return clusterId;
	}
	/**
	 * @param clusterId the clusterId to set
	 */
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}
	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/**
	 * @return the stdUnitOfMeasure
	 */
	public String getStdUnitOfMeasure() {
		return stdUnitOfMeasure;
	}
	/**
	 * @param stdUnitOfMeasure the stdUnitOfMeasure to set
	 */
	public void setStdUnitOfMeasure(String stdUnitOfMeasure) {
		this.stdUnitOfMeasure = stdUnitOfMeasure;
	}
	/**
	 * @return the targetOutput
	 */
	public int getTargetOutput() {
		return targetOutput;
	}
	/**
	 * @param targetOutput the targetOutput to set
	 */
	public void setTargetOutput(int targetOutput) {
		this.targetOutput = targetOutput;
	}
	/**
	 * @return the allowedError
	 */
	public int getAllowedError() {
		return allowedError;
	}
	/**
	 * @param allowedError the allowedError to set
	 */
	public void setAllowedError(int allowedError) {
		this.allowedError = allowedError;
	}
	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	/**
	 * @return the allowedWaitingTime
	 */
	public long getAllowedWaitingTime() {
		return allowedWaitingDuration;
	}
	/**
	 * @param allowedWaitingTime the allowedWaitingTime to set
	 */
	public void setAllowedWaitingDuration(long allowedWaitingTime) {
		this.allowedWaitingDuration = allowedWaitingTime;
	}
	/**
	 * @return the allowedProcessingTime
	 */
	public long getAllowedProcessingTime() {
		return allowedProcessingDuration;
	}
	/**
	 * @param allowedProcessingTime the allowedProcessingTime to set
	 */
	public void setAllowedProcessingDuration(long allowedProcessingTime) {
		this.allowedProcessingDuration = allowedProcessingTime;
	}
	
	public boolean getCanMeetTarget() {
		return canMeetTarget;
	}
	
	
	public void setCanMeetTarget(boolean status) {
		this.canMeetTarget = status;
	}
	/**
	 * @return the totalOutputCount
	 */
	public int getTotalOutputCount() {
		return totalOutputCount;
	}
	/**
	 * @param totalOutputCount the totalOutputCount to set
	 */
	public void setTotalOutputCount(int totalOutputCount) {
		this.totalOutputCount = totalOutputCount;
	}
	/**
	 * @return the totalErrorCount
	 */
	public int getTotalErrorCount() {
		return totalErrorCount;
	}
	/**
	 * @param totalErrorCount the totalErrorCount to set
	 */
	public void setTotalErrorCount(int totalErrorCount) {
		this.totalErrorCount = totalErrorCount;
	}
	/**
	 * @return the totalProcessDuration
	 */
	public long getTotalProcessDuration() {
		return totalProcessDuration;
	}
	/**
	 * @param totalProcessDuration the totalProcessDuration to set
	 */
	public void setTotalProcessDuration(long totalProcessDuration) {
		this.totalProcessDuration = totalProcessDuration;
	}
	/**
	 * @return the totalProcessedElement
	 */
	public int getTotalProcessedElement() {
		return totalProcessedElement;
	}
	/**
	 * @param totalProcessedElement the totalProcessedElement to set
	 */
	public void setTotalProcessedElement(int totalProcessedElement) {
		this.totalProcessedElement = totalProcessedElement;
	}
	/**
	 * @return the totalWaitingDuration
	 */
	public long getTotalWaitingDuration() {
		return totalWaitingDuration;
	}
	/**
	 * @param totalWaitingDuration the totalWaitingDuration to set
	 */
	public void setTotalWaitingDuration(long totalWaitingDuration) {
		this.totalWaitingDuration = totalWaitingDuration;
	}
	/**
	 * @return the averageWaitingDuration
	 */
	public long getAverageWaitingDuration() {
		return averageWaitingDuration;
	}
	/**
	 * @param averageWaitingDuration the averageWaitingDuration to set
	 */
	public void setAverageWaitingDuration(long averageWaitingDuration) {
		this.averageWaitingDuration = averageWaitingDuration;
	}
	/**
	 * @return the averageProcessDuration
	 */
	public long getAverageProcessDuration() {
		return averageProcessDuration;
	}
	/**
	 * @param averageProcessDuration the averageProcessDuration to set
	 */
	public void setAverageProcessDuration(long averageProcessDuration) {
		this.averageProcessDuration = averageProcessDuration;
	}
	/**
	 * @return the totalWaitingElement
	 */
	public int getTotalWaitingElement() {
		return totalWaitingElement;
	}
	/**
	 * @param totalWaitingElement the totalWaitingElement to set
	 */
	public void setTotalWaitingElement(int totalWaitingElement) {
		this.totalWaitingElement = totalWaitingElement;
	}
	/**
	 * @return the currentTotalWaitingElements
	 */
	public int getCurrentTotalWaitingElements() {
		return currentTotalWaitingElements;
	}
	/**
	 * @param currentTotalWaitingElements the currentTotalWaitingElements to set
	 */
	public void setCurrentTotalWaitingElements(int currentTotalWaitingElements) {
		this.currentTotalWaitingElements = currentTotalWaitingElements;
	}
	/**
	 * @return the currentTotalInProcessElements
	 */
	public int getCurrentTotalInProcessElements() {
		return currentTotalInProcessElements;
	}
	/**
	 * @param currentTotalInProcessElements the currentTotalInProcessElements to set
	 */
	public void setCurrentTotalInProcessElements(int currentTotalInProcessElements) {
		this.currentTotalInProcessElements = currentTotalInProcessElements;
	}
	public String getAssignedWorker() {
		return assignedWorker;
	}
	public void setAssignedWorker(String assignedWorker) {
		this.assignedWorker = assignedWorker;
	}

}