package com.svi.bpo.client.view.widgets.admin.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.svi.bpo.objects.ExceptionDtlObj;


public class ExceptionNodeTable extends Composite{
	
	private FlowPanel body;
	private Map<String, ExceptionDtlObj> nodesSelected;

	public ExceptionNodeTable(){
		
		nodesSelected = new HashMap<String, ExceptionDtlObj>();
		
		HTMLPanel queueNoHdr = new HTMLPanel("Exception Code");
		queueNoHdr.setStyleName("node-queue-no-col");
		HTMLPanel queueDescHdr = new HTMLPanel("Exception Name");
		queueDescHdr.setStyleName("node-queue-desc-col");
		HTMLPanel waitingHdr = new HTMLPanel("Waiting");
		waitingHdr.setStyleName("node-wait-col");
		HTMLPanel inProgHdr = new HTMLPanel("In Progress");
		inProgHdr.setStyleName("node-inprog-col");
		HTMLPanel totalElemHdr = new HTMLPanel("Total");
		totalElemHdr.setStyleName("node-totalElem-col");
		HTMLPanel nodeStatus = new HTMLPanel("Status");
		nodeStatus.setStyleName("node-status-col");
		FlowPanel pnl = new FlowPanel();
		pnl.add(queueNoHdr);
		pnl.add(queueDescHdr);
		pnl.add(waitingHdr);
		pnl.add(inProgHdr);
		pnl.add(totalElemHdr);
		pnl.add(nodeStatus);
		FlowPanel header = new FlowPanel();
		header.setStyleName("node-tbl-hdr");
		header.add(pnl);
		
		body = new FlowPanel();
		body.setStyleName("node-tbl-body");
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("node-tbl-pnl");
		mainPnl.add(header);
		mainPnl.add(body);
		initWidget(mainPnl);
	}
	
	public FlowPanel getTableContents(){
		return body;
	}
	
	public Map<String, ExceptionDtlObj> getMapOfSelectedNodes(){
		return nodesSelected;
	}
	
	public List<ExceptionDtlObj> getSelectedNodes() {
		List<ExceptionDtlObj> nodes = new ArrayList<ExceptionDtlObj>();
		nodes.addAll(nodesSelected.values());
		return nodes;
	}
	
	public void clearSelectedNodes(){
		nodesSelected.clear();
	}
	
}
