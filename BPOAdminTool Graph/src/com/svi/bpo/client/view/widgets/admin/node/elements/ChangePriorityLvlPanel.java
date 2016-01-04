package com.svi.bpo.client.view.widgets.admin.node.elements;

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
import com.svi.bpo.client.view.widgets.SwitchButton;

public class ChangePriorityLvlPanel extends Composite{

	private FlowPanel mainPnl;
	private boolean visible;
	private Button closeBtn;
	private FlowPanel container;
	private FlowPanel priLvlCntnrWdgt;
	private FlowPanel priLvlBtnCntr;
	private SwitchButton prilvlBtn;
	private String selectedPriLvl;

	public ChangePriorityLvlPanel(){

		closeBtn = new Button();
		closeBtn.setStyleName("endpt-list-pnl-close-btn");

		HTMLPanel hdr = new HTMLPanel("Change Priority Level");
		hdr.setStyleName("endpt-list-hdr");
		hdr.add(closeBtn);

		container = new FlowPanel();

		container.add(hdr);

		priLvlCntnrWdgt = new FlowPanel();
		priLvlCntnrWdgt.setStyleName("priLvl-wdgt-cntr");

		buildPriLvlPnls();

		container.add(priLvlCntnrWdgt);

		priLvlBtnCntr = new FlowPanel();
		priLvlBtnCntr.setStyleName("priLvl-btn-pnl");

		prilvlBtn = new SwitchButton("OK");
		prilvlBtn.setStyleName("priLvl-btn");

		priLvlBtnCntr.add(prilvlBtn);

		container.add(priLvlBtnCntr);

		mainPnl = new FlowPanel();

		mainPnl.setStyleName("endpt-list-pnl");
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
		visible = true;
	}

	public void hide(){

		int wdgtCnt = priLvlCntnrWdgt.getWidgetCount();
		for(int i = 0; i < wdgtCnt; i++){
			if(priLvlCntnrWdgt.getWidget(i) instanceof SwitchButton){
				if(!((SwitchButton)priLvlCntnrWdgt.getWidget(i)).isEnable()){
					((SwitchButton)priLvlCntnrWdgt.getWidget(i)).enable();
				}
			}
		}

		mainPnl.getElement().getStyle().setDisplay(Display.NONE);
		visible = false;
	}

	public boolean isVisibile() {
		return visible;
	}

	public Button getCloseBtn() {
		return closeBtn;
	}

	public String getSelectedPriLvl(){
		return this.selectedPriLvl;
	}

	public SwitchButton getPrilvlBtn(){
		return this.prilvlBtn;
	}

	public void buildPriLvlPnls(){

		for(int i = 0; i < 10; i++){

			final SwitchButton priLvlBtn = new SwitchButton(String.valueOf(i));
			priLvlBtn.setEnableStyle("priLvl-btn-enable");
			priLvlBtn.setDisableStyle("priLvl-btn-disable");

			priLvlBtn.enable();

			priLvlBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if(checkPriLvlSlctd()){
						getIndxOfSlctdWdgt();						
					}

					priLvlBtn.disable();
					selectedPriLvl = priLvlBtn.getText();			
				}
			});

			priLvlCntnrWdgt.add(priLvlBtn);
		}
	}

	private boolean checkPriLvlSlctd() {
		boolean slctd = false;
		int wdgtCnt = priLvlCntnrWdgt.getWidgetCount();

		for(int i = 0; i < wdgtCnt; i++){
			if(priLvlCntnrWdgt.getWidget(0) instanceof SwitchButton){
				if(!((SwitchButton)priLvlCntnrWdgt.getWidget(i)).isEnable()){
					return true;
				}
			}
		}

		return slctd;
	}

	private int getIndxOfSlctdWdgt(){
		int indx = 0;
		int wdgtCnt = priLvlCntnrWdgt.getWidgetCount();

		for(int i = 0; i < wdgtCnt; i++){
			if(priLvlCntnrWdgt.getWidget(i) instanceof SwitchButton){
				if(!((SwitchButton)priLvlCntnrWdgt.getWidget(i)).isEnable()){
					((SwitchButton)priLvlCntnrWdgt.getWidget(i)).enable();
					return i;
				}
			}
		}

		return indx;
	}

}
