package com.svi.bpo.client.view.widgets.admin.node.elements;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.client.view.widgets.SwitchButton;

public class ChangeWorkerPanel extends Composite{

	private FlowPanel mainPnl;
	private boolean visible;
	private Button closeBtn;
	private FlowPanel container;
	private FlowPanel wrkrCntnrWdgt;
	private FlowPanel wrkrBtnCntr;
	private SwitchButton wrkrBtn;
	private Label newWrkrLbl;
	private TextBox newWrkrTxBx;

	public ChangeWorkerPanel(){

		closeBtn = new Button();
		closeBtn.setStyleName("endpt-list-pnl-close-btn");

		HTMLPanel hdr = new HTMLPanel("Change Worker");
		hdr.setStyleName("endpt-list-hdr");
		hdr.add(closeBtn);

		container = new FlowPanel();

		container.add(hdr);

		wrkrCntnrWdgt = new FlowPanel();
		wrkrCntnrWdgt.setStyleName("wrkr-wdgt-cntr");

		newWrkrLbl = new Label("New Worker ID: ");
		newWrkrLbl.setStyleName("wrkr-wdgt-lbl");
		newWrkrTxBx = new TextBox();
		newWrkrTxBx.setStyleName("wrkr-wdgt-txbx");
		wrkrCntnrWdgt.add(newWrkrLbl);
		wrkrCntnrWdgt.add(newWrkrTxBx);

		container.add(wrkrCntnrWdgt);

		wrkrBtnCntr = new FlowPanel();
		wrkrBtnCntr.setStyleName("wrkr-btn-pnl");

		wrkrBtn = new SwitchButton("OK");
		wrkrBtn.setStyleName("wrkr-btn");

		wrkrBtnCntr.add(wrkrBtn);

		container.add(wrkrBtnCntr);

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
		mainPnl.getElement().getStyle().setDisplay(Display.NONE);
		visible = false;
	}

	public boolean isVisibile() {
		return visible;
	}

	public Button getCloseBtn() {
		return closeBtn;
	}

	public SwitchButton getWrkrOkBtn() {
		return wrkrBtn;
	}

	public String getNewWorkerID(){
		return newWrkrTxBx.getText();
	}

	

}
