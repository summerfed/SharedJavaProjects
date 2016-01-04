package com.svi.bpo.client.view.widgets.admin.node.elements;

import java.util.List;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.client.svc.BpoSvcAsync;
import com.svi.bpo.client.view.widgets.SimplePagingWidget;
import com.svi.bpo.client.view.widgets.SwitchButton;
import com.svi.bpo.constants.BPOCnstnts;
import com.svi.bpo.constants.Notification;
import com.svi.bpo.objects.ElemDtlObj;
import com.svi.bpo.objects.NodeDtlObj;




public class ViewElementPanel extends Composite {

	private NodeDtlObj nodeObj;
	
	private Header header;
	private ElementTable tbl;
	private NodeDetailsBar bar;
	private ButtonPanel btnPnl;
	private SimplePagingWidget spw;

	private InsertElementPanel insertElemPnl;
	private ChangePriorityLvlPanel chngePriLvlPnl;
	private TransferElementPanel trnsfrElmPnl;
	private ChangeWorkerPanel chngeWrkrPnl;

	boolean updateElemCount = true;
	public ViewElementPanel(){


		insertElemPnl = new InsertElementPanel();
		chngePriLvlPnl = new ChangePriorityLvlPanel();
		trnsfrElmPnl = new TransferElementPanel();
		chngeWrkrPnl = new ChangeWorkerPanel();

		header = new Header();
		tbl = new ElementTable();
		spw = new SimplePagingWidget();
		bar = new NodeDetailsBar();
		
		FlowPanel body = new FlowPanel();
		body.setStyleName("view-elem-body");
		body.add(tbl);
	
	
		//updateLabel();
		
		body.add(bar);
		//body.add(spw);
		//setPager(spw);
		btnPnl = new ButtonPanel();
		//btnPnl.
		
		btnPnl.getInsertPnlBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(btnPnl.getInsertPnlBtn().isEnable()){
					insertElemPnl.hide();
					btnPnl.getInsertPnlBtn().disable();
					InsertElementTextBoxes.nodeNameTxtbx.getTxtBx().setText(nodeObj.getNodeId());//added by jm

				} else {
					insertElemPnl.show();
					InsertElementTextBoxes.nodeNameTxtbx.getTxtBx().setText(nodeObj.getNodeId());//added by jm

					btnPnl.getInsertPnlBtn().enable();
				}
			}
		});
		
		btnPnl.getChngePriLvlBtn().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(btnPnl.getChngePriLvlBtn().isEnable()){
					chngePriLvlPnl.show();
				}
			}
		});
		
		btnPnl.getChngeWrkrBtn().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(btnPnl.getChngeWrkrBtn().isEnable()){
					chngeWrkrPnl.show();
				}
			}
		});
		
		btnPnl.getRefreshBtn().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("Refresh button is clicked");
				((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getElements(getNodeObj(),CommonObjs.ElemPageCurrPage,CommonObjs.ElemPageLimit, new AsyncCallback<List<ElemDtlObj>>() {
					
					@Override
					public void onSuccess(List<ElemDtlObj> result) {
						System.out.println("result.size: " + result.size());
					System.out.println("getNodeObj: " + getNodeObj().getNodeId());
						setNodeObj(getNodeObj());
						setTable(result);
						show();
							
						CommonObjs.ElemPageCount= CommonObjs.ElemRecordCount/CommonObjs.ElemPageLimit;
			
						if(!result.isEmpty()){
							CommonObjs.notify(Notification.SUCCESS, "Load Elements Success!");
						} else {
							CommonObjs.notify(Notification.INFO, "No elements in the node selected!");
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						CommonObjs.notify(Notification.ERROR, "Get Elements Failed");
					}
				});
				
				((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getNodeWithElements(getNodeObj(), new AsyncCallback<NodeDtlObj>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						CommonObjs.notify(Notification.ERROR, "Get Nodes Failed");
						
					}

					@Override
					public void onSuccess(NodeDtlObj result) {
						// TODO Auto-generated method stub
						setNodeObj(result);
						
						show();
						
						
						
					}
				
				});
			}
		});
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("view-elem-pnl");
		mainPnl.add(header);
		setPager(spw);
	//	body.add(spw);
		mainPnl.add(body);
		mainPnl.add(spw);
		
		spw.setStyleName("pager");
		mainPnl.add(btnPnl);
		mainPnl.add(insertElemPnl);
		mainPnl.add(chngePriLvlPnl);
		mainPnl.add(trnsfrElmPnl);
		mainPnl.add(chngeWrkrPnl);

		if(CommonObjs.bpoProps.getMode().equalsIgnoreCase("Y")){
			insertElemPnl.setVisible(false);
			chngePriLvlPnl.setVisible(false);
			trnsfrElmPnl.setVisible(false);
			chngeWrkrPnl.setVisible(false);
		}
		
		initWidget(mainPnl);

		this.getElement().getStyle().setDisplay(Display.NONE);
	}

	public void setNodeObj(NodeDtlObj nodeObj) {
		this.nodeObj = nodeObj;
		header.setText("Node ID: " + nodeObj.getNodeId());// node title for the header
		
			this.bar.getNoOfProcess().setText(nodeObj.getElmtsInprogress());
		this.bar.getNoOfWaiting().setText(nodeObj.getElmtsWaiting());
		this.bar.getCompleteCnt().setText(nodeObj.getCumElemCount());
//		this.bar.getCompleteCnt().setText(nodeObj.getElmtsCompleted());
		this.bar.getOutputCnt().setText(nodeObj.getCumOutput()+" "+nodeObj.getStdUnitOfMeasure());
		this.bar.getErrorCnt().setText(nodeObj.getCumError());
	}

	public void setTable(List<ElemDtlObj> elems){
		tbl.clear();
	  //int counter = 0;
	  //int pagelimit = CommonObjs.ElemPageLimit;
	  //int start = (CommonObjs.ElemPageCurrPage - 1) * CommonObjs.ElemPageLimit;
		for(final ElemDtlObj elem : elems){
			addToTable(elem);		
	//   counter++;
		}
	}

	public void setTable(ElemDtlObj elem){
		tbl.clear();
		addToTable(elem);
	}

	public void addToTable(final ElemDtlObj elem){

		final ElementRecord record = new ElementRecord(elem);
		/*ADDED*/
		 
		if(nodeObj.getAllowedMaxWait() == null){
			nodeObj.setAllowedMaxWait("0");
		}
		if(nodeObj.getAllowedMaxProc() == null){
			nodeObj.setAllowedMaxProc("0");
		}
		
		
			if(elem.getStatus().equalsIgnoreCase("W")){
			if(elem.getWaitDuration() > Integer.parseInt(nodeObj.getAllowedMaxWait())){
				record.getElement().addClassName("elem-tbl-row-exceed");
			}
		}else if(elem.getStatus().equalsIgnoreCase("P")){
			if(elem.getProcessDuration() > Integer.parseInt(nodeObj.getAllowedMaxProc())){
				record.getElement().addClassName("elem-tbl-row-exceed");
			}
		}
		tbl.add(record);

		record.addDomHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String elemId = record.getElemObj().getElementId();

				if(!tbl.getMapOfSelectedElements().containsKey(elemId)){
					tbl.getMapOfSelectedElements().put(elemId, elem);
					record.addStyleName("elem-tbl-row-selected");
				} else {
					tbl.getMapOfSelectedElements().remove(elemId);
					record.removeStyleName("elem-tbl-row-selected");
				}
System.out.println("tbl.getElementsSelected(): " + tbl.getElementsSelected().size());
				System.out.println(tbl.getElementsSelected());

				if(hasSlctdNodes()){
					btnPnl.getDelBtn().enable();
					btnPnl.getChngePriLvlBtn().enable();
					btnPnl.getTrnsfrElmntBtn().enable();
					btnPnl.getChngeWrkrBtn().enable();
				} else {
					btnPnl.getDelBtn().disable();
					btnPnl.getChngePriLvlBtn().disable();
					btnPnl.getTrnsfrElmntBtn().disable();
					btnPnl.getChngeWrkrBtn().disable();
				}
			}
		}, ClickEvent.getType());

		updateLabel();}

	public void show(){
		this.getElement().getStyle().setDisplay(Display.BLOCK);
		btnPnl.getDelBtn().disable();
		btnPnl.getChngePriLvlBtn().disable();
		btnPnl.getTrnsfrElmntBtn().disable();
		btnPnl.getChngeWrkrBtn().disable();
	}
	
	public void close(){
		tbl.clearSelectedElements();
		hide();
	}
	public void hide(){
		this.getElement().getStyle().setDisplay(Display.NONE);
	}
	public NodeDtlObj getNodeObj() {
		return nodeObj;
	}
	public SwitchButton getInsertPnlBtn() {
		return btnPnl.getInsertPnlBtn();
	}
	public InsertElementPanel getInsertElemPnl() {
		return insertElemPnl;
	}
	public boolean hasSlctdNodes(){
		return !tbl.getElementsSelected().isEmpty();
	}
	public SwitchButton getDelBtn() {
		return btnPnl.getDelBtn();
	}
	public SwitchButton getChngePriLvlBtn(){
		return btnPnl.getChngePriLvlBtn();
	}
	public SwitchButton getTrnsfrElmntBtn(){
		return btnPnl.getTrnsfrElmntBtn();
	}
	public SwitchButton getChngeWrkrBtn() {
		return btnPnl.getChngeWrkrBtn();
	}
	public ElementTable getTbl() {
		return tbl;
	}
	public ChangePriorityLvlPanel getChangePriorityLvlPanel(){
		return this.chngePriLvlPnl;
	}
	public TransferElementPanel getTrnsfrElmPnl(){
		return trnsfrElmPnl;
	}
	public ChangeWorkerPanel getChangeWorkerPanel(){
		return this.chngeWrkrPnl;
	}
	public Button getCloseBtn() {
		return header.getCloseBtn();
	}
	
	public class Header extends Composite{

		private Label label;
		private Label total;
		private Button closeBtn;
		
		public Header() {

			closeBtn = new Button();
			closeBtn.setStyleName("view-elem-hdr-close-btn");

			label = new Label();
			label.setStyleName("view-elem-hdr-lbl");
			
			total = new Label();
			total.setStyleName("view-elem-hdr-total");

			FlowPanel mainPnl = new FlowPanel();
			mainPnl.setStyleName("view-elem-hdr");
			mainPnl.add(label);
			mainPnl.add(total);
			mainPnl.add(closeBtn);
			
			initWidget(mainPnl);
		}

		public void setText(String text) {
			this.label.setText(text);
		}
		
		public void setTotal(String text) {
			this.total.setText(text);
		}

		public Button getCloseBtn() {
			return closeBtn;
		}
		
	}
	
	public class NodeDetailsBar extends Composite {
		
		private Label noOfWaiting = new Label();
		private Label noOfProcess = new Label();
		private Label completeCnt = new Label();
		private Label outputCnt = new Label();
		private Label errorCnt = new Label();
		
		public NodeDetailsBar() {
			
			FlowPanel mainPnl = new FlowPanel();
			mainPnl.setStyleName("node-dtl-pnl");
			
			noOfWaiting = new Label();
			noOfProcess = new Label();
			completeCnt = new Label();
			outputCnt = new Label();
			errorCnt = new Label();
			
			HTMLPanel waitLbl = new HTMLPanel("WAITING");
			HTMLPanel procLbl = new HTMLPanel("PROCESSING");
			HTMLPanel ccLbl = new HTMLPanel("Total Element Count");
			HTMLPanel ocLbl = new HTMLPanel("Total Output");
			HTMLPanel ecLbl = new HTMLPanel("Total Errors");
			
			FlowPanel topPnl =  new FlowPanel();
			topPnl.add(noOfWaiting);
			topPnl.add(waitLbl);
			topPnl.add(noOfProcess);
			topPnl.add(procLbl);
			
			FlowPanel btmPnl = new FlowPanel();
			btmPnl.add(completeCnt);
			btmPnl.add(ccLbl);
			btmPnl.add(outputCnt);
			btmPnl.add(ocLbl);
			btmPnl.add(errorCnt);
			btmPnl.add(ecLbl);
			
			mainPnl.add(topPnl);
			mainPnl.add(btmPnl);
			
			initWidget(mainPnl);
		}

		public Label getNoOfWaiting() {
			return noOfWaiting;
		}

		public Label getNoOfProcess() {
			return noOfProcess;
		}

		public Label getCompleteCnt() {
			return completeCnt;
		}

		public Label getOutputCnt() {
			return outputCnt;
		}

		public Label getErrorCnt() {
			return errorCnt;
		}
		
	}

	public class ButtonPanel extends Composite {
		
		private SwitchButton insertPnlBtn;
		private SwitchButton delBtn;
		private SwitchButton chngePriLvlBtn;
		private SwitchButton trnsfrElmntBtn;
		private SwitchButton chngeWrkrBtn;
		private SwitchButton refreshBtn;
		
		public ButtonPanel() {

			insertPnlBtn = new SwitchButton("Insert");
			insertPnlBtn.setEnableStyle("view-elem-add-btn-enable");
			insertPnlBtn.setDisableStyle("view-elem-add-btn-disable");
			insertPnlBtn.disable();
			delBtn = new SwitchButton("Delete");
			delBtn.setEnableStyle("view-elem-del-btn-enable");
			delBtn.setDisableStyle("view-elem-del-btn-disable");
			delBtn.disable();
			chngePriLvlBtn = new SwitchButton("Change Priority");
			chngePriLvlBtn.setDisableStyle("view-elem-chngePri-btn-disable");
			chngePriLvlBtn.setEnableStyle("view-elem-chngePri-btn-enable");
			chngePriLvlBtn.disable();
			trnsfrElmntBtn = new SwitchButton("Transfer");
			trnsfrElmntBtn.setEnableStyle("view-elem-transfr-btn-enable");
			trnsfrElmntBtn.setDisableStyle("view-elem-transfr-btn-disable");
			trnsfrElmntBtn.disable();
			chngeWrkrBtn =  new SwitchButton("Shift Worker");
			chngeWrkrBtn.setEnableStyle("view-elem-chnge-wrkr-btn-enable");
			chngeWrkrBtn.setDisableStyle("view-elem-chnge-wrkr-btn-disable");
			chngeWrkrBtn.disable();
			refreshBtn = new SwitchButton("Refresh");
			refreshBtn.setEnableStyle("view-elem-refresh-btn-enable");
			refreshBtn.setDisableStyle("view-elem-refresh-btn-disable");
			refreshBtn.enable();

			FlowPanel mainPnl = new FlowPanel();
			mainPnl.setStyleName("view-elem-btn-pnl");
			mainPnl.add(refreshBtn);
			mainPnl.add(insertPnlBtn);
			mainPnl.add(delBtn);
			mainPnl.add(chngePriLvlBtn);
			mainPnl.add(trnsfrElmntBtn);
			mainPnl.add(chngeWrkrBtn);
			
			
			if(CommonObjs.bpoProps.getMode().equalsIgnoreCase("Y")){
				insertPnlBtn.setVisible(false);
				delBtn.setVisible(false);
				chngePriLvlBtn.setVisible(false);
				trnsfrElmntBtn.setVisible(false);
				chngeWrkrBtn.setVisible(false);
			}
			
			initWidget(mainPnl);
		}

		public SwitchButton getInsertPnlBtn() {
			return insertPnlBtn;
		}
		public SwitchButton getDelBtn() {
			return delBtn;
		}
		public SwitchButton getChngePriLvlBtn() {
			return chngePriLvlBtn;
		}
		public SwitchButton getTrnsfrElmntBtn() {
			return trnsfrElmntBtn;
		}
		public SwitchButton getChngeWrkrBtn() {
			return chngeWrkrBtn;
		}
		public SwitchButton getRefreshBtn() {
			return refreshBtn;
		}
	}
	
	private void setPager(final SimplePagingWidget widget) {

		//int pageCount = CommonObjs.ElemPageCount;
	
		widget.getNextBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
					
				updateRecordCount();
				updateElemCount = false;
				
				if ((((CommonObjs.ElemPageCurrPage-1)*10+1) + 9) <= CommonObjs.ElemRecordCount) {
			//		_SEARCHING_SHOW_OK();
				
					CommonObjs.ElemPageCurrPage++;
			//		srchData(null, CommonObjAndHistoryRep.currentSrchKeywrdLst);
				}
				refresh();
				updateLabel();
			}
		});

		widget.getPrevBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
			
				updateRecordCount();
				updateElemCount = false;
				if (CommonObjs.ElemPageCurrPage > 1) {
				//	_SEARCHING_SHOW_OK();
				//	Window.alert("getPrevBtn");
					CommonObjs.ElemPageCurrPage--;
				//	srchData(null, CommonObjAndHistoryRep.currentSrchKeywrdLst);
				}
				refresh();
				
				
				updateLabel();
			}
		});

		widget.getLastBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				updateRecordCount();
				updateElemCount = false;
				int lastRecordOnPage = ((CommonObjs.ElemPageCurrPage-1)*10+1) + 9;
				if (lastRecordOnPage != CommonObjs.ElemRecordCount ) {
				//	_SEARCHING_SHOW_OK();
					
					if (CommonObjs.ElemRecordCount % CommonObjs.ElemPageLimit == 0) {
						CommonObjs.ElemPageCurrPage = (CommonObjs.ElemRecordCount / CommonObjs.ElemPageLimit);
					} else {
						CommonObjs.ElemPageCurrPage = (CommonObjs.ElemRecordCount / CommonObjs.ElemPageLimit) + 1 ;
					}
		//			srchData(null, CommonObjAndHistoryRep.currentSrchKeywrdLst);
				}
				//Window.alert("CommonObjs.ElemPageCurrPage is " + CommonObjs.ElemPageCurrPage);
				refresh();
				updateLabel();
			}
		});

		widget.getFirstBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				updateRecordCount();
				updateElemCount = false;
				//Window.alert("getFirstBtn");
				if (CommonObjs.ElemPageCurrPage > 1) {
					//_SEARCHING_SHOW_OK();
					CommonObjs.ElemPageCurrPage = 1;
					//srchData(null, CommonObjAndHistoryRep.currentSrchKeywrdLst);
				}
				refresh();
				updateLabel();
			}
		});
	}
	
	public void updateLabel(){
		spw.enableLeft();
		spw.enableRight();
		if (CommonObjs.ElemPageCurrPage <= 0)
			CommonObjs.ElemPageCurrPage = 1;
			
		CommonObjs.ElemPageCount = CommonObjs.ElemRecordCount/CommonObjs.ElemPageLimit;
		int recordCount = CommonObjs.ElemRecordCount;
		int startPage = ((CommonObjs.ElemPageCurrPage-1)*10+1);
		int lastRecordOnPage = ((CommonObjs.ElemPageCurrPage-1)*10+1) + 9;
		if ( recordCount == 0){
			lastRecordOnPage = recordCount;
			startPage = 0;
		}
		else if (lastRecordOnPage > recordCount){
			lastRecordOnPage = recordCount;
			
		}
		
		if (startPage <= 1){
			spw.disableLeft();
			
		}
		if (lastRecordOnPage == recordCount){
			spw.disableRight();
			
		}
		spw.setText(recordCount,  startPage, lastRecordOnPage);

		
	}
	
	public void refresh(){
		
		if (updateElemCount){
			updateRecordCount();
		}
		
		((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getElements(getNodeObj(),CommonObjs.ElemPageCurrPage,CommonObjs.ElemPageLimit, new AsyncCallback<List<ElemDtlObj>>() {
			
			@Override
			public void onSuccess(List<ElemDtlObj> result) {
				System.out.println("result.size: " + result.size());
			System.out.println("getNodeObj: " + getNodeObj().getNodeId());
				setNodeObj(getNodeObj());
				setTable(result);
				show();
				
				//CommonObjs.ElemRecordCount = result.size();
				
				//CommonObjs.ElemPageCurrPage = 1;
				
				CommonObjs.ElemPageCount= CommonObjs.ElemRecordCount/CommonObjs.ElemPageLimit;
	
				if(!result.isEmpty()){
					//CommonObjs.notify(Notification.SUCCESS, "Load Elements Success!");
				} else {
					CommonObjs.notify(Notification.INFO, "Node is Empty!");
				}
				updateLabel();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				CommonObjs.notify(Notification.ERROR, "Get Elements Failed");
			}
		});
		updateElemCount = true;
		getTbl().getElementsSelected().clear();
	}
	
	public void updateRecordCount(){
		
((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getElements(getNodeObj(), new AsyncCallback<List<ElemDtlObj>>() {
			
			@Override
			public void onSuccess(List<ElemDtlObj> result) {
				System.out.println(" updateReordCount result.size: " + result.size());
			System.out.println("updateReordCount getNodeObj: " + getNodeObj().getNodeId());
				
				CommonObjs.ElemRecordCount = result.size();
				
	
				if(!result.isEmpty()){
					//CommonObjs.notify(Notification.SUCCESS, "Load Elements Success!");
				} else {
					CommonObjs.notify(Notification.INFO, "Node is Empty!");
				}
				updateLabel();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				CommonObjs.notify(Notification.ERROR, "Update Record Count failed");
			}
		});
		
	}
	
	
}
