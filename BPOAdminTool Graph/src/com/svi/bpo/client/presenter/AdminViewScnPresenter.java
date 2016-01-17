package com.svi.bpo.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.client.event.LginEvent;
import com.svi.bpo.client.svc.BpoSvcAsync;
import com.svi.bpo.client.view.widgets.BannerPanel;
import com.svi.bpo.client.view.widgets.EndpointListPanel;
import com.svi.bpo.client.view.widgets.admin.batch.AdminBatchUploadPanel;
import com.svi.bpo.client.view.widgets.admin.batch.BatchUploadWidget;
import com.svi.bpo.client.view.widgets.admin.batch.ResultWidget;
import com.svi.bpo.client.view.widgets.admin.node.AdminNodePanel;
import com.svi.bpo.client.view.widgets.admin.node.EndpointNodesWidget;
import com.svi.bpo.client.view.widgets.admin.node.ExceptionNodePanel;
import com.svi.bpo.client.view.widgets.admin.node.ExceptionSearchableEndpointNodesWidget;
import com.svi.bpo.client.view.widgets.admin.node.InsertExceptionNodePanel;
import com.svi.bpo.client.view.widgets.admin.node.InsertNodePanel;
import com.svi.bpo.client.view.widgets.admin.node.InsertNodeTextBoxes;
import com.svi.bpo.client.view.widgets.admin.node.SearchableEndpointNodesWidget;
import com.svi.bpo.client.view.widgets.admin.node.elements.ChangePriorityLvlPanel;
import com.svi.bpo.client.view.widgets.admin.node.elements.ChangeWorkerPanel;
import com.svi.bpo.client.view.widgets.admin.node.elements.InsertElementPanel;
import com.svi.bpo.client.view.widgets.admin.node.elements.TransferElementPanel;
import com.svi.bpo.client.view.widgets.admin.node.elements.ViewElementPanel;
import com.svi.bpo.constants.BPOCnstnts;
import com.svi.bpo.constants.Notification;
import com.svi.bpo.objects.ElemDtlObj;
import com.svi.bpo.objects.EndpointObj;
import com.svi.bpo.objects.ExceptionDtlObj;
import com.svi.bpo.objects.NodeDtlObj;
import com.svi.bpo.objects.ResultObj;
import com.svi.bpo.objects.UpldRsltObj;
import com.svi.ssocookie.client.CookieUtil;

/**
 * 
 * Admin View Screen Presenter
 * 
 * @author aenriquez
 * @developer ecruz
 * @developer edecano
 * @developer mrongcales
 */
public class AdminViewScnPresenter implements Presenter {

	public interface Display {

		Widget asWidget();

		BannerPanel getBannerPanel();
		FlowPanel getViewPanel();

		AdminNodePanel getNodeTabPanel();
			InsertNodePanel getAddNodePanel();
			ViewElementPanel getViewElemPanel();
			InsertElementPanel getInsertElemPanel();

		AdminBatchUploadPanel getBatchUpldTabPnl();
			BatchUploadWidget getNodeUpldWidget();
			BatchUploadWidget getElemUpldWidget();
			ResultWidget getResultWdgt();
			
		ExceptionNodePanel getExceptionTabPnl();
			InsertExceptionNodePanel getAddExceptionNodePanel();
		EndpointListPanel getEndpointPnl();
		List<String> getEndpoints();
		ChangePriorityLvlPanel getChangePriorityLvlPanel();
		ChangeWorkerPanel getChangeWorkerPanel();
		TransferElementPanel getTransferElmntPnl();

		void showLoadingScn();
		void hideLoadingScn();
		void notify(Notification notification, String text);

	}

	private Display display;
	private HasWidgets container;

	public AdminViewScnPresenter(Display view) {
		this.display = view;
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;	
		bind();
		setAdminTable();
	//	display.getExceptionTabPnl().getClusterList().setVisible(false);
	//	display.getExceptionTabPnl().getAddNodeBtn().setVisible(false);
	//	display.getExceptionTabPnl().getDelNodeBtn().setVisible(false);
	//	display.getExceptionTabPnl().getSearchWdgt().setVisible(false);
		
		setExceptionTable();
	}

	/**
	 * Handler Assignment
	 * 
	 * @author ecruz
	 */
	public void bind() {

		container.clear();
		container.add(display.asWidget());

		
		
		
		display.getEndpointPnl().getCloseBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("getEndpoint is clicked");
				display.getEndpointPnl().hide();
				setAdminTable();
			}
		});

		//display.getExceptionTabPnl().getTblPnl();
		
		display.getBannerPanel().getListBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("getBanner is clicked");
				if(display.getEndpointPnl().isVisibile()){
					display.getEndpointPnl().hide();
					setAdminTable();
				} else {
					display.getEndpointPnl().show();
				}
			}
		});
		display.getExceptionTabPnl().getDelNodeBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent paramClickEvent) {
				// TODO Auto-generated method stub
				System.out.println("getDelNodeBtn Exception is clicked");
				if(display.getExceptionTabPnl().hasSlctdNodes())
				{
					List<ExceptionDtlObj> exceptNodes = display.getNodeTabPanel().getExceptionSelectedNodes();
				
				}
			}});

		display.getNodeTabPanel().getDelNodeBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("getDelNodeBtn is clicked");
				if(display.getNodeTabPanel().hasSlctdNodes()){

					List<NodeDtlObj> nodes = display.getNodeTabPanel().getSelectedNodes();
					
					((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).deleteNode(
							nodes, 
							new AsyncCallback<List<String>>() {

								@Override
								public void onFailure(Throwable caught) {
									display.notify(Notification.ERROR, "Delete Node Failed!");
								}

								@Override
								public void onSuccess(List<String> results) {
									setAdminTable();
									for(String result : results){
										display.notify(Notification.INFO, result);
									}
								}

							});
					display.getNodeTabPanel().getDelNodeBtn().disable();
				}
			}
		});

	
		display.getAddExceptionNodePanel().getAddNodeBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				// TODO Auto-generated method stub
			//	Window.alert("add node button excetion tab is clicked!");
				List<ExceptionDtlObj> exceptionNodesToBeAdded = display.getExceptionTabPnl().getAddNodePnl().getListOfExceptionNodesObjToBeAdded();
				if (exceptionNodesToBeAdded == null){
					display.notify(Notification.ERROR, "There is a blank required field!!");
					return;
				}
				
				((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).addExceptionNode(
						exceptionNodesToBeAdded, 
						new AsyncCallback<List<String>>() {

							@Override
							public void onFailure(Throwable arg0) {
								// TODO Auto-generated method stub
								display.notify(Notification.ERROR, "Add Exception Node Failed!");
							}

							@Override
							public void onSuccess(List<String> results) {
								// TODO Auto-generated method stub
								for(String result : results){
									display.notify(Notification.INFO, result);
								}
								setExceptionTable();
							}});
				
				display.getExceptionTabPnl().getAddNodeBtn().click();
			}});
		display.getAddNodePanel().getAddNodeBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
			//	System.out.println("getAddNode is clicked");
				List<NodeDtlObj> nodesToBeAdded = display.getNodeTabPanel().getAddNodePnl().getListOfNodesObjToBeAdded();
				
				if (nodesToBeAdded == null){
					display.notify(Notification.ERROR, "There is a blank required field!!");
					return;
				}
				for(NodeDtlObj nodeObj : nodesToBeAdded ){
					try{
						double f = nodeObj.getCost();
						
					}
					catch(Exception e){
						display.notify(Notification.ERROR, "Wrong Data type!");

						
					}
					
				}
				
				
				((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).addNode(
						nodesToBeAdded, 
						new AsyncCallback<List<String>>() {

							@Override
							public void onFailure(Throwable caught) {
								display.notify(Notification.ERROR, "Add Node Failed!");
							}

							@Override
							public void onSuccess(List<String> results) {
							//	setAdminTable();
								for(String result : results){
									display.notify(Notification.INFO, result);
								}
								String chosenCluster = display.getNodeTabPanel().getListBox().getItemText(display.getNodeTabPanel().getListBox().getSelectedIndex());
								setAdminTableByCluster(chosenCluster);

							}

						});

				display.getNodeTabPanel().getAddNodeBtn().click();

			}
		});

		display.getNodeTabPanel().getListBox().addChangeHandler(new ChangeHandler(){

			@Override
			public void onChange(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				//	Window.alert("Clusterbox is change!");
				String chosenCluster = display.getNodeTabPanel().getListBox().getItemText(display.getNodeTabPanel().getListBox().getSelectedIndex());
				
				setAdminTableByCluster(chosenCluster);

				if (chosenCluster.equals("*"))
				display.getAddNodePanel().getTextBox().getClusterTxtbx().setText("*");
				else
				display.getAddNodePanel().getTextBox().getClusterTxtbx().setText(chosenCluster);

					
			}});
		
		display.getNodeTabPanel().getEndpointListBox().addChangeHandler(new ChangeHandler(){

			@Override
			public void onChange(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				//	Window.alert("Clusterbox is change!");
					
				setAdminTable();

				setExceptionTable();
					
			}});
		
		
		
		display.getNodeTabPanel().getSearchWdgt().getBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("getSearchWdgt is clicked");
				final String nodeName = display.getNodeTabPanel().getSearchWdgt().getTxtbx().getText();
				List<String> endpointIds = new ArrayList<String>();

				for(EndpointObj endpoint : CommonObjs.bpoUser.getEndpoints()){
					if(endpoint.isActive()){
						System.out.println("EndPOint: " + endpoint.getEndpointId());
						endpointIds.add(endpoint.getEndpointId());
					}
				}

				if(!nodeName.trim().isEmpty()){

					((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).searchNode(endpointIds, nodeName, new AsyncCallback<List<NodeDtlObj>>() {

						@Override
						public void onFailure(Throwable caught) {
							display.notify(Notification.ERROR, "Search Node Failed!");
						}

						@Override
						public void onSuccess(List<NodeDtlObj> results) {
							setAdminTable(results);
							if(results.isEmpty()){
								display.notify(Notification.ERROR, "Node "+nodeName+" not found!");
							}
						}

					});			
				} else {
					setAdminTable();
				}

			}
		});

		display.getViewElemPanel().getCloseBtn().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("getViewElement is clicked");
				display.getViewElemPanel().close();
				setAdminTable();
			}
		});
		
		display.getViewElemPanel().getDelBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("getDelElemPanel is clicked");
				if(display.getViewElemPanel().hasSlctdNodes()){

					((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).deleteElement(
							display.getViewElemPanel().getTbl().getElementsSelected(), 
							display.getViewElemPanel().getNodeObj(), new AsyncCallback<ResultObj>() {

								@Override
								public void onSuccess(ResultObj result) {
									setViewElements();
									display.getViewElemPanel().getTbl().getMapOfSelectedElements().clear();// added
									System.out.println("Size of tableselected: " + display.getViewElemPanel().getTbl().getMapOfSelectedElements().size());
							
									for(String s : result.getMap().values()){
										display.notify(Notification.ERROR, s);
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									display.notify(Notification.ERROR, "Delete Element(s) Failed!");
								}
							});
				}
			}
		});

		display.getInsertElemPanel().getAddElemBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("getEnsertElemPanel is clicked");
				final List<String> retKeyList = new ArrayList<String>();
				
				if(display.getViewElemPanel().getInsertElemPnl().getListOfElemsToBeAdded() == null)
				{
					display.notify(Notification.ERROR, "There is a blank required field! ");
					return ;
				}
				for(ElemDtlObj e : display.getViewElemPanel().getInsertElemPnl().getListOfElemsToBeAdded() ){
					
					retKeyList.add(e.getElementId());
					
				}
				((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).insertElement(
						display.getViewElemPanel().getInsertElemPnl().getListOfElemsToBeAdded(), 
						display.getViewElemPanel().getNodeObj(), 
						new AsyncCallback<ResultObj>() {

							@Override
							public void onFailure(Throwable caught) {
								display.notify(Notification.ERROR, "Insert Element Failed!");
							}

							@Override
							public void onSuccess(ResultObj result) {
							
								setViewElements();
								display.getViewElemPanel().getInsertPnlBtn().click();
								for(String key : retKeyList){
									String ans = result.map.get(key);
									if (ans.toLowerCase().contains("success"))
									{display.notify(Notification.SUCCESS,ans);
									CommonObjs.ElemRecordCount++;
									
									
									}
									else
									{
									display.notify(Notification.ERROR,ans);
									}
									display.getViewElemPanel().refresh();
									display.getViewElemPanel().updateLabel();

									
								}
							
								
								
								
							}
						});
			}
		});

		display.getChangePriorityLvlPanel().getPrilvlBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("getChangePriorityLvlPanel is clicked");
				List<ElemDtlObj> elemLst = display.getViewElemPanel().getTbl().getElementsSelected();
				for(ElemDtlObj e : elemLst){
					((BpoSvcAsync)CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).changeElmtPrty(
							display.getViewElemPanel().getNodeObj().getEndpointId()
							, e.getElementId()
							, e.getNodeId()
							, Integer.parseInt(display.getChangePriorityLvlPanel().getSelectedPriLvl())
							, new AsyncCallback<String>(){

								@Override
								public void onFailure(Throwable caught) {
									display.notify(Notification.ERROR, "Change Priority Failed!");
								}

								@Override
								public void onSuccess(String result) {
									setViewElements();
									display.getViewElemPanel().getChangePriorityLvlPanel().hide();
									if(display.getChangePriorityLvlPanel().getSelectedPriLvl()!=null){
										display.getViewElemPanel().getTbl().getMapOfSelectedElements().clear();// added
										System.out.println("Size of tableselected: " + display.getViewElemPanel().getTbl().getMapOfSelectedElements().size());
								
										display.notify(Notification.SUCCESS, "Change Priority Successful!");
									}
								//	display.getViewElemPanel().getTbl().getElementsSelected().clear();// added
									display.getViewElemPanel().refresh();
									display.getViewElemPanel().updateLabel();
								}
							});
				}
			}
		});

		display.getViewElemPanel().getChangePriorityLvlPanel().getCloseBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("View Element is clicked!");
				setViewElements();
				display.getViewElemPanel().getChangePriorityLvlPanel().hide();
			}
		});
		
		
		
		/** 
		 * @author edecano
		 */
		display.getViewElemPanel().getTrnsfrElmPnl().getCloseBtn().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("View Element is clicked!");
				setViewElements();
				display.getViewElemPanel().getTrnsfrElmPnl().hide();
			}
		});
		
		/** 
		 * @author edecano
		 */
		display.getViewElemPanel().getTrnsfrElmPnl().getTrnsfrElmntOkBtn().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(display.getViewElemPanel().hasSlctdNodes()){
					System.out.println("View Element is clicked!");
					List<ElemDtlObj> elmntLst = display.getViewElemPanel().getTbl().getElementsSelected();
					System.out.println("display.getViewElemPanel().getTrnsfrElmPnl().getSlctdNode(): " + display.getViewElemPanel().getTrnsfrElmPnl().getSlctdNode());
					for(ElemDtlObj elem : elmntLst){
						((BpoSvcAsync)CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).transferElement(
								elem.getElementId(), display.getViewElemPanel().getNodeObj().getNodeId(), display.getViewElemPanel().getTrnsfrElmPnl().getSlctdNode(), display.getViewElemPanel().getNodeObj().getEndpointId(), new AsyncCallback<String>() {

									@Override
									public void onFailure(Throwable caught) {
										display.notify(Notification.ERROR, "Transfer Element Failed!");
									}

									@Override
									public void onSuccess(String result) {
										setViewElements();
										display.getViewElemPanel().getTrnsfrElmPnl().hide();
										if(display.getViewElemPanel().getTrnsfrElmPnl().getSlctdNode()!=null){
											display.notify(Notification.SUCCESS, "Transfer Element Successful");
										}
										display.getViewElemPanel().getTbl().getMapOfSelectedElements().clear();// added
										System.out.println("Size of tableselected: " + display.getViewElemPanel().getTbl().getMapOfSelectedElements().size());
								
									}
																	
						});
					}
				}					
			}
		});
		
		/**
		 * @author edecano
		 */
		display.getViewElemPanel().getTrnsfrElmntBtn().addClickHandler(new ClickHandler() {
		
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("View Element is clicked!");
				if(!display.getViewElemPanel().getTrnsfrElmntBtn().isEnable()){
					display.getViewElemPanel().getTrnsfrElmPnl().hide();
				} else {
					
					String slctdNodeId = display.getViewElemPanel().getNodeObj().getEndpointId();
			
					((BpoSvcAsync)CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getNodes(slctdNodeId, new AsyncCallback<List<NodeDtlObj>>() {

						@Override
						public void onFailure(Throwable caught) {
							display.notify(Notification.ERROR, "Get Node Failed");
						}

						@Override
						public void onSuccess(List<NodeDtlObj> result) {
							
							List<String> nodeNameLst = new ArrayList<String>();
							
							for(NodeDtlObj node : result){
								
								if(!node.getNodeId().equals(display.getViewElemPanel().getNodeObj().getNodeId())){
									nodeNameLst.add(node.getNodeId());
								}		
							}
		
							display.getViewElemPanel().getTrnsfrElmPnl().setNodeList(nodeNameLst);
							display.getViewElemPanel().getTrnsfrElmPnl().show();
						}
						
					});
					
					
				}
				
			}
		});
		
		/** 
		 * @author mrongcales
		 */
		display.getChangeWorkerPanel().getWrkrOkBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("getchangeworker is clicked");
				List<ElemDtlObj> elemLst = display.getViewElemPanel().getTbl().getElementsSelected();
				System.out.println("elemLst.size() : " + elemLst.size());
				for(ElemDtlObj e : elemLst){
					((BpoSvcAsync)CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).changeWorkerId(
							display.getViewElemPanel().getNodeObj().getEndpointId()
							, e.getElementId()
							, display.getViewElemPanel().getNodeObj().getNodeId()
							, display.getChangeWorkerPanel().getNewWorkerID()
							, new AsyncCallback<String>(){

								@Override
								public void onFailure(Throwable caught) {
									display.notify(Notification.ERROR, "Change Worker Failed!");
								}

								@Override
								public void onSuccess(String result) {
									setViewElements();
									display.getViewElemPanel().getChangeWorkerPanel().hide();
									if(display.getChangeWorkerPanel().getNewWorkerID()!=null){
										display.getViewElemPanel().getTbl().getMapOfSelectedElements().clear();// added
										System.out.println("Size of tableselected: " + display.getViewElemPanel().getTbl().getMapOfSelectedElements().size());
								
										display.notify(Notification.SUCCESS, "Change Worker Successful");
										display.getViewElemPanel().refresh();
										display.getViewElemPanel().updateLabel();

											}
									
									}
							});
				}
				display.getViewElemPanel().getTbl().getElementsSelected().clear();
			}
		});

		display.getViewElemPanel().getChangeWorkerPanel().getCloseBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setViewElements();
				display.getViewElemPanel().getChangeWorkerPanel().hide();
			}
		});
		
		display.getBannerPanel().getLogoutBtn().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				CookieUtil ckeUtil = new CookieUtil();
				ckeUtil.dltCookie(CommonObjs.ssoInfoDto.getAppNm());
				CommonObjs.access = false;
				CommonObjs.eventBus.fireEvent(new LginEvent());
			}
		});

		for(final BatchUploadWidget upldWdgt : display.getBatchUpldTabPnl().getUpldWidgts()){

			upldWdgt.getUpldBtn().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					final String filePth = upldWdgt.getFileName();

					if (filePth != null && !filePth.isEmpty()) {

						if(filePth.toUpperCase().endsWith(".CSV")){

							if(upldWdgt.getSelectedEndpoint() != null){

								upldWdgt.getFormPnl().addSubmitCompleteHandler(new SubmitCompleteHandler() {

									@Override
									public void onSubmitComplete(SubmitCompleteEvent event) {

										display.showLoadingScn();

										((BpoSvcAsync)CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).uploadBtch(
												filePth, 
												upldWdgt.getSelectedEndpoint(), 
												upldWdgt.getType().getValue(), new AsyncCallback<UpldRsltObj>() {

													@Override
													public void onSuccess(UpldRsltObj result) {
														display.getBatchUpldTabPnl().viewResults(result, upldWdgt.getType());
														setAdminTable();
														upldWdgt.reset();
														display.hideLoadingScn();
													}

													@Override
													public void onFailure(Throwable caught) {
														display.hideLoadingScn();
														display.notify(Notification.ERROR, "Upload Batch Failed!");
														upldWdgt.reset();
													}
												});
									}
								});

								upldWdgt.submit();

							} else {
								display.notify(Notification.WARNING, "No Selected Endpoint");
								upldWdgt.reset();
							}
						}
					}
				}
			});
		}

	}

	/**
	 * Refresh Node Table
	 * 
	 * @author ecruz
	 */
	private void setAdminTable(){
		String chosenEndPoint = display.getNodeTabPanel().getEndpointListBox().getItemText(display.getNodeTabPanel().getEndpointListBox().getSelectedIndex());

		display.getNodeTabPanel().getDelNodeBtn().disable();
		display.getNodeTabPanel().clear();

		for (final EndpointObj dtls : CommonObjs.bpoUser.getEndpoints()) {

			if(dtls.isActive()){
				if (!(chosenEndPoint.equals("*")|| chosenEndPoint.equals(dtls.getEndpointId()))){
					continue;
				}
				final SearchableEndpointNodesWidget widget = new SearchableEndpointNodesWidget(dtls.getLabel());

				((BpoSvcAsync)CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getNodes(
						dtls.getEndpointId(), new AsyncCallback<List<NodeDtlObj>>() {

					@Override
					public void onSuccess(List<NodeDtlObj> result) {
						display.getNodeTabPanel().addEndpoint(widget, result);// adds the node
						widget.stopLoading();
					}

					@Override
					public void onFailure(Throwable caught) {
						display.notify(Notification.ERROR, "Get Node Failed");
						widget.stopLoading();
					}
				});
				
				widget.getSearchWdgt().getBtn().addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						final String elementId = widget.getSearchWdgt().getText();
						final String endpointId = dtls.getEndpointId();

						if(!elementId.trim().isEmpty()){
							((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).searchElement(endpointId, elementId, new AsyncCallback<ElemDtlObj>() {
								@Override
								public void onFailure(Throwable caught) {
									display.notify(Notification.ERROR, "Search Element Failed!");
								}
								@Override
								public void onSuccess(ElemDtlObj elem) {
									if(elem == null){
										display.notify(Notification.ERROR, "Element " 
												+ elementId 
												+ " is not existing in " 
												+ getEndpointLabel(endpointId));
									} else {
										setViewElements(elem, endpointId);
									}
								}
							});
						}
					}
				});
			}
		}
		display.getNodeTabPanel().fillListBox();
	}
	/**
	 * Builds the exception node table
	 */
	
	private void setExceptionTable(){
		String chosenEndPoint = display.getNodeTabPanel().getEndpointListBox().getItemText(display.getNodeTabPanel().getEndpointListBox().getSelectedIndex());

		display.getExceptionTabPnl().getClusterList().setVisible(false);
		//	display.getExceptionTabPnl().getAddNodeBtn().setVisible(false);
		//	display.getExceptionTabPnl().getDelNodeBtn().setVisible(false);
		display.getExceptionTabPnl().getSearchWdgt().setVisible(false);
		
		
		display.getExceptionTabPnl().getDelNodeBtn().disable();
		display.getExceptionTabPnl().clear();

		for (final EndpointObj dtls : CommonObjs.bpoUser.getEndpoints()) {

			if(dtls.isActive()){
				if (!(chosenEndPoint.equals("*")|| chosenEndPoint.equals(dtls.getEndpointId()))){
					continue;
				}
				final ExceptionSearchableEndpointNodesWidget widget = new ExceptionSearchableEndpointNodesWidget(dtls.getLabel());

				((BpoSvcAsync)CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getNodesException(
						dtls.getEndpointId(), new AsyncCallback<List<ExceptionDtlObj>>() {

					@Override
					public void onSuccess(List<ExceptionDtlObj> result) {
						display.getExceptionTabPnl().addExceptionEndpoint(widget, result);// adds the node
						widget.stopLoading();
					}

					@Override
					public void onFailure(Throwable caught) {
						display.notify(Notification.ERROR, "Get Node Failed");
						widget.stopLoading();
					}
				});
				
				widget.getSearchWdgt().getBtn().addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						final String elementId = widget.getSearchWdgt().getText();
						final String endpointId = dtls.getEndpointId();

						if(!elementId.trim().isEmpty()){
							((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).searchElement(endpointId, elementId, new AsyncCallback<ElemDtlObj>() {
								@Override
								public void onFailure(Throwable caught) {
									display.notify(Notification.ERROR, "Search Element Failed!");
								}
								@Override
								public void onSuccess(ElemDtlObj elem) {
									if(elem == null){
										display.notify(Notification.ERROR, "Element " 
												+ elementId 
												+ " is not existing in " 
												+ getEndpointLabel(endpointId));
									} else {
										setViewElements(elem, endpointId);
									}
								}
							});
						}
					}
				});
			}
		}
		
	//	display.getNodeTabPanel().fillListBox();
	}

	private void setAdminTable(List<NodeDtlObj> nodes){

		display.getNodeTabPanel().clear();

		List<NodeDtlObj> temp;

		for(NodeDtlObj node : nodes){

			EndpointNodesWidget widget = new SearchableEndpointNodesWidget(getEndpointLabel(node.getEndpointId()));
			temp = new ArrayList<NodeDtlObj>();
			temp.add(node);

			display.getNodeTabPanel().addEndpoint(widget, temp);
			widget.stopLoading();
			
			display.getNodeTabPanel().getTblPnl().add(widget);

		}
	}

	private void setAdminTableByCluster(String cluster){// will return only by cluster
		String chosenEndPoint = display.getNodeTabPanel().getEndpointListBox().getItemText(display.getNodeTabPanel().getEndpointListBox().getSelectedIndex());

		display.getNodeTabPanel().getDelNodeBtn().disable();
		display.getNodeTabPanel().clear();

		for (final EndpointObj dtls : CommonObjs.bpoUser.getEndpoints()) {

			if(dtls.isActive()){
				if (!(chosenEndPoint.equals("*")|| chosenEndPoint.equals(dtls.getEndpointId()))){
					continue;
				}
				final SearchableEndpointNodesWidget widget = new SearchableEndpointNodesWidget(dtls.getLabel());

				((BpoSvcAsync)CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getClusterNodes(
						dtls.getEndpointId(),cluster, new AsyncCallback<List<NodeDtlObj>>() {

					@Override
					public void onSuccess(List<NodeDtlObj> result) {
						display.getNodeTabPanel().addEndpoint(widget, result);// adds the node
						widget.stopLoading();
					}

					@Override
					public void onFailure(Throwable caught) {
						display.notify(Notification.ERROR, "Get Node Failed");
						widget.stopLoading();
					}
				});
				
				widget.getSearchWdgt().getBtn().addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						final String elementId = widget.getSearchWdgt().getText();
						final String endpointId = dtls.getEndpointId();

						if(!elementId.trim().isEmpty()){
							((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).searchElement(endpointId, elementId, new AsyncCallback<ElemDtlObj>() {
								@Override
								public void onFailure(Throwable caught) {
									display.notify(Notification.ERROR, "Search Element Failed!");
								}
								@Override
								public void onSuccess(ElemDtlObj elem) {
									if(elem == null){
										display.notify(Notification.ERROR, "Element " 
												+ elementId 
												+ " is not existing in " 
												+ getEndpointLabel(endpointId));
									} else {
										setViewElements(elem, endpointId);
									}
								}
							});
						}
					}
				});
			}
		}
	}
	
	
	
	private String getEndpointLabel(String endpointId){

		for (EndpointObj dtls : CommonObjs.bpoUser.getEndpoints()) {
			if(endpointId.trim().equals(dtls.getEndpointId())){
				return dtls.getLabel();
			}
		}

		return null;
	}

	private void setViewElements(){
		System.out.println("SET VIEW ELEMENTS ");
		((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getNodeWithElements(
				display.getViewElemPanel().getNodeObj(), 
				new AsyncCallback<NodeDtlObj>() {

			@Override
			public void onFailure(Throwable caught) {
				display.notify(Notification.ERROR, "Get Elements Failed!");
			}

			@Override
			public void onSuccess(NodeDtlObj result) {
				System.out.println("GET ELEMENTS SUCCEED");
				display.getViewElemPanel().setNodeObj(result);

			//	display.getViewElemPanel().setTable(result.getElementList());
			//	CommonObjs.ElemRecordCount = result.getElementList().size();
			//	display.getViewElemPanel().updateLabel();
				display.getViewElemPanel().refresh();
				display.getViewElemPanel().show();
			}
		});
		

//		((BpoSvcAsync) CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getElements(
//				display.getViewElemPanel().getNodeObj(),
//				new AsyncCallback<List<ElemDtlObj>>() {
//
//					@Override
//					public void onSuccess(List<ElemDtlObj> result) {
//						display.getViewElemPanel().setNodeObj(display.getViewElemPanel().getNodeObj());
//						display.getViewElemPanel().setTable(result);
//						display.getViewElemPanel().show();
//
//						display.getViewElemPanel().getDelBtn().disable();
//						display.getViewElemPanel().getTbl().clearSelectedElements();
//					}
//
//					@Override
//					public void onFailure(Throwable caught) {
//						display.notify(Notification.ERROR, "Get Elements Failed!");
//					}
//				});
	}

	private void setViewElements(ElemDtlObj elem, String endpointId){

		NodeDtlObj node = new NodeDtlObj();
		node.setEndpointId(endpointId);
		//System.out.println("elem.getCurrnode: " + elem.getCurrNode());
		node.setNodeId(elem.getNodeId());

		display.getViewElemPanel().setNodeObj(node);
		display.getViewElemPanel().setTable(elem);
		display.getViewElemPanel().show();
		
	}
	
}	