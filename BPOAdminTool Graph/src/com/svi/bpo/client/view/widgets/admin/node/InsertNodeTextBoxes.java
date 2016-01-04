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

public class InsertNodeTextBoxes extends Composite{
	
	
	private LabeledTxtBx nodeIdTxtbx;// stay
	//private LabeledTxtBx nodeNameTxtbx;// remove?
	
	private LabeledTxtBx clusterTxtbx;// stay
	private LabeledTxtBx unitTxtbx;// stay
	private LabeledTxtBx costTxtbx;// stay
	//private LabeledTxtBx minWaitTxtbx;//remove 
	//private LabeledTxtBx maxWaitTxtbx;//remove 
	//private LabeledTxtBx aveWaitTxtbx;//remove 
	//private LabeledTxtBx minProcTxtbx;//remove 
	//private LabeledTxtBx maxProcTxtbx;//remove 
	//private LabeledTxtBx aveProcTxtbx;//remove 
	private LabeledTxtBx targetOutTxtbx;// change to TargetOutput count
	private LabeledTxtBx allowErrorTxtbx;//stay
	//private LabeledTxtBx errorNodeTxtbx;
	//private LabeledTxtBx nodeExceptTxtbx;
	//private LabeledListBx statusListBx;
	private LabeledListBx endPointsListBx;
	
	private LabeledTxtBx allowedWaitTxtbx;//add
	private  LabeledTxtBx allowedProcTxtbx;//add
	private LabeledTxtBx nodeNameTxtbx;//add
	public InsertNodeTextBoxes(){
		
		nodeIdTxtbx = new LabeledTxtBx("Node ID:",true);
		nodeIdTxtbx.setStyleName("insert-node-txtbx-widget-input");
		nodeNameTxtbx = new LabeledTxtBx("Node Name:",true);
		nodeNameTxtbx.setStyleName("insert-node-txtbx-widget-input");
		clusterTxtbx = new LabeledTxtBx("Cluster:");
		clusterTxtbx.setStyleName("insert-node-txtbx-widget-input",true);
		unitTxtbx = new LabeledTxtBx("Standard Unit of Measure:");
		unitTxtbx.setStyleName("insert-node-txtbx-widget-input");
		costTxtbx = new LabeledTxtBx("Cost:");
		costTxtbx.setStyleName("insert-node-txtbx-widget-input");
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
		targetOutTxtbx = new LabeledTxtBx("Target Output:");
		targetOutTxtbx.setStyleName("insert-node-txtbx-widget-input");
		allowErrorTxtbx = new LabeledTxtBx("Allowable Error:");
		allowErrorTxtbx.setStyleName("insert-node-txtbx-widget-input");
		allowedWaitTxtbx = new LabeledTxtBx("Allowable Wait:");
		allowedWaitTxtbx.setStyleName("insert-node-txtbx-widget-input");
		allowedProcTxtbx = new LabeledTxtBx("Allowable Proc:");
		allowedProcTxtbx.setStyleName("insert-node-txtbx-widget-input");
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
		txtPnl1.add(nodeIdTxtbx);
		txtPnl1.add(nodeNameTxtbx);
		txtPnl1.add(clusterTxtbx);
		txtPnl1.add(unitTxtbx);
		txtPnl1.add(costTxtbx);
		
		//txtPnl1.add(errorNodeTxtbx);
	//	txtPnl1.add(statusListBx);
		FlowPanel txtPnl2 = new FlowPanel();
		txtPnl2.setStyleName("insert-node-col2");
		txtPnl2.add(targetOutTxtbx);
		txtPnl2.add(allowErrorTxtbx);
		txtPnl2.add(allowedWaitTxtbx);
		txtPnl2.add(allowedProcTxtbx);
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
				InsertNodeTextBoxes.this.removeFromParent();
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

	public TextBox getClusterTxtbx() {
		return clusterTxtbx.getTxtBx();
	}

	public void setClusterTxtbx(LabeledTxtBx clusterTxtbx) {
		this.clusterTxtbx = clusterTxtbx;
	}

	public TextBox getAllowedWaitTxtbx() {
		return allowedWaitTxtbx.getTxtBx();
	}

	public void setAllowedWaitTxtbx(LabeledTxtBx allowedWaitTxtbx) {
		this.allowedWaitTxtbx = allowedWaitTxtbx;
	}

	public TextBox getAllowedProcTxtbx() {
		return allowedProcTxtbx.getTxtBx();
	}

	public void setAllowedProcTxtbx(LabeledTxtBx allowedProcTxtbx) {
		this.allowedProcTxtbx = allowedProcTxtbx;
	}

	public TextBox getNodeNameTxtbx() {
		return nodeNameTxtbx.getTxtBx();
	}

	
	public void setNodeNameTxtbx(LabeledTxtBx nodeNameTxtbx) {
		this.nodeNameTxtbx = nodeNameTxtbx;
	}

	public void setNodeIdTxtbx(LabeledTxtBx nodeIdTxtbx) {
		this.nodeIdTxtbx = nodeIdTxtbx;
	}

	public void setUnitTxtbx(LabeledTxtBx unitTxtbx) {
		this.unitTxtbx = unitTxtbx;
	}

	public void setCostTxtbx(LabeledTxtBx costTxtbx) {
		this.costTxtbx = costTxtbx;
	}

	public void setTargetOutTxtbx(LabeledTxtBx targetOutTxtbx) {
		this.targetOutTxtbx = targetOutTxtbx;
	}

	public void setAllowErrorTxtbx(LabeledTxtBx allowErrorTxtbx) {
		this.allowErrorTxtbx = allowErrorTxtbx;
	}

	public TextBox getNodeIdTxtbx() {
		return nodeIdTxtbx.getTxtBx();
	}

	//public TextBox getNodeDescTxtbx() {
//		return nodeDescTxtbx.getTxtBx();
//	}

//	public TextBox getClusterTxtbx() {
//		return clusterTxtbx.getTxtBx();
//	}

	public TextBox getUnitTxtbx() {
		return unitTxtbx.getTxtBx();
	}

	public TextBox getCostTxtbx() {
		return costTxtbx.getTxtBx();
	}

	//public TextBox getMinWaitTxtbx() {
	//	return minWaitTxtbx.getTxtBx();
	//}

	//public TextBox getMaxWaitTxtbx() {
	//	return maxWaitTxtbx.getTxtBx();
	//}

	//public TextBox getAveWaitTxtbx() {
	//	return aveWaitTxtbx.getTxtBx();
	//}

	//public TextBox getMinProcTxtbx() {
	//	return minProcTxtbx.getTxtBx();
	//}

	//public TextBox getMaxProcTxtbx() {
	//	return maxProcTxtbx.getTxtBx();
	//}

	//public TextBox getAveProcTxtbx() {
	//	return aveProcTxtbx.getTxtBx();
	//}

	public TextBox getTargetOutTxtbx() {
		return targetOutTxtbx.getTxtBx();
	}

	public TextBox getAllowErrorTxtbx() {
		return allowErrorTxtbx.getTxtBx();
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
