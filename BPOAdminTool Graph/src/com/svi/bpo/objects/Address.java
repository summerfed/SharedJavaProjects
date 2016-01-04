//package com.svi.bpo.objects;
//
//import java.io.Serializable;
//
//
//public class Address implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -4435778211134555959L;
//
//	private String name;
//	private String address;
//	private double lat;
//	private double lng;
//	private String dstnCmp;
//	private String docType;
//	private String instn;
//	private String time;
//	private String remarks;
//	
//	public Address() {
//		
//	}
//	
//	public Address(String name, String address, double lat, double lng
//			, String dstnCmp, String docType, String instn, String time
//			, String remarks) {
//		this.name = name;
//		this.address = address;
//		this.lat = lat;
//		this.lng = lng;
//		this.dstnCmp = dstnCmp;
//		this.docType = docType;
//		this.instn = instn;
//		this.time = time;
//		this.remarks = remarks;
//	}
//	
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getAddress() {
//		return address;
//	}
//	public void setAddress(String address) {
//		this.address = address;
//	}
//	
//	public String getDstnCmp() {
//		return dstnCmp;
//	}
//
//	public void setDstnCmp(String dstnCmp) {
//		this.dstnCmp = dstnCmp;
//	}
//
//	public String getDocType() {
//		return docType;
//	}
//
//	public void setDocType(String docType) {
//		this.docType = docType;
//	}
//
//	public String getInstn() {
//		return instn;
//	}
//
//	public void setInstn(String instn) {
//		this.instn = instn;
//	}
//
//	public String getTime() {
//		return time;
//	}
//
//	public void setTime(String time) {
//		this.time = time;
//	}
//
//	public String getRemarks() {
//		return remarks;
//	}
//
//	public void setRemarks(String remarks) {
//		this.remarks = remarks;
//	}
//
//	public void setLat(double lat) {
//		this.lat = lat;
//	}
//
//	public void setLng(double lng) {
//		this.lng = lng;
//	}
//
//	public double getLat() {
//		return lat;
//	}
//	
//	public double getLng() {
//		return lng;
//	}
//	
//	@Override
//	public String toString() {
//		return "name : " + name
//				+ " address : " + address
//				+ " position : (" + lat + "," + lng + ")"
//				+ " dstnCmp : " + dstnCmp
//				+ " docType : " + docType
//				+ " instn : " + instn
//				+ " time : " + time
//				+ " remarks : " + remarks;
//	}	
//	
//}
