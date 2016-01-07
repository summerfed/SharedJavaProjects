package com.svi.bpo.client.view;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.svi.bpo.client.presenter.DashboardViewScnPresenter.Display;
import com.svi.bpo.client.view.widgets.dshbrd.EndPointsExceptionDashboardWidget;
import com.svi.bpo.client.view.widgets.dshbrd.EndpointNodesDshBrdWidget;
import com.svi.bpo.client.view.widgets.dshbrd.ExceptionNodeDshBrdRecord;
import com.svi.bpo.client.view.widgets.dshbrd.NodeDshBrdRecord;
import com.svi.bpo.constants.Notification;
import com.svi.bpo.objects.ExceptionNodeDshBrdObj;
import com.svi.bpo.objects.NodeDshBrdObj;

public class DashboardViewScn extends BsScn implements Display {
	
	private FlowPanel tblPnl;
	private EndpointNodesDshBrdWidget dashBoard;
	private EndPointsExceptionDashboardWidget exDashboard;
	private FlowPanel nodeDashBoard;
	private FlowPanel exceptionDashBoard;
	private Button nodeTabBtn;
	//private FlowPanel tabPnl;
	private Button exceptionTabBtn;
	@Override
	public FlowPanel bldCtnt() {
		
		nodeDashBoard = new FlowPanel();
		nodeDashBoard.setStyleName("admin-node-pnl");
		exceptionDashBoard = new FlowPanel();
		exceptionDashBoard.setStyleName("admin-node-pnl");
		
		nodeTabBtn = new Button("Node");
		nodeTabBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				selectNodeTab();
			}
		});
		
		exceptionTabBtn = new Button("Exception Tab");
		exceptionTabBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				selectExceptionTab();
			}
		});
		
		FlowPanel tabBtnPnl = new FlowPanel();
		tabBtnPnl.setStyleName("admin-tab-btn-pnl");
		tabBtnPnl.add(nodeTabBtn);
		tabBtnPnl.add(exceptionTabBtn);
		
	//	tabPnl = new FlowPanel();
	//	tabPnl.setStyleName("admin-tab-body-pnl");
		
		getNavPnl().selectDashboardBtn();
		tblPnl = new FlowPanel();
		tblPnl.setStyleName("dshbrd-pnl");
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("admin-pnl");
		mainPnl.add(tabBtnPnl);
	//	mainPnl.add(tabPnl);
		mainPnl.add(tblPnl);
		selectNodeTab();
		return mainPnl;
	}

	@Override
	public void notify(Notification notification, String text){
		super.notify(notification, text);
	}

	@Override
	public EndpointNodesDshBrdWidget getDashBoard() {
		return dashBoard;
	}

	@Override
	public void addEndpoint(final EndpointNodesDshBrdWidget widget, List<NodeDshBrdObj> nodes){
		
		for(final NodeDshBrdObj node : nodes){
			final NodeDshBrdRecord record = new NodeDshBrdRecord(node);
			widget.addNode(record);
		}
		
		nodeDashBoard.add(widget);
	}

	
	
	@Override
	public FlowPanel getTblPnl() {
		return tblPnl;
	}
	
	public void selectNodeTab(){
		nodeTabBtn.setStyleName("admin-tab-btn-selected");
		
		exceptionTabBtn.removeStyleName("admin-tab-btn-selected");
		tblPnl.clear();
		tblPnl.add(nodeDashBoard);
	}
	
public void selectExceptionTab(){
	exceptionTabBtn.setStyleName("admin-tab-btn-selected");
	
	nodeTabBtn.removeStyleName("admin-tab-btn-selected");
	tblPnl.clear();
	tblPnl.add(exceptionDashBoard);
	}

@Override
public void addExceptionEndpoint(EndPointsExceptionDashboardWidget widget,
		List<ExceptionNodeDshBrdObj> nodes) {
	for(final ExceptionNodeDshBrdObj node : nodes){
		final ExceptionNodeDshBrdRecord record = new ExceptionNodeDshBrdRecord(node);
		widget.addNode(record);
	}
	
	exceptionDashBoard.add(widget);
}
	
	
}
