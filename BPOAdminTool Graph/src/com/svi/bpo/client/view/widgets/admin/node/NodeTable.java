package com.svi.bpo.client.view.widgets.admin.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.svi.bpo.objects.NodeDtlObj;

public class NodeTable extends Composite{
	
	private FlowPanel body;
	private Map<String, NodeDtlObj> nodesSelected;

	public NodeTable(){
		
		nodesSelected = new HashMap<String, NodeDtlObj>();
		
		HTMLPanel queueNoHdr = new HTMLPanel("Node ID");
		queueNoHdr.setStyleName("node-queue-no-col");
		HTMLPanel queueDescHdr = new HTMLPanel("Node Name");
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
	
	public Map<String, NodeDtlObj> getMapOfSelectedNodes(){
		return nodesSelected;
	}
	
	public List<NodeDtlObj> getSelectedNodes() {
		List<NodeDtlObj> nodes = new ArrayList<NodeDtlObj>();
		nodes.addAll(nodesSelected.values());
		return nodes;
	}
	
	
	
	public void clearSelectedNodes(){
		nodesSelected.clear();
	}
	
}
