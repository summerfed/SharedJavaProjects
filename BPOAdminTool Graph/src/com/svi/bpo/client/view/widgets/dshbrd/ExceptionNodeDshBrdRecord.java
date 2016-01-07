package com.svi.bpo.client.view.widgets.dshbrd;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.svi.bpo.objects.ExceptionDtlObj;
import com.svi.bpo.objects.ExceptionNodeDshBrdObj;


public class ExceptionNodeDshBrdRecord extends Composite {
	private ExceptionNodeDshBrdObj node;
	private FlowPanel tblPnl;
	
	public ExceptionNodeDshBrdRecord(ExceptionNodeDshBrdObj node){
		
		this.node = node;
		
		tblPnl = new FlowPanel();
		
		HTMLPanel nodeNameCell = new HTMLPanel(node.getExceptionCode());
		nodeNameCell.setStyleName("node-dshbrd-node-name-col");
		HTMLPanel inQueueCell = new HTMLPanel(node.getExceptionName());
		inQueueCell.setStyleName("node-dshbrd-in-queue-col");
		HTMLPanel inProgCell = new HTMLPanel(node.getCurrentTotalWaitingElements()+"");
		inProgCell.setStyleName("node-dshbrd-in-prog-col");
		HTMLPanel aveWaitTimeCell = new HTMLPanel(node.getAveWaitTime());
		aveWaitTimeCell.setStyleName("node-dshbrd-ave-wait-time-col");
		HTMLPanel aveProgTimeCell = new HTMLPanel(node.getAveProcTime());
		aveProgTimeCell.setStyleName("node-dshbrd-ave-wait-proc-col");

		FlowPanel mainPnl = new FlowPanel();
		tblPnl.add(nodeNameCell);
		tblPnl.add(inQueueCell);
		tblPnl.add(inProgCell);
		tblPnl.add(aveWaitTimeCell);
		tblPnl.add(aveProgTimeCell);
		mainPnl.add(tblPnl);
		
		initWidget(mainPnl);
	}
	
	public ExceptionDtlObj getNodeObj() {
		return node;
	}

	public void setNodeObj(ExceptionNodeDshBrdObj node) {
		this.node = node;
	}

	public void addClickHandler(ClickHandler handler){
		tblPnl.addDomHandler(handler, ClickEvent.getType());
	}
}
