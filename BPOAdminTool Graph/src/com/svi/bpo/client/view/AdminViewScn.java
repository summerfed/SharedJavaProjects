package com.svi.bpo.client.view;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.client.presenter.AdminViewScnPresenter.Display;
import com.svi.bpo.client.view.widgets.BannerPanel;
import com.svi.bpo.client.view.widgets.EndpointListPanel;
import com.svi.bpo.client.view.widgets.admin.batch.AdminBatchUploadPanel;
import com.svi.bpo.client.view.widgets.admin.batch.BatchUploadWidget;
import com.svi.bpo.client.view.widgets.admin.batch.ResultWidget;
import com.svi.bpo.client.view.widgets.admin.node.AdminNodePanel;
import com.svi.bpo.client.view.widgets.admin.node.ExceptionNodePanel;
import com.svi.bpo.client.view.widgets.admin.node.InsertExceptionNodePanel;
import com.svi.bpo.client.view.widgets.admin.node.InsertNodePanel;
import com.svi.bpo.client.view.widgets.admin.node.elements.ChangePriorityLvlPanel;
import com.svi.bpo.client.view.widgets.admin.node.elements.ChangeWorkerPanel;
import com.svi.bpo.client.view.widgets.admin.node.elements.InsertElementPanel;
import com.svi.bpo.client.view.widgets.admin.node.elements.TransferElementPanel;
import com.svi.bpo.client.view.widgets.admin.node.elements.ViewElementPanel;
import com.svi.bpo.constants.Notification;

public class AdminViewScn extends BsScn implements Display{

	private Button nodeTabBtn;
	private Button batchUpldTabBtn;
	private Button exceptionTabBtn;
	private FlowPanel tabPnl;
	
	private AdminNodePanel nodeTabPnl;
	private AdminBatchUploadPanel batchUpldTabPnl;
	private ExceptionNodePanel exceptionTabPnl;	
	@Override
	public FlowPanel bldCtnt() {
		
		getNavPnl().selectAdminBtn();
		
		nodeTabBtn = new Button("Node");
		nodeTabBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				selectNodeTab();
			}
		});
		
		batchUpldTabBtn = new Button("Batch Upload");
		batchUpldTabBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				selectBatchUpldTab();
			}
		});
		
		exceptionTabBtn = new Button("Exception");
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
		tabBtnPnl.add(batchUpldTabBtn);
		
		tabPnl = new FlowPanel();
		tabPnl.setStyleName("admin-tab-body-pnl");
		
		nodeTabPnl = new AdminNodePanel();
		batchUpldTabPnl = new AdminBatchUploadPanel();
		exceptionTabPnl = new ExceptionNodePanel();
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("admin-pnl");
		mainPnl.add(tabBtnPnl);
		mainPnl.add(tabPnl);
		
		if(CommonObjs.bpoProps.getMode().equalsIgnoreCase("Y")){
			batchUpldTabBtn.setVisible(false);
			batchUpldTabPnl.setVisible(false);
			exceptionTabBtn.setVisible(false);
			exceptionTabPnl.setVisible(false);
		}
		
		selectNodeTab();
		
		return mainPnl;
	}
	
	private void selectNodeTab(){
		nodeTabBtn.setStyleName("admin-tab-btn-selected");
		batchUpldTabBtn.removeStyleName("admin-tab-btn-selected");
		exceptionTabBtn.removeStyleName("admin-tab-btn-selected");
		tabPnl.clear();
		tabPnl.add(nodeTabPnl);
	}
	
	private void selectBatchUpldTab(){
		batchUpldTabBtn.setStyleName("admin-tab-btn-selected");
		nodeTabBtn.removeStyleName("admin-tab-btn-selected");
		exceptionTabBtn.removeStyleName("admin-tab-btn-selected");
		tabPnl.clear();
		tabPnl.add(batchUpldTabPnl);
	}
	
	private void selectExceptionTab(){
		exceptionTabBtn.setStyleName("admin-tab-btn-selected");
		batchUpldTabBtn.removeStyleName("admin-tab-btn-selected");
		nodeTabBtn.removeStyleName("admin-tab-btn-selected");
		tabPnl.clear();
		tabPnl.add(exceptionTabPnl);
	}
	
	
	@Override
	public AdminNodePanel getNodeTabPanel(){
		return nodeTabPnl;
	}
	
	@Override
	public InsertNodePanel getAddNodePanel(){
		return nodeTabPnl.getAddNodePnl();
	}
	
	@Override
	public ViewElementPanel getViewElemPanel(){
		return nodeTabPnl.getViewElemPnl();
	}
	
	@Override
	public InsertElementPanel getInsertElemPanel(){
		return nodeTabPnl.getViewElemPnl().getInsertElemPnl();
	}

	@Override
	public AdminBatchUploadPanel getBatchUpldTabPnl() {
		return batchUpldTabPnl;
	}

	@Override
	public BatchUploadWidget getNodeUpldWidget() {
		return batchUpldTabPnl.getNodeUpldWidget();
	}

	@Override
	public BatchUploadWidget getElemUpldWidget() {
		return batchUpldTabPnl.getElemUpldWidget();
	}

	@Override
	public ResultWidget getResultWdgt() {
		return batchUpldTabPnl.getResultWdgt();
	}
	
	@Override
	public BannerPanel getBannerPanel(){
		return super.getBannrPnl();
	}
	
	@Override
	public FlowPanel getViewPanel(){
		return super.getViewPanel();
	}

	@Override
	public List<String> getEndpoints() {
		return super.getEndpointPnl().getEndpoints();
	}

	@Override
	public EndpointListPanel getEndpointPnl() {
		return super.getEndpointPnl();
	}

	@Override
	public void showLoadingScn(){
		super.showLoadingScn();
	}

	@Override
	public void hideLoadingScn(){
		super.hideLoadingScn();
	}

	@Override
	public void notify(Notification notification, String text){
		super.notify(notification, text);
	}

	@Override
	public ChangePriorityLvlPanel getChangePriorityLvlPanel() {
		return nodeTabPnl.getViewElemPnl().getChangePriorityLvlPanel();
	}

	@Override
	public TransferElementPanel getTransferElmntPnl() {
		return nodeTabPnl.getViewElemPnl().getTrnsfrElmPnl();
	}
	
	@Override
	public ChangeWorkerPanel getChangeWorkerPanel() {
		return nodeTabPnl.getViewElemPnl().getChangeWorkerPanel();
	}

	public ExceptionNodePanel getExceptionTabPnl() {
		return exceptionTabPnl;
	}

	public void setExceptionTabPnl(ExceptionNodePanel exceptionTabPnl) {
		this.exceptionTabPnl = exceptionTabPnl;
	}

	@Override
	public InsertExceptionNodePanel getAddExceptionNodePanel() {
		// TODO Auto-generated method stub
		
		return exceptionTabPnl.getAddNodePnl();
	}
	
}
