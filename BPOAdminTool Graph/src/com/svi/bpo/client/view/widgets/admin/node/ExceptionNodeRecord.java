package com.svi.bpo.client.view.widgets.admin.node;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.svi.bpo.objects.ExceptionDtlObj;
import com.svi.bpo.objects.NodeDtlObj;

public class ExceptionNodeRecord extends Composite {
	
	private ExceptionDtlObj node;
	private Button viewBtn;
	private FlowPanel tblPnl;
	
	public ExceptionNodeRecord(ExceptionDtlObj node){
		
		this.node = node;
		
		tblPnl = new FlowPanel();
		
		HTMLPanel queueNoCell = new HTMLPanel(node.getExceptionCode());
		queueNoCell.setStyleName("node-queue-no-col");
		HTMLPanel queueDescCell = new HTMLPanel(node.getExceptionName());
		queueDescCell.setStyleName("node-queue-desc-col");
		
		HTMLPanel queueWaitingCell = new HTMLPanel(node.getCurrentTotalWaitingElements()+"");
		queueWaitingCell.setStyleName("node-wait-col");
		
		HTMLPanel queueInProgressCell = new HTMLPanel(node.getCurrentTotalInProcessElements()+"");
		queueInProgressCell.setStyleName("node-inprog-col");
		HTMLPanel queueTotalCell = new HTMLPanel((node.getCurrentTotalWaitingElements()+node.getCurrentTotalInProcessElements())+"");
		queueTotalCell.setStyleName("node-totalElem-col");
		
		
		viewBtn = new Button("VIEW");
		FlowPanel btnPnl = new FlowPanel();
		btnPnl.setStyleName("node-view-btn-col");
		btnPnl.add(viewBtn);
		
		FlowPanel mainPnl = new FlowPanel();
//		mainPnl.setTitle("End Point: " + node.getEndpointId());
		tblPnl.add(queueNoCell);
		tblPnl.add(queueDescCell);
		tblPnl.add(queueWaitingCell);
		tblPnl.add(queueInProgressCell);
		tblPnl.add(queueTotalCell);
	//	tblPnl.add(testCell);
		mainPnl.add(tblPnl);
		mainPnl.add(btnPnl);	//Uncomment
		
		initWidget(mainPnl);
	}
	
	public ExceptionDtlObj getExceptionNodeObj() {
		return node;
	}

	public void setNodeObj(ExceptionDtlObj node) {
		this.node = node;
	}

	public Button getViewBtn() {
		return viewBtn;
	}
	
	public void addClickHandler(ClickHandler handler){
		tblPnl.addDomHandler(handler, ClickEvent.getType());
	}
}
