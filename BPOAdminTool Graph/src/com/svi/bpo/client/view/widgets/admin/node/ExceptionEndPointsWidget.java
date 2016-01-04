package com.svi.bpo.client.view.widgets.admin.node;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ExceptionEndPointsWidget extends Composite {

	private ExceptionNodeTable tbl;
	private HTMLPanel hdr;
	
	public ExceptionEndPointsWidget(String endpointName) {
		
		hdr = new HTMLPanel(endpointName);
		
//		HTMLPanel body = new HTMLPanel(endpointName);
//		body.setStyleName("endpt-nodes-wdgt-body");
		
		tbl = new ExceptionNodeTable();
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("endpt-nodes-wdgt-pnl");
		mainPnl.add(hdr);
		mainPnl.add(tbl);
		
		initWidget(mainPnl);
		
		hdr.setStyleName("endpt-nodes-wdgt-hdr-loading");
		
	}
	
	public void addNode(ExceptionNodeRecord record){
		tbl.getTableContents().add(record);
	}

	public HTMLPanel getHdr() {
		return hdr;
	}

	public ExceptionNodeTable getTbl() {
		return tbl;
	}
	
	public void loading(){
		hdr.setStyleName("endpt-nodes-wdgt-hdr-loading");
	}
	
	public void stopLoading(){
		hdr.setStyleName("endpt-nodes-wdgt-hdr");
	}

	
}
