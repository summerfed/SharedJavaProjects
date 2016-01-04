package com.svi.bpo.client.view.widgets;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.objects.EndpointObj;

public class EndpointListPanel extends Composite {
	
	private FlowPanel mainPnl;
	private boolean visibile;
	private Button closeBtn;
	
	public EndpointListPanel() {
		
		closeBtn = new Button();
		closeBtn.setStyleName("endpt-list-pnl-close-btn");
		
		HTMLPanel hdr = new HTMLPanel("Endpoints");
		hdr.setStyleName("endpt-list-hdr");
		hdr.add(closeBtn);

		FlowPanel container = new FlowPanel();
		container.add(hdr);
		for(int i = 0; i < CommonObjs.bpoUser.getEndpoints().size(); i++){
			container.add(new EndPointWidget(CommonObjs.bpoUser.getEndpoints().get(i), i));
		}
		
		mainPnl = new FlowPanel();
		mainPnl.add(container);
		
		hide();
		
		initWidget(mainPnl);
		
	}
	
	public List<String> getEndpoints(){
		
		List<String> endpoints = new ArrayList<String>();
		
		for(int i = 0; i < CommonObjs.bpoUser.getEndpoints().size(); i++){
			if(CommonObjs.bpoUser.getEndpoints().get(i).isActive()){
				endpoints.add(CommonObjs.bpoUser.getEndpoints().get(i).getEndpointId());
			}
		}
		
		return endpoints;
	}
	
	public void show(){
		mainPnl.getElement().getStyle().setDisplay(Display.BLOCK);
		visibile = true;
	}
	
	public void hide(){
		mainPnl.getElement().getStyle().setDisplay(Display.NONE);
		visibile = false;
	}
	
	public boolean isVisibile() {
		return visibile;
	}
	
	public Button getCloseBtn() {
		return closeBtn;
	}



	class EndPointWidget extends Composite {
		
		private FlowPanel mainPnl;
		private SwitchButton checkBtn;
		private String id;
		private int index;
		
		EndPointWidget(EndpointObj obj, int index){
			
			this.id = obj.getEndpointId();
			this.index = index;
			
			checkBtn = new SwitchButton("");
			checkBtn.setEnableStyle("endpt-wdgt-chk-btn-chkd");
			checkBtn.setDisableStyle("endpt-wdgt-chk-btn-unchkd");
			
			HTMLPanel lbl = new HTMLPanel(obj.getLabel());
			lbl.setStyleName("endpt-wdgt-lbl");
			
			mainPnl = new FlowPanel();
			mainPnl.setStyleName("endpt-wdgt-pnl");
			mainPnl.add(checkBtn);
			mainPnl.add(lbl);
			
			mainPnl.addDomHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if(checkBtn.isEnable()){
						disableEndpoint();
					} else {
						enableEndpoint();
					}
				}
			}, ClickEvent.getType());
			
			if(obj.isActive()){
				enableEndpoint();
			} else {
				disableEndpoint();
			}
			
			initWidget(mainPnl);
			
		}
		
		private void enableEndpoint(){
			checkBtn.enable();
			mainPnl.setStyleName("endpt-wdgt-pnl-selected");
			CommonObjs.bpoUser.getEndpoints().get(index).setActive(true);
		}
		
		private void disableEndpoint(){
			checkBtn.disable();
			mainPnl.setStyleName("endpt-wdgt-pnl");
			CommonObjs.bpoUser.getEndpoints().get(index).setActive(false);
		}

		public String getId() {
			return id;
		}

		public SwitchButton getCheckBtn() {
			return checkBtn;
		}
	}
}
