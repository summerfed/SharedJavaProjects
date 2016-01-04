package com.svi.bpo.objects;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UpldRsltObj implements IsSerializable{

	private String fileName;
	private String noOfCompleted;
	private String noOfError;
	private List<String> dtlList;
	
	public UpldRsltObj() {
		dtlList = new ArrayList<String>();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getNoOfCompleted() {
		
		return noOfCompleted;
	}

	public void setNoOfCompleted(String noOfCompleted) {
		this.noOfCompleted = noOfCompleted;
	}

	public String getNoOfError() {
		return noOfError;
	}

	public void setNoOfError(String noOfError) {
		this.noOfError = noOfError;
	}

	public List<String> getDtlList() {
		return dtlList;
	}

	public void putOnList(String key, String value) {
		String dtl = key + "|" + value;
		dtlList.add(dtl);
	}

	@Override
	public String toString() {
		return "UpldRsltObj [fileName=" + fileName + ", noOfCompleted="
				+ noOfCompleted + ", noOfError=" + noOfError + ", dtlMap="
				+ dtlList + "]";
	}
	
	
}
