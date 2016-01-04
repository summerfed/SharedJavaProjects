package com.svi.bpo.objects;


public class NodeDshBrdObj extends NodeDtlObj {

	//private String elmtsCmpltd = "0";
	private String minWaitTime;
	private String maxWaitTime;
	private String aveWaitTime;
	private String minProcTime;
	private String maxProcTime;
	private String aveProcTime;
	private String extra1;
	private String extra2;
	private String extra3;
	
	public NodeDshBrdObj(){}

	public NodeDshBrdObj(String nodeId, String nodeName, String endpointId,
			String elmtsWaiting, String elmtsInprogress,
			String cluster, String cost, String status, String stdUnitOfMeasure,
			String allowedMinWait, String allowedMaxWait,
			String allowedAveWait, String allowedMinProc,
			String allowedMaxProc, String allowedAveProc, String targetOutput,
			String allowedError, String errorNode, String nodeExceptionOf,
			String elmtsCmpltd,
			String aveWaitTime, String aveProcTime, String extra1, 
			String extra2, String extra3) {
	//	super(nodeId, nodeName, endpointId, elmtsWaiting, elmtsInprogress, cluster, cost, status, stdUnitOfMeasure, allowedMinWait, allowedMaxWait, allowedAveWait, allowedMinProc, allowedMaxProc, allowedAveProc, targetOutput, allowedError, errorNode, nodeExceptionOf);
	
		this.aveWaitTime = aveWaitTime;
		this.aveProcTime = aveProcTime;
		this.extra1 = extra1;
		this.extra2 = extra2;
		this.extra3 = extra3;
	}
	public NodeDshBrdObj(String nodeId,String nodeName,String cluster,String stdUnitOfMeasure,String status,
			double cost, int allowedWaitingTime, int allowedProcessingTime,int targetOutputCount, int allowedErrorCount){
		super(nodeId, nodeName, cluster, stdUnitOfMeasure, cost, allowedWaitingTime, allowedProcessingTime, targetOutputCount, allowedErrorCount);

		
	}

	
//	public String getElmtsCmpltd() {
//		return elmtsCmpltd;
//	}
//
//	public void setElmtsCmpltd(String elmtsCmpltd) {
//		this.elmtsCmpltd = elmtsCmpltd;
//	}

	public String getMinWaitTime() {
		return minWaitTime;
	}

	public void setMinWaitTime(String minWaitTime) {
		this.minWaitTime = minWaitTime;
	}

	public String getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(String maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public String getAveWaitTime() {
		return aveWaitTime;
	}

	public void setAveWaitTime(String aveWaitTime) {
		this.aveWaitTime = aveWaitTime;
	}

	public String getMinProcTime() {
		return minProcTime;
	}

	public void setMinProcTime(String minProcTime) {
		this.minProcTime = minProcTime;
	}

	public String getMaxProcTime() {
		return maxProcTime;
	}

	public void setMaxProcTime(String maxProcTime) {
		this.maxProcTime = maxProcTime;
	}

	public String getAveProcTime() {
		return aveProcTime;
	}

	public void setAveProcTime(String aveProcTime) {
		this.aveProcTime = aveProcTime;
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
