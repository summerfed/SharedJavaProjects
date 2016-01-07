package com.svi.bpo.client.view.widgets.dshbrd;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class EndPointsExceptionDashboardWidget extends Composite {

	private ExceptionNodeDshBrdTable tbl;
	private HTMLPanel hdr;
	
	public EndPointsExceptionDashboardWidget(String endpointName) {
		
		hdr = new HTMLPanel(endpointName);
		
		tbl = new ExceptionNodeDshBrdTable();
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("endpt-nodes-dshbrd-wdgt-pnl");
		mainPnl.add(hdr);
		mainPnl.add(tbl);
		
		initWidget(mainPnl);
		
		hdr.setStyleName("endpt-nodes-dshbrd-wdgt-hdr-loading");
		
	}
	
	public void addNode(ExceptionNodeDshBrdRecord record){
		tbl.getTableContents().add(record);
	}

	
	public HTMLPanel getHdr() {
		return hdr;
	}

	public ExceptionNodeDshBrdTable getTbl() {
		return tbl;
	}
	
	public void loading(){
		hdr.setStyleName("endpt-nodes-dshbrd-wdgt-hdr-loading");
	}
	
	public void stopLoading(){
		hdr.setStyleName("endpt-nodes-dshbrd-wdgt-hdr");
	}

	
	
}
