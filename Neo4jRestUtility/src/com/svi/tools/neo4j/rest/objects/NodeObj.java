package com.svi.tools.neo4j.rest.objects;

import java.util.ArrayList;
import java.util.List;

public class NodeObj {

	private String nodeName;
	private List<String> filterList;
	private List<String> attribList;
	
	
	public NodeObj(String nodeName) {
		this.nodeName = nodeName;
		this.filterList = new ArrayList<String>();
		this.attribList = new ArrayList<String>();
	}


	public String getNodeName() {
		return nodeName;
	}


	public List<String> getFilterList() {
		return filterList;
	}


	public void addToFilterList(String filterType) {
		this.filterList.add(filterType);
	}


	public List<String> getAttribList() {
		return attribList;
	}


	public void addToAttribList(String attribType) {
		this.attribList.add(attribType);
	}


	@Override
	public String toString() {
		return "NodeObj [nodeName=" + nodeName + ", filterList=" + filterList
				+ ", attribList=" + attribList + "]";
	}
	
	
}
