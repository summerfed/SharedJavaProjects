package com.svi.bpo.client.view.widgets.admin.node;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class EndpointNodesWidget extends Composite {

	private NodeTable tbl;
	private HTMLPanel hdr;
	
	public EndpointNodesWidget(String endpointName) {
		
		hdr = new HTMLPanel(endpointName);
		
//		HTMLPanel body = new HTMLPanel(endpointName);
//		body.setStyleName("endpt-nodes-wdgt-body");
		
		tbl = new NodeTable();
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("endpt-nodes-wdgt-pnl");
		mainPnl.add(hdr);
		mainPnl.add(tbl);
		
		initWidget(mainPnl);
		
		hdr.setStyleName("endpt-nodes-wdgt-hdr-loading");
		
	}
	
	public void addNode(NodeRecord record){
		tbl.getTableContents().add(record);
	}

	public HTMLPanel getHdr() {
		return hdr;
	}

	public NodeTable getTbl() {
		return tbl;
	}
	
	public void loading(){
		hdr.setStyleName("endpt-nodes-wdgt-hdr-loading");
	}
	
	public void stopLoading(){
		hdr.setStyleName("endpt-nodes-wdgt-hdr");
	}

	
}
