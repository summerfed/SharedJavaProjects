package com.svi.bpo.graph.obj;

public class ExceptionNodeObject {
	private String exceptionName;
	private String exceptionCode;
	private long allowedWaitingDuration;
	private long allowedProcessDuration;
	private long currentTotalWaitingElements;
	private long currentTotalInProcessElements;
	/**
	 * @return the exceptionName
	 */
	public String getExceptionName() {
		return exceptionName;
	}
	/**
	 * @param exceptionName the exceptionName to set
	 */
	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	/**
	 * @return the exceptionCode
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}
	/**
	 * @param exceptionCode the exceptionCode to set
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	/**
	 * @return the allowedWaitingDuration
	 */
	public long getAllowedWaitingDuration() {
		return allowedWaitingDuration;
	}
	/**
	 * @param allowedWaitingDuration the allowedWaitingDuration to set
	 */
	public void setAllowedWaitingDuration(long allowedWaitingDuration) {
		this.allowedWaitingDuration = allowedWaitingDuration;
	}
	/**
	 * @return the allowedProcessDuration
	 */
	public long getAllowedProcessDuration() {
		return allowedProcessDuration;
	}
	/**
	 * @param allowedProcessDuration the allowedProcessDuration to set
	 */
	public void setAllowedProcessDuration(long allowedProcessDuration) {
		this.allowedProcessDuration = allowedProcessDuration;
	}
	/**
	 * @return the currentTotalWaitingElements
	 */
	public long getCurrentTotalWaitingElements() {
		return currentTotalWaitingElements;
	}
	/**
	 * @param currentTotalWaitingElements the currentTotalWaitingElements to set
	 */
	public void setCurrentTotalWaitingElements(long currentTotalWaitingElements) {
		this.currentTotalWaitingElements = currentTotalWaitingElements;
	}
	/**
	 * @return the currentTotalInProcessElements
	 */
	public long getCurrentTotalInProcessElements() {
		return currentTotalInProcessElements;
	}
	/**
	 * @param currentTotalInProcessElements the currentTotalInProcessElements to set
	 */
	public void setCurrentTotalInProcessElements(long currentTotalInProcessElements) {
		this.currentTotalInProcessElements = currentTotalInProcessElements;
	}
}
