package com.svi.bpo.client.view.widgets.admin.node;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.client.svc.BpoSvcAsync;
import com.svi.bpo.client.view.widgets.SwitchButton;
import com.svi.bpo.client.view.widgets.admin.node.InsertNodeTextBoxes.LabeledListBx;
import com.svi.bpo.client.view.widgets.admin.node.elements.ViewElementPanel;
import com.svi.bpo.constants.BPOCnstnts;
import com.svi.bpo.constants.Notification;
import com.svi.bpo.graph.obj.ClusterObject;
import com.svi.bpo.objects.ElemDtlObj;
import com.svi.bpo.objects.EndpointObj;
import com.svi.bpo.objects.ExceptionDtlObj;
import com.svi.bpo.objects.NodeDtlObj;


public class AdminNodePanel extends Composite{

	private SwitchButton addNodeBtn;
	private SwitchButton delNodeBtn;
	private SearchWidget searchWdgt;
	private LabeledListBx clusterList;
	private FlowPanel tblPnl;
	
	private InsertNodePanel addNodePnl;
	private ViewElementPanel viewElemPnl;

	public AdminNodePanel() {
		clusterList = new LabeledListBx("Clusters: ");
		addNodePnl = new InsertNodePanel();
		viewElemPnl = new ViewElementPanel();
		
		searchWdgt = new SearchWidget("Search Node");
		addNodeBtn = new SwitchButton("Add Node");
		addNodeBtn.setDisableStyle("admin-node-add-btn-disable");
		addNodeBtn.setEnableStyle("admin-node-add-btn-enable");
		addNodeBtn.disable();
		delNodeBtn = new SwitchButton("Delete Node");
		delNodeBtn.setDisableStyle("admin-node-del-btn-disable");
		delNodeBtn.setEnableStyle("admin-node-del-btn-enable");
		delNodeBtn.disable();

		FlowPanel btnPnl = new FlowPanel();
		btnPnl.setStyleName("admin-node-btn-pnl");
		btnPnl.add(delNodeBtn);
		btnPnl.add(addNodeBtn);
		btnPnl.add(searchWdgt);
		
		clusterList.addItem("*");
		fillListBox();
		btnPnl.add(clusterList);
		clusterList.setStyleName("clusterListStyle");
		clusterList.getListBx().setStyleName("clusterListBoxStyle");
	//	clusterList.setStyleName("clusterListBoxStyle");
		if(CommonObjs.bpoProps.getMode().equalsIgnoreCase("Y")){
			delNodeBtn.setVisible(false);
			addNodeBtn.setVisible(false);
			searchWdgt.setVisible(false);
		}

		tblPnl = new FlowPanel();
		tblPnl.setStyleName("admin-endpt-tbl-pnl");

		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("admin-node-pnl");
		mainPnl.add(btnPnl);
		mainPnl.add(tblPnl);
		mainPnl.add(addNodePnl);
		mainPnl.add(viewElemPnl);

		initWidget(mainPnl);

		addNodeBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(addNodeBtn.isEnable()){
					addNodePnl.hide();
					addNodeBtn.disable();
				} else {
					String chosenCluster = getListBox().getItemText(getListBox().getSelectedIndex());
					
										//addNodePnl.getTxtBxWidgetPnl().add(addNodePnl.getInsertNodeTxtBxsBtn());
					InsertNodeTextBoxes first = new InsertNodeTextBoxes();
					if (chosenCluster.equals("*"))
						first.getClusterTxtbx().setText("");
					else
						first.getClusterTxtbx().setText(chosenCluster);
					addNodePnl.getTxtBxWidgetPnl().clear();
					addNodePnl.getTxtBxWidgetPnl().add(first);
					addNodePnl.getTxtBxWidgetPnl().add(addNodePnl.getInsertNodeTxtBxsBtn());
				

					addNodePnl.show();
					addNodeBtn.enable();
				}
			}
		});
	}
	
	public void addEndpoint(final EndpointNodesWidget widget, List<NodeDtlObj> nodes){
		
	
		
		for(final NodeDtlObj node : nodes){
			
		//	if(listboxContains(node.getCluster()))
		//	{clusterList.addItem(node.getCluster()) ;
		//	}			
			final NodeRecord record = new NodeRecord(node);//adds the node to the node table
			
			
			
			record.setTitle(node.getEndpointId());
			record.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					
					String nodeId = record.getNodeObj().getNodeId();

					if(!widget.getTbl().getMapOfSelectedNodes().containsKey(nodeId)){
						widget.getTbl().getMapOfSelectedNodes().put(nodeId, record.getNodeObj());
						record.addStyleName("node-tbl-row-selected");
					} else {
						widget.getTbl().getMapOfSelectedNodes().remove(nodeId);
						record.removeStyleName("node-tbl-row-selected");
					}

					if(hasSlctdNodes()){
						delNodeBtn.enable();
					} else {
						delNodeBtn.disable();
					}

				}
			});
			
	//		if (clusterList.getListBx().getItemText(clusterList.getListBx().getSelectedIndex()).equals("all"))
	//		{ widget.addNode(record); }
	//		else if (clusterList.getListBx().getItemText(clusterList.getListBx().getSelectedIndex()).equals(node.getCluster()))
	//			{
	//			widget.addNode(record);
	//			}
		
			widget.addNode(record);
			
			record.getViewBtn().addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getElements(node, new AsyncCallback<List<ElemDtlObj>>() {
						
						@Override
						public void onSuccess(List<ElemDtlObj> result) {
							viewElemPnl.setNodeObj(record.getNodeObj());
							//viewElemPnl.setTable(result);
							viewElemPnl.show();
									if(!result.isEmpty()){
								CommonObjs.notify(Notification.SUCCESS, "Load Elements Success!");

								CommonObjs.ElemRecordCount = result.size();
								
								CommonObjs.ElemPageCurrPage = 1;
								
								CommonObjs.ElemPageCount = CommonObjs.ElemRecordCount/CommonObjs.ElemPageLimit;
								
								viewElemPnl.updateLabel();
								viewElemPnl.refresh();
							
							} else {
								CommonObjs.notify(Notification.INFO, "Node is Empty!");
								CommonObjs.ElemRecordCount = result.size();
								
								CommonObjs.ElemPageCurrPage = 0;
								viewElemPnl.updateLabel();
							}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							CommonObjs.notify(Notification.ERROR, "Get Elements Failed");
						}
					});
					
				}
			});
			
		}
		
		tblPnl.add(widget);
	}
	
public void addExceptionEndpoint(final ExceptionEndPointsWidget widget, List<ExceptionDtlObj> nodes){
		
	
		
		for(final ExceptionDtlObj node : nodes){
			
		//	if(listboxContains(node.getCluster()))
		//	{clusterList.addItem(node.getCluster()) ;
		//	}			
			final ExceptionNodeRecord record = new ExceptionNodeRecord(node);//adds the node to the node table
			
			
			
			//record.setTitle(node.getEndpointId());
			record.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					
					String exceptionCode = record.getExceptionNodeObj().getExceptionCode();

					if(!widget.getTbl().getMapOfSelectedNodes().containsKey(exceptionCode)){
						widget.getTbl().getMapOfSelectedNodes().put(exceptionCode, record.getExceptionNodeObj());
						record.addStyleName("node-tbl-row-selected");
					} else {
						widget.getTbl().getMapOfSelectedNodes().remove(exceptionCode);
						record.removeStyleName("node-tbl-row-selected");
					}

					if(hasSlctdNodes()){
						delNodeBtn.enable();
					} else {
						delNodeBtn.disable();
					}

				}
			});
			
	//		if (clusterList.getListBx().getItemText(clusterList.getListBx().getSelectedIndex()).equals("all"))
	//		{ widget.addNode(record); }
	//		else if (clusterList.getListBx().getItemText(clusterList.getListBx().getSelectedIndex()).equals(node.getCluster()))
	//			{
	//			widget.addNode(record);
	//			}
		
			widget.addNode(record);
			
		/*	record.getViewBtn().addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getElements(node, new AsyncCallback<List<ElemDtlObj>>() {
						
						@Override
						public void onSuccess(List<ElemDtlObj> result) {
							viewElemPnl.setNodeObj(record.getNodeObj());
							//viewElemPnl.setTable(result);
							viewElemPnl.show();
									if(!result.isEmpty()){
								CommonObjs.notify(Notification.SUCCESS, "Load Elements Success!");

								CommonObjs.ElemRecordCount = result.size();
								
								CommonObjs.ElemPageCurrPage = 1;
								
								CommonObjs.ElemPageCount = CommonObjs.ElemRecordCount/CommonObjs.ElemPageLimit;
								
								viewElemPnl.updateLabel();
								viewElemPnl.refresh();
							
							} else {
								CommonObjs.notify(Notification.INFO, "Node is Empty!");
								CommonObjs.ElemRecordCount = result.size();
								
								CommonObjs.ElemPageCurrPage = 0;
								viewElemPnl.updateLabel();
							}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							CommonObjs.notify(Notification.ERROR, "Get Elements Failed");
						}
					});
					
				}
			});*/
			
		}
		
		tblPnl.add(widget);
	}
	
	
	
	public boolean hasSlctdNodes(){
		for(int i = 0; i < tblPnl.getWidgetCount(); i++){
			if(tblPnl.getWidget(i) instanceof EndpointNodesWidget){
				EndpointNodesWidget nodeWdgt = (EndpointNodesWidget) tblPnl.getWidget(i);
				if(!nodeWdgt.getTbl().getSelectedNodes().isEmpty()){
					return true;
				}
			}
		}
		return false;
	}

	public List<NodeDtlObj> getSelectedNodes() {
		List<NodeDtlObj> nodes = new ArrayList<NodeDtlObj>();
		for(int i = 0; i < tblPnl.getWidgetCount(); i++){
			if(tblPnl.getWidget(i) instanceof EndpointNodesWidget){
				EndpointNodesWidget nodeWdgt = (EndpointNodesWidget) tblPnl.getWidget(i);
				nodes.addAll(nodeWdgt.getTbl().getSelectedNodes());
			}
		}
		return nodes;
	}
	
	public List<ExceptionDtlObj> getExceptionSelectedNodes() {
		List<ExceptionDtlObj> nodes = new ArrayList<ExceptionDtlObj>();
		for(int i = 0; i < tblPnl.getWidgetCount(); i++){
			if(tblPnl.getWidget(i) instanceof ExceptionEndPointsWidget){
				ExceptionEndPointsWidget nodeWdgt = (ExceptionEndPointsWidget) tblPnl.getWidget(i);
				nodes.addAll(nodeWdgt.getTbl().getSelectedNodes());
			}
		}
		return nodes;
	}
	
//	public void clearSelectedNodes(){
//		for(int i = 0; i < tblPnl.getWidgetCount(); i++){
//			if(tblPnl.getWidget(i) instanceof EndpointNodesWidget){
//				EndpointNodesWidget nodeWdgt = (EndpointNodesWidget) tblPnl.getWidget(i);
//				nodeWdgt.getTbl().clearSelectedNodes();
//			}
//		}
//	}

	public SwitchButton getAddNodeBtn() {
		return addNodeBtn;
	}

	public SwitchButton getDelNodeBtn() {
		return delNodeBtn;
	}

	public InsertNodePanel getAddNodePnl() {
		return addNodePnl;
	}

	public ViewElementPanel getViewElemPnl() {
		return viewElemPnl;
	}
	
	public FlowPanel getTblPnl() {
		return tblPnl;
	}

	public SearchWidget getSearchWdgt() {
		return searchWdgt;
	}

	public void clear(){
		tblPnl.clear();
	}
	
	public LabeledListBx getClusterList(){
		return clusterList;
	}
	
	public ListBox getListBox(){
		return clusterList.getListBx();
	}
	public class SearchWidget extends Composite {

		private TextBox txtbx;
		private Button btn;
		
		public SearchWidget(String value){
			
			txtbx = new TextBox();
			txtbx.getElement().setAttribute("placeholder", value);
			txtbx.setStyleName("srch-wdgt-txtbx");
			txtbx.addKeyDownHandler(new KeyDownHandler() {
				
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if(KeyCodes.KEY_ENTER == (int)event.getNativeKeyCode()){
						btn.click();
					}
				}
			});
			
			btn = new Button();
			btn.setStyleName("srch-wdgt-btn");
			
			FlowPanel mainPnl = new FlowPanel();
			mainPnl.setStyleName("srch-wdgt");
			mainPnl.add(txtbx);
			mainPnl.add(btn);

			initWidget(mainPnl);
		}

		public TextBox getTxtbx() {
			return txtbx;
		}

		public Button getBtn() {
			return btn;
		}
	}
	
	
public class LabeledListBx extends Composite {
		
		private ListBox listBx;
		
		public LabeledListBx(String lbl){
			
			HTMLPanel label = new HTMLPanel(lbl);
			label.setStyleName("clusterList-label");
			
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

public boolean listboxContains(String item){
	int total = clusterList.getListBx().getItemCount();
		for(int i =0 ;i < total;i++){
		if (clusterList.getListBx().getItemText(i).equals(item))
			{
			return false;
			
			}
		}
	
	
	return true;
}

	public void fillListBox(){
	
	
		for (final EndpointObj dtls : CommonObjs.bpoUser.getEndpoints()) {

			if(dtls.isActive()){

				final SearchableEndpointNodesWidget widget = new SearchableEndpointNodesWidget(dtls.getLabel());

				((BpoSvcAsync)CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getCluster(
						dtls.getEndpointId(), new AsyncCallback<String[]>() {

					@Override
					public void onSuccess(String[] result) {
					//	display.getNodeTabPanel().addEndpoint(widget, result);// adds the node
								for (String clusterObj : result) {
									if (listboxContains(clusterObj)) {
										clusterList.addItem(clusterObj);
									}
								}
					}

					@Override
					public void onFailure(Throwable caught) {
					Window.alert("Unable to get clusters");	
					}
				});
			}
		
		
		}
	}


}
