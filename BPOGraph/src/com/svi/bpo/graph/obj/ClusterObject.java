package com.svi.bpo.graph.obj;

public class ClusterObject {
	private String clusterId;
	private long targetCompletionTime;
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
	 * @return the targetCompletionTime
	 */
	public long getTargetCompletionTime() {
		return targetCompletionTime;
	}
	/**
	 * @param targetCompletionTime the targetCompletionTime to set
	 */
	public void setTargetCompletionTime(long targetCompletionTime) {
		this.targetCompletionTime = targetCompletionTime;
	}
}
