package com.svi.bpo.client.view.widgets;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.svi.bpo.client.CommonObjs;

public class BannerPanel extends Composite{

	private Button logoutBtn;
	private Button listBtn;
	
	public BannerPanel() {

		FlowPanel logo = new FlowPanel();
		logo.setStyleName("bnr-bpo-logo");

		logoutBtn = new Button();
		logoutBtn.setStyleName("bnr-logout-btn");
				
		listBtn = new Button();
		listBtn.setStyleName("bnr-list-btn");	

		FlowPanel mainPnl = new FlowPanel();
		mainPnl.add(logoutBtn);
		mainPnl.add(listBtn);
		mainPnl.add(logo);

		if(CommonObjs.bpoProps.getMode().equalsIgnoreCase("Y")){
			listBtn.setVisible(false);
		}
		
		initWidget(mainPnl);
	}

	public Button getLogoutBtn() {
		return logoutBtn;
	}

	public Button getListBtn() {
		return listBtn;
	}

}
