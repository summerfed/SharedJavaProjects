package com.svi.bpo.client.view.widgets.admin.node;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.objects.EndpointObj;

public class InsertExceptionNodeTextBoxes extends Composite{
	
	private LabeledTxtBx exceptionNameTxtbx;
	private LabeledTxtBx nodeIdTxtbx;
	private LabeledTxtBx exceptionCodeTxtbx;
	private LabeledTxtBx allowedWaitingDurationTxtbx;
	private LabeledTxtBx allowedProcessDurationTxtbx;
	private LabeledTxtBx currentTotalWaitingElementsTxtbx;
	private LabeledTxtBx currentTotalInProcessElementsTxtbx;

	public TextBox getExceptionNameTextBox(){
		
		return exceptionNameTxtbx.getTxtBx();
	}
	
	public TextBox getExceptionCodeTextbox(){
		
		return exceptionCodeTxtbx.getTxtBx();
	}
public TextBox getNodeIdTextbox(){
		
		return nodeIdTxtbx.getTxtBx();
	}
	
	public TextBox getAllowedWaitingDurationTxtbxTextbox(){
		
		return allowedWaitingDurationTxtbx.getTxtBx();
	}
public TextBox getAllowedProcessDurationTxtbxTextbox(){
		
		return allowedProcessDurationTxtbx.getTxtBx();
	}

public TextBox getCurrentTotalWaitingElementsTxtbxTextbox(){
	
	return currentTotalWaitingElementsTxtbx.getTxtBx();
}

public TextBox getCurrentTotalInProcessElementsTxtbxTextbox(){
	
	return currentTotalInProcessElementsTxtbx.getTxtBx();
}
	
	public LabeledTxtBx getExceptionNameTxtbx() {
		return exceptionNameTxtbx;
	}

	public void setExceptionNameTxtbx(LabeledTxtBx exceptionNameTxtbx) {
		this.exceptionNameTxtbx = exceptionNameTxtbx;
	}

	public LabeledTxtBx getExceptionCodeTxtbx() {
		return exceptionCodeTxtbx;
	}

	public void setExceptionCodeTxtbx(LabeledTxtBx exceptionCodeTxtbx) {
		this.exceptionCodeTxtbx = exceptionCodeTxtbx;
	}

	public LabeledTxtBx getAllowedWaitingDurationTxtbx() {
		return allowedWaitingDurationTxtbx;
	}

	public void setAllowedWaitingDurationTxtbx(
			LabeledTxtBx allowedWaitingDurationTxtbx) {
		this.allowedWaitingDurationTxtbx = allowedWaitingDurationTxtbx;
	}

	public LabeledTxtBx getAllowedProcessDurationTxtbx() {
		return allowedProcessDurationTxtbx;
	}

	public void setAllowedProcessDurationTxtbx(
			LabeledTxtBx allowedProcessDurationTxtbx) {
		this.allowedProcessDurationTxtbx = allowedProcessDurationTxtbx;
	}

	public LabeledTxtBx getCurrentTotalWaitingElementsTxtbx() {
		return currentTotalWaitingElementsTxtbx;
	}

	public void setCurrentTotalWaitingElementsTxtbx(
			LabeledTxtBx currentTotalWaitingElementsTxtbx) {
		this.currentTotalWaitingElementsTxtbx = currentTotalWaitingElementsTxtbx;
	}

	public LabeledTxtBx getCurrentTotalInProcessElementsTxtbx() {
		return currentTotalInProcessElementsTxtbx;
	}

	public void setCurrentTotalInProcessElementsTxtbx(
			LabeledTxtBx currentTotalInProcessElementsTxtbx) {
		this.currentTotalInProcessElementsTxtbx = currentTotalInProcessElementsTxtbx;
	}

	public LabeledListBx getEndPointsListBx() {
		return endPointsListBx;
	}

	public void setEndPointsListBx(LabeledListBx endPointsListBx) {
		this.endPointsListBx = endPointsListBx;
	}

	
	private LabeledListBx endPointsListBx;
	
	public InsertExceptionNodeTextBoxes(){
		
		exceptionCodeTxtbx = new LabeledTxtBx("Exception Code :",true);
		exceptionCodeTxtbx.setStyleName("insert-node-txtbx-widget-input");
		exceptionNameTxtbx = new LabeledTxtBx("Exception Name:",true);
		exceptionNameTxtbx.setStyleName("insert-node-txtbx-widget-input");
		nodeIdTxtbx = new LabeledTxtBx("Node Id:",true);
		nodeIdTxtbx.setStyleName("insert-node-txtbx-widget-input");
		allowedWaitingDurationTxtbx = new LabeledTxtBx("AllowedWaiting :");
		allowedWaitingDurationTxtbx.setStyleName("insert-node-txtbx-widget-input",true);
		allowedProcessDurationTxtbx = new LabeledTxtBx("Allowed Process:");
		allowedProcessDurationTxtbx.setStyleName("insert-node-txtbx-widget-input");
		currentTotalWaitingElementsTxtbx = new LabeledTxtBx("Current Wait:");
		currentTotalWaitingElementsTxtbx.setStyleName("insert-node-txtbx-widget-input");
		currentTotalInProcessElementsTxtbx = new LabeledTxtBx("Current In Process:");
		currentTotalInProcessElementsTxtbx.setStyleName("insert-node-txtbx-widget-input");

		//		minWaitTxtbx = new LabeledTxtBx("Allowable Min Waiting Time:");
//		minWaitTxtbx.setStyleName("insert-node-txtbx-widget-input");
//		maxWaitTxtbx = new LabeledTxtBx("Allowable Max Waiting Time:");
//		maxWaitTxtbx.setStyleName("insert-node-txtbx-widget-input");
//		aveWaitTxtbx = new LabeledTxtBx("Allowable Ave Waiting Time:");
//		aveWaitTxtbx.setStyleName("insert-node-txtbx-widget-input");
//		minProcTxtbx = new LabeledTxtBx("Allowable Min Processing Time:");
//		minProcTxtbx.setStyleName("insert-node-txtbx-widget-input");
//		maxProcTxtbx = new LabeledTxtBx("Allowable Max Processing Time:");
//		maxProcTxtbx.setStyleName("insert-node-txtbx-widget-input");
//		aveProcTxtbx = new LabeledTxtBx("Allowable Ave Processing Time:");
//		aveProcTxtbx.setStyleName("insert-node-txtbx-widget-input");
		//		errorNodeTxtbx = new LabeledTxtBx("Error Node:");
//		errorNodeTxtbx.setStyleName("insert-node-txtbx-widget-input");
//		nodeExceptTxtbx = new LabeledTxtBx("Node Exception Of:");
//		nodeExceptTxtbx.setStyleName("insert-node-txtbx-widget-input");
//		statusListBx = new LabeledListBx("Status:");
//		statusListBx.setStyleName("insert-node-txtbx-widget-input");
//		statusListBx.addItem("OPEN");
//		statusListBx.addItem("CLOSE");
		endPointsListBx = new LabeledListBx("Endpoint:");
		endPointsListBx.setStyleName("insert-node-txtbx-widget-input");
		for (EndpointObj obj : CommonObjs.bpoUser.getEndpoints()) {
			if (obj.isActive()) {
				endPointsListBx.addItem(obj.getEndpointId());
			}
		}
		
		FlowPanel txtPnl1 = new FlowPanel();
		txtPnl1.setStyleName("insert-node-col1");
		txtPnl1.add(exceptionCodeTxtbx);
		txtPnl1.add(nodeIdTxtbx);
		txtPnl1.add(exceptionNameTxtbx);

		txtPnl1.add(allowedWaitingDurationTxtbx);
		
		
		//txtPnl1.add(errorNodeTxtbx);
	//	txtPnl1.add(statusListBx);
		FlowPanel txtPnl2 = new FlowPanel();
		txtPnl2.setStyleName("insert-node-col2");
		txtPnl2.add(allowedProcessDurationTxtbx);
		txtPnl2.add(currentTotalWaitingElementsTxtbx);
		txtPnl2.add(currentTotalInProcessElementsTxtbx);
		
		//txtPnl2.add(minWaitTxtbx);
		//txtPnl2.add(maxWaitTxtbx);
		//txtPnl2.add(aveWaitTxtbx);
		//txtPnl2.add(minProcTxtbx);
		//txtPnl2.add(maxProcTxtbx);
		//txtPnl2.add(aveProcTxtbx);
		//txtPnl2.add(nodeExceptTxtbx);
		txtPnl2.add(endPointsListBx);
		FlowPanel txtPnl3 = new FlowPanel();
		txtPnl3.setStyleName("insert-node-col3");
		
		
		
		
		Button closeBtn = new Button();
		closeBtn.setStyleName("insert-node-txtbx-widget-close-btn");
		closeBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				InsertExceptionNodeTextBoxes.this.removeFromParent();
			}
		});

		FlowPanel btnPnl = new FlowPanel();
		btnPnl.setStyleName("insert-node-x-col");
		btnPnl.add(closeBtn);
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("insert-node-txtbx-widget");
		mainPnl.add(txtPnl1);
		mainPnl.add(txtPnl2);
		//mainPnl.add(txtPnl3);
		mainPnl.add(btnPnl);
		
		initWidget(mainPnl);
	
	}

	

	//public TextBox getErrorNodeTxtbx() {
	//	return errorNodeTxtbx.getTxtBx();
	//}

	//public TextBox getNodeExceptTxtbx() {
	//	return nodeExceptTxtbx.getTxtBx();
	//}

//	public String getSelectedStatus() {
//		return statusListBx.getListBx().getItemText(statusListBx.getListBx().getSelectedIndex());
//	}

	public String getSelectedEndPoint() {
		if (endPointsListBx.getListBx().getItemCount() > 0) {
			return endPointsListBx.getListBx().getItemText(endPointsListBx.getListBx().getSelectedIndex());
		}
		return null;
	}
	
	public class LabeledTxtBx extends Composite {// made public by jm
		
		private TextBox txtBx;
		
		
		public LabeledTxtBx(String lbl){
			
			HTMLPanel label = new HTMLPanel(lbl);
			label.setStyleName("lbl-txtbx-label");
			
			txtBx = new TextBox();
			txtBx.setStyleName("lbl-txtbx-textbox");
			
			FlowPanel mainPnl = new FlowPanel();
			mainPnl.add(label);
			mainPnl.add(txtBx);
			
			initWidget(mainPnl);
			
		}

		public LabeledTxtBx(String lbl,boolean required){
			
			HTMLPanel requiredlabel = new HTMLPanel("*");
			requiredlabel.setStyleName("lbl-txtbx-req-label");
			
			HTMLPanel label = new HTMLPanel(lbl);
			label.setStyleName("lbl-txtbx-label");
			
			txtBx = new TextBox();
			txtBx.setStyleName("lbl-txtbx-textbox");
			
			FlowPanel mainPnl = new FlowPanel();
			mainPnl.add(label);
			if (required)
			mainPnl.add(requiredlabel);
			mainPnl.add(txtBx);
			
			initWidget(mainPnl);
			
		}
		
		public TextBox getTxtBx() {
			return txtBx;
		}
		
	}
	
	 class LabeledListBx extends Composite {
		private ListBox listBx;
		
		public LabeledListBx(String lbl){
			
			HTMLPanel label = new HTMLPanel(lbl);
			label.setStyleName("lbl-listbx-label");
			
			listBx = new ListBox();
			listBx.setStyleName("lbl-listbx-listbox");
			listBx.setVisibleItemCount(1);
			
			FlowPanel mainPnl = new FlowPanel();
			mainPnl.add(label);
			mainPnl.add(listBx);
			
			initWidget(mainPnl);
			
		}

		public ListBox getListBx() {
			return listBx;
		}
		
		private void addItem(String item){
			listBx.addItem(item);
		}
		
	}
	
}
