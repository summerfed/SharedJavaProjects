package com.svi.bpo.client.view.widgets.dshbrd;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.svi.bpo.objects.NodeDshBrdObj;
import com.svi.bpo.objects.NodeDtlObj;

public class NodeDshBrdRecord extends Composite {
	
	private NodeDshBrdObj node;
	private FlowPanel tblPnl;
	
	public NodeDshBrdRecord(NodeDshBrdObj node){
		
		this.node = node;
		
		tblPnl = new FlowPanel();
		
		HTMLPanel nodeNameCell = new HTMLPanel(node.getNodeName());
		nodeNameCell.setStyleName("node-dshbrd-node-name-col");
		HTMLPanel inQueueCell = new HTMLPanel(node.getElmtsWaiting());
		inQueueCell.setStyleName("node-dshbrd-in-queue-col");
		HTMLPanel inProgCell = new HTMLPanel(node.getElmtsInprogress());
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
	
	public NodeDtlObj getNodeObj() {
		return node;
	}

	public void setNodeObj(NodeDshBrdObj node) {
		this.node = node;
	}

	public void addClickHandler(ClickHandler handler){
		tblPnl.addDomHandler(handler, ClickEvent.getType());
	}
}
