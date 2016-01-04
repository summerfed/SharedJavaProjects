package com.svi.bpo.client.view.widgets.admin.node;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.svi.bpo.objects.NodeDtlObj;

public class InsertNodePanel extends Composite {
	
	private FlowPanel txtBxWidgetPnl;
	

	private Button addNodeBtn;
	

	private Button insertNodeTxtBxsBtn;
	private InsertNodeTextBoxes textBox;
	
	public InsertNodePanel() {

		FlowPanel header = new FlowPanel();
		header.setStyleName("insert-node-hdr");

		// FlowPanel lblPnl = new FlowPanel();
		// lblPnl.setStyleName("add-node-lbl-pnl");
		//
		// for(int i=0; i<placeholder.size();i+=1){
		// HTMLPanel nodeLbl = new HTMLPanel(placeholder.get(i));
		// nodeLbl.setStyleName("add-node-id-col");
		// lblPnl.add(nodeLbl);
		// }

		// HTMLPanel nodeIdLbl = new HTMLPanel("Node ID");
		// nodeIdLbl.setStyleName("add-node-id-col");
		// HTMLPanel nodeDescLbl = new HTMLPanel("Node Description");
		// nodeDescLbl.setStyleName("add-node-desc-col");
		//
		// FlowPanel lblPnl = new FlowPanel();
		// lblPnl.setStyleName("add-node-lbl-pnl");
		// lblPnl.add(nodeIdLbl);
		// lblPnl.add(nodeDescLbl);
		textBox = new InsertNodeTextBoxes();
		txtBxWidgetPnl = new FlowPanel();
		txtBxWidgetPnl.setStyleName("insert-node-txtbx-pnl");
		//txtBxWidgetPnl.add(textBox);

		insertNodeTxtBxsBtn = new Button();
		insertNodeTxtBxsBtn.setStyleName("insert-node-add-txtbx-widget-btn");
		insertNodeTxtBxsBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				txtBxWidgetPnl.add(textBox);
				txtBxWidgetPnl.add(insertNodeTxtBxsBtn);
			}
		});
		txtBxWidgetPnl.add(insertNodeTxtBxsBtn);

		addNodeBtn = new Button("Add");
		addNodeBtn.setStyleName("insert-node-btn");

		FlowPanel addNodeBtnPnl = new FlowPanel();
		addNodeBtnPnl.setStyleName("insert-node-btn-pnl");
		addNodeBtnPnl.add(addNodeBtn);

		FlowPanel body = new FlowPanel();
		body.setStyleName("insert-node-body");
		// body.add(lblPnl);
		body.add(txtBxWidgetPnl);
		body.add(addNodeBtnPnl);

		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("insert-node-pnl");
		mainPnl.add(header);
		mainPnl.add(body);

		initWidget(mainPnl);

		this.getElement().getStyle().setDisplay(Display.NONE);
	}

	public List<NodeDtlObj> getListOfNodesObjToBeAdded() {

		List<NodeDtlObj> nodes = new ArrayList<NodeDtlObj>();

		for (Widget widget : txtBxWidgetPnl) {
			if (widget instanceof InsertNodeTextBoxes) {
				
				InsertNodeTextBoxes txbx = (InsertNodeTextBoxes) widget;
				if (txbx.getNodeIdTxtbx().getText().equals("") || txbx.getNodeNameTxtbx().getText().equals("") )
				{   return null;                                         }
				
				NodeDtlObj nodeObj = new NodeDtlObj(
						txbx.getNodeIdTxtbx().getText(),
						txbx.getNodeNameTxtbx().getText(),
						
						txbx.getClusterTxtbx().getText(),
						txbx.getUnitTxtbx().getText(),
						Double.parseDouble(toInt(txbx.getCostTxtbx().getText())),
						Integer.parseInt(toInt(txbx.getTargetOutTxtbx().getText())),
						Integer.parseInt(toInt(txbx.getAllowedWaitTxtbx().getText())),
						Integer.parseInt(toInt(txbx.getAllowedProcTxtbx().getText())),
						Integer.parseInt(toInt(txbx.getAllowErrorTxtbx().getText())));
				nodeObj.setEndpointId(txbx.getSelectedEndPoint());
				nodes.add(nodeObj);
				
//				NodeDtlObj nodeObj = new NodeDtlObj();
//				nodeObj.setNodeId(((AddNodeTextBoxes)widget).getNodeTxtBx().get(0).getText());
//				nodeObj.setNodeName(((AddNodeTextBoxes)widget).getNodeTxtBx().get(1).getText());
//				nodeObj.setCluster(((AddNodeTextBoxes)widget).getNodeTxtBx().get(2).getText());
//				nodeObj.setStdUnitOfMeasure(((AddNodeTextBoxes)widget).getNodeTxtBx().get(3).getText());
//				nodeObj.setCost(((AddNodeTextBoxes)widget).getNodeTxtBx().get(4).getText());
//				nodeObj.setAllowedMinWait(((AddNodeTextBoxes)widget).getNodeTxtBx().get(5).getText());
//				nodeObj.setAllowedMaxWait(((AddNodeTextBoxes)widget).getNodeTxtBx().get(6).getText());
//				nodeObj.setAllowedAveWait(((AddNodeTextBoxes)widget).getNodeTxtBx().get(7).getText());
//				nodeObj.setAllowedMinProc(((AddNodeTextBoxes)widget).getNodeTxtBx().get(8).getText());
//				nodeObj.setAllowedMaxProc(((AddNodeTextBoxes)widget).getNodeTxtBx().get(9).getText());
//				nodeObj.setAllowedAveProc(((AddNodeTextBoxes)widget).getNodeTxtBx().get(10).getText());
//				nodeObj.setTargetOutput(((AddNodeTextBoxes)widget).getNodeTxtBx().get(11).getText());
//				nodeObj.setAllowedError(((AddNodeTextBoxes)widget).getNodeTxtBx().get(12).getText());
//				nodeObj.setErrorNode(((AddNodeTextBoxes)widget).getNodeTxtBx().get(13).getText());
//				nodeObj.setNodeExceptionOf(((AddNodeTextBoxes)widget).getNodeTxtBx().get(14).getText());
//				nodeObj.setStatus(((AddNodeTextBoxes)widget).getSelectedStatus());
//				nodeObj.setEndpointId(((AddNodeTextBoxes)widget).getSelectedEndpoint());
//				nodes.add(nodeObj);

			}
		}

		return nodes;
	}

	public String toInt (String text){
		if (text.equals("")){
			return "0";
		}
		return text;
	}
	
	public void show() {
		//clearNodes();
		this.getElement().getStyle().setDisplay(Display.BLOCK);
	}

	public void hide() {
		this.getElement().getStyle().setDisplay(Display.NONE);
		clearNodes();
	}

	private void clearNodes() {
		txtBxWidgetPnl.clear();
		txtBxWidgetPnl.add(new InsertNodeTextBoxes());
		txtBxWidgetPnl.add(insertNodeTxtBxsBtn);
	}

	public Button getAddNodeBtn() {
		return addNodeBtn;
	}

	public InsertNodeTextBoxes getTextBox() {
		return textBox;
	}

	public void setTextBox(InsertNodeTextBoxes textBox) {
		this.textBox = textBox;
	}

//	public class AddNodeTextBoxes extends Composite {
//
//		private ArrayList<TextBox> nodeTxtBx = new ArrayList<TextBox>();
//		private ListBox statusListBx;
//		private ListBox endPointsListBx;
//
//		public AddNodeTextBoxes() {
//
//			// nodeIdTxtBx = new TextBox();
//			// nodeIdTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// nodeIdTxtBx.getElement().setAttribute("placeholder", "Node ID");
//			// nodeDescTxtBx = new TextBox();
//			// nodeDescTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// nodeDescTxtBx.getElement().setAttribute("placeholder",
//			// "Node Description");
//			// clusterTxtBx = new TextBox();
//			// clusterTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// clusterTxtBx.getElement().setAttribute("placeholder", "Cluster");
//			// unitTxtBx = new TextBox();
//			// unitTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// unitTxtBx.getElement().setAttribute("placeholder",
//			// "Standard Unit of Measure");
//			// statTxtBx = new TextBox();
//			// statTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// statTxtBx.getElement().setAttribute("placeholder", "Status");
//			// costTxtBx = new TextBox();
//			// costTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// costTxtBx.getElement().setAttribute("placeholder", "Cost");
//			// allowMinWaitTxtBx = new TextBox();
//			// allowMinWaitTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// allowMinWaitTxtBx.getElement().setAttribute("placeholder",
//			// "Allowable Min Waiting Time");
//			// allowMaxWaitTxtBx = new TextBox();
//			// allowMaxWaitTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// allowMaxWaitTxtBx.getElement().setAttribute("placeholder",
//			// "Allowable Max Waiting Time");
//			// allowAveWaitTxtBx = new TextBox();
//			// allowAveWaitTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// allowAveWaitTxtBx.getElement().setAttribute("placeholder",
//			// "Allowable Ave Waiting Time");
//			// allowMinProcTxtBx = new TextBox();
//			// allowMinProcTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// allowMinProcTxtBx.getElement().setAttribute("placeholder",
//			// "Allowable Min Processing Time");
//			// allowMaxProcTxtBx = new TextBox();
//			// allowMaxProcTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// allowMaxProcTxtBx.getElement().setAttribute("placeholder",
//			// "Allowable Max Processing Time");
//			// allowAveProcTxtBx = new TextBox();
//			// allowAveProcTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// allowAveProcTxtBx.getElement().setAttribute("placeholder",
//			// "Allowable Ave Processing Time");
//			// targetOutputTxtBx = new TextBox();
//			// targetOutputTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// targetOutputTxtBx.getElement().setAttribute("placeholder",
//			// "Target Output");
//			// allowErrorTxtBx = new TextBox();
//			// allowErrorTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// allowErrorTxtBx.getElement().setAttribute("placeholder",
//			// "Allowable Error");
//			// errorNodeTxtBx = new TextBox();
//			// errorNodeTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// errorNodeTxtBx.getElement().setAttribute("placeholder",
//			// "Error Node");
//			// nodeExceptTxtBx = new TextBox();
//			// nodeExceptTxtBx.setStyleName("add-node-txtbx-widget-input");
//			// nodeExceptTxtBx.getElement().setAttribute("placeholder",
//			// "Node Exception Of");
//
//			FlowPanel txtPnl1 = new FlowPanel();
//			// txtPnl1.setStyleName("add-node-id-col");
//
//			for (int i = 0; i < placeholder.size(); i++) {
//				TextBox txtBox = new TextBox();
//				//txtBox.setStyleName("add-node-txtbx-widget-input");
//				txtBox.getElement().setAttribute("placeholder",
//						placeholder.get(i));
//				nodeTxtBx.add(txtBox);
//				txtPnl1.add(txtBox);
//			}
//
//			statusListBx = new ListBox();
//			statusListBx.setStyleName("add-node-add-txtbx-widget-list");
//			statusListBx.addItem("OPEN");
//			statusListBx.addItem("CLOSE");
//			statusListBx.setVisibleItemCount(1);
//
//			endPointsListBx = new ListBox();
//			endPointsListBx.setStyleName("add-node-add-txtbx-widget-list");
//			endPointsListBx.setVisibleItemCount(1);
//
//			for (EndpointObj obj : CommonObjs.bpoUser.getEndpoints()) {
//				if (obj.isActive()) {
//					endPointsListBx.addItem(obj.getEndpointId());
//				}
//			}
//
//			// FlowPanel txtPnl1 = new FlowPanel();
//			// txtPnl1.setStyleName("add-node-id-col");
//			// txtPnl1.add(nodeIdTxtBx);
//			// FlowPanel txtPnl2 = new FlowPanel();
//			// txtPnl2.setStyleName("add-node-desc-col");
//			// txtPnl2.add(nodeDescTxtBx);
//			FlowPanel txtPnl2 = new FlowPanel();
//			txtPnl2.setStyleName("add-node-endpt-col");
//			txtPnl2.add(statusListBx);
//			txtPnl2.add(endPointsListBx);
//
//			Button closeBtn = new Button();
//			closeBtn.setStyleName("add-node-txtbx-widget-close-btn");
//			closeBtn.addClickHandler(new ClickHandler() {
//				@Override
//				public void onClick(ClickEvent event) {
//					AddNodeTextBoxes.this.removeFromParent();
//				}
//			});
//
//			FlowPanel btnPnl = new FlowPanel();
//			btnPnl.setStyleName("add-node-x-col");
//			btnPnl.add(closeBtn);
//
//			FlowPanel mainPnl = new FlowPanel();
//			mainPnl.setStyleName("add-node-txtbx-widget");
//			mainPnl.add(txtPnl1);
//			mainPnl.add(txtPnl2);
//			mainPnl.add(btnPnl);
//
//			initWidget(mainPnl);
//
//		}
//		
//		public ArrayList<TextBox> getNodeTxtBx() {
//			return nodeTxtBx;
//		}
//		
//		public String getSelectedStatus() {
//			System.out.println(statusListBx.getItemText(statusListBx.getSelectedIndex()));
//			return statusListBx.getItemText(statusListBx.getSelectedIndex());
//		}
//		
//		public String getSelectedEndpoint() {
//			if (endPointsListBx.getItemCount() > 0) {
//				return endPointsListBx.getItemText(endPointsListBx
//						.getSelectedIndex());
//			}
//			return null;
//		}
//
//	}
	public FlowPanel getTxtBxWidgetPnl() {
		return txtBxWidgetPnl;
	}

	public void setTxtBxWidgetPnl(FlowPanel txtBxWidgetPnl) {
		this.txtBxWidgetPnl = txtBxWidgetPnl;
	}
	public Button getInsertNodeTxtBxsBtn() {
		return insertNodeTxtBxsBtn;
	}

	public void setInsertNodeTxtBxsBtn(Button insertNodeTxtBxsBtn) {
		this.insertNodeTxtBxsBtn = insertNodeTxtBxsBtn;
	}

	public void setAddNodeBtn(Button addNodeBtn) {
		this.addNodeBtn = addNodeBtn;
	}
}
