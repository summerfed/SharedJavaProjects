package com.svi.bpo.objects;

public class ExceptionNodeDshBrdObj extends ExceptionDtlObj {

	private String minWaitTime;
	private String maxWaitTime;
	private String aveWaitTime;
	private String minProcTime;
	private String maxProcTime;
	private String aveProcTime;
	private String extra1;
	private String extra2;
	private String extra3;
	
	public ExceptionNodeDshBrdObj(){}
/*public ExceptionDtlObj(String nodeId,String exceptionName, String exceptionCode,
			long allowedWaitingDuration, long allowedProcessDuration,
			long currentTotalWaitingElements, long currentTotalInProcessElements) {
*/
	public ExceptionNodeDshBrdObj(String nodeId,String exceptionName,String exceptionCode,
			 int allowedWaitingTime, int allowedProcessingTime,int currentTotalWaitingElements, int currentTotalInProcessElements){
		super(nodeId, exceptionName, exceptionCode, allowedWaitingTime,allowedProcessingTime, currentTotalWaitingElements, currentTotalInProcessElements);

		
	}
	
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
