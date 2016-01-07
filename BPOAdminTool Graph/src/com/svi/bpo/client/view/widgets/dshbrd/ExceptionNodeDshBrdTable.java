package com.svi.bpo.client.view.widgets.dshbrd;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ExceptionNodeDshBrdTable extends Composite{

	private FlowPanel body;

	public ExceptionNodeDshBrdTable(){
		
		HTMLPanel nodeNameHdr = new HTMLPanel("Exception Code");
		nodeNameHdr.setStyleName("node-dshbrd-node-name-col");
		HTMLPanel inQueueHdr = new HTMLPanel("Exception Name");
		inQueueHdr.setStyleName("node-dshbrd-in-queue-col");
		HTMLPanel inProgHdr = new HTMLPanel("In Progress");
		inProgHdr.setStyleName("node-dshbrd-in-prog-col");
		HTMLPanel aveWTHdr = new HTMLPanel("Ave. Waiting Time");
		aveWTHdr.setStyleName("node-dshbrd-ave-wait-time-col");
		HTMLPanel avePTHdr = new HTMLPanel("Ave. Processing Time");
		avePTHdr.setStyleName("node-dshbrd-ave-proc-time-col");
		
		FlowPanel pnl = new FlowPanel();
		pnl.add(nodeNameHdr);
		pnl.add(inQueueHdr);
		pnl.add(inProgHdr);
		pnl.add(aveWTHdr);
		pnl.add(avePTHdr);
		
		FlowPanel header = new FlowPanel();
		header.setStyleName("node-dshbrd-tbl-hdr");
		header.add(pnl);
		
		body = new FlowPanel();
		body.setStyleName("node-dshbrd-tbl-body");
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("node-dshbrd-tbl-pnl");
		mainPnl.add(header);
		mainPnl.add(body);
		initWidget(mainPnl);
	}
	
	public FlowPanel getTableContents(){
		return body;
	}
	
}
