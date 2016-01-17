package com.svi.bpo.client.view.widgets.admin.node;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.svi.bpo.objects.NodeDtlObj;

public class NodeRecord extends Composite {
	
	private NodeDtlObj node;
	private Button viewBtn;
	private FlowPanel tblPnl;
	
	public NodeRecord(NodeDtlObj node){
		
		this.node = node;
		
		tblPnl = new FlowPanel();
		
		HTMLPanel queueNoCell = new HTMLPanel(node.getNodeId());
		queueNoCell.setStyleName("node-queue-no-col");
		HTMLPanel queueDescCell = new HTMLPanel(node.getNodeName());
		queueDescCell.setStyleName("node-queue-desc-col");
		HTMLPanel waitingCell = new HTMLPanel(""+node.getElmtsWaiting());
		waitingCell.setStyleName("node-wait-col");
		HTMLPanel inProgCell = new HTMLPanel(""+node.getElmtsInprogress());
		inProgCell.setStyleName("node-inprog-col");
		HTMLPanel totalElemCell = new HTMLPanel(node.getElmtsWaiting() +node.getElmtsInprogress()+"");
		totalElemCell.setStyleName("node-totalElem-col");
		
		
		HTMLPanel statusCell;
		if(node.isCanMeetTarget()){
		statusCell = new HTMLPanel(" ");
		statusCell.setStyleName("node-status-green");
		
		}
		else{
		statusCell = new HTMLPanel(" ");
		statusCell.setStyleName("node-status-red");
		}
		
		
	//	HTMLPanel testCell = new HTMLPanel("Test only");
	//	 testCell.setStyleName("node-totalElem-col");
		
		viewBtn = new Button("VIEW");
		FlowPanel btnPnl = new FlowPanel();
		btnPnl.setStyleName("node-view-btn-col");
		btnPnl.add(viewBtn);
		
		FlowPanel mainPnl = new FlowPanel();
//		mainPnl.setTitle("End Point: " + node.getEndpointId());
		tblPnl.add(queueNoCell);
		tblPnl.add(queueDescCell);
		tblPnl.add(waitingCell);
		tblPnl.add(inProgCell);
		tblPnl.add(totalElemCell);
		tblPnl.add(statusCell);
	//	tblPnl.add(testCell);
		mainPnl.add(tblPnl);
		mainPnl.add(btnPnl);
		
		initWidget(mainPnl);
	}
	
	public NodeDtlObj getNodeObj() {
		return node;
	}

	public void setNodeObj(NodeDtlObj node) {
		this.node = node;
	}

	public Button getViewBtn() {
		return viewBtn;
	}
	
	public void addClickHandler(ClickHandler handler){
		tblPnl.addDomHandler(handler, ClickEvent.getType());
	}
}
