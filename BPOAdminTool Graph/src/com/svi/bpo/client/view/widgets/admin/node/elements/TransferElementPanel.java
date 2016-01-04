package com.svi.bpo.client.view.widgets.admin.node.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.svi.bpo.client.view.widgets.SwitchButton;

public class TransferElementPanel extends Composite{
	
	private FlowPanel mainPnl;
	private boolean visibile;
	private Button closeBtn;
	private FlowPanel nodeContainer;
	private List<String> nodeList;
	private SwitchButton trnsfrElmntOkBtn;
	private String slctdNode;
	private FlowPanel container;
	
	public TransferElementPanel(){
				
		mainPnl = new FlowPanel();
		nodeList = new ArrayList<String>();
		container = new FlowPanel();
		container.setStyleName("trnsfrElmt-containerPnl");
		
		closeBtn = new Button();
		closeBtn.setStyleName("endpt-list-pnl-close-btn");
		
		HTMLPanel hdr = new HTMLPanel("Transfer Element");
		hdr.setStyleName("trnsfrElmnt-list-hdr");
		hdr.add(closeBtn);
		
		trnsfrElmntOkBtn = new SwitchButton("OK");
		trnsfrElmntOkBtn.setStyleName("priLvl-btn");
		FlowPanel btnPnl = new FlowPanel();
		btnPnl.setStyleName("trnsfrElem-btnPnl");
		btnPnl.add(trnsfrElmntOkBtn);
		
		nodeContainer = new FlowPanel();
		nodeContainer.setStyleName("node-wdgt-cntr");
		container.add(hdr);
		container.add(nodeContainer);
		container.add(btnPnl);
		
		mainPnl.setStyleName("endpt-list-pnl");
		mainPnl.add(container);
		//mainPnl.add(btnPnl);
		
		hide();
		
		initWidget(mainPnl);	
	}
	
	private void buildNodeTble(){

		clearNodeWdgts();
		
		for(int i = 0; i < nodeList.size(); i++){
			nodeContainer.add(new NodeWidget(nodeList.get(i), i));
		}
	}
	
	public boolean isVisibile() {
		return visibile;
	}
	
	public void show(){
		mainPnl.getElement().getStyle().setDisplay(Display.BLOCK);
		visibile = true;
	}
	
	public void hide(){
		mainPnl.getElement().getStyle().setDisplay(Display.NONE);
		visibile = false;
	}
	
	public Button getCloseBtn(){
		return this.closeBtn;
	}
	
	
	class NodeWidget extends Composite {
		
		private FlowPanel mainPnl;
		private SwitchButton checkBtn;
		private String id;
		private boolean isSelected;
		
		NodeWidget(String nodeDesc, int index){
			
			isSelected = false;		
			Label lbl = new Label(nodeDesc);
			lbl.setStyleName("endpt-wdgt-lbl");
			
			mainPnl = new FlowPanel();
			mainPnl.setStyleName("endpt-wdgt-pnl-selected");
			//mainPnl.setStyleName("endpt-wdgt-pnl");
			mainPnl.add(lbl);
			
			mainPnl.addDomHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					chngeNdeWdgtToEnable();
					slctdNode = ((Label)mainPnl.getWidget(0)).getText();
					if(!isSelected){
						isSelected = true;
						//disableEndpoint();
						mainPnl.setStyleName("endpt-wdgt-pnl");
						
					} else {
						//enableEndpoint();
						mainPnl.setStyleName("endpt-wdgt-pnl-selected");
						
					}
				}
			}, ClickEvent.getType());
			
//			if(!this.isSelected){
//				enableEndpoint();
//			} else {
//				disableEndpoint();
//			}
			
			initWidget(mainPnl);
			
		}
		
		private void enableEndpoint(){
			//checkBtn.enable();
			//mainPnl.setStyleName("endpt-wdgt-pnl");
			mainPnl.setStyleName("endpt-wdgt-pnl-selected");
			//CommonObjs.bpoUser.getEndpoints().get(index).setActive(true);
		}
		
//		private void disableEndpoint(){
//			//checkBtn.disable();
//			mainPnl.setStyleName("endpt-wdgt-pnl");
//			//mainPnl.setStyleName("endpt-wdgt-pnl-selected");
//			//CommonObjs.bpoUser.getEndpoints().get(index).setActive(false);
//		}

		public String getId() {
			return id;
		}

		public SwitchButton getCheckBtn() {
			return checkBtn;
		}
	}
	
	public void setNodeList(List<String> nodes){
		nodeList = nodes;
		buildNodeTble();
	}
	
	private void clearNodeWdgts(){		
		Iterator<Widget> it = nodeContainer.iterator();
		while(it.hasNext()){
			if(it.next() instanceof NodeWidget){
				it.remove();
			}
		}
	}
	
	public SwitchButton getTrnsfrElmntOkBtn(){
		return trnsfrElmntOkBtn;
	}
	
	public String getSlctdNode(){
		return slctdNode;
	}
	
	public void chngeNdeWdgtToEnable(){
		for(int i = 0; i < nodeContainer.getWidgetCount(); i++){
			if(((NodeWidget)nodeContainer.getWidget(i)).isSelected){
				//((NodeWidget)nodeContainer.getWidget(i)).enableEndpoint();
				((NodeWidget)nodeContainer.getWidget(i)).isSelected = false;
				//((NodeWidget)nodeContainer.getWidget(i)).disableEndpoint();
				((NodeWidget)nodeContainer.getWidget(i)).enableEndpoint();
			}
		}
	}
	
}
