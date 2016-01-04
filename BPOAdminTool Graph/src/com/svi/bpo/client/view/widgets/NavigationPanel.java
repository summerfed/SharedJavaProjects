package com.svi.bpo.client.view.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.client.event.AdminViewEvent;
import com.svi.bpo.client.event.DashboardViewEvent;
import com.svi.bpo.client.event.ReportsViewEvent;

public class NavigationPanel extends Composite {
	
	private Button adminBtn;
	private Button dashbrdBtn;
	private Button reportsBtn;
	
	public NavigationPanel() {
		
		FlowPanel navPnl = new FlowPanel();
		
		adminBtn = new Button("ADMIN");
		dashbrdBtn = new Button("DASHBOARD");
		reportsBtn = new Button("REPORTS");
		
		adminBtn.addStyleName("nav-admin-btn");
		dashbrdBtn.addStyleName("nav-dashboard-btn");
		reportsBtn.addStyleName("nav-reports-btn");
		
		addInitialBtnHandlers();
		
		navPnl.add(adminBtn);
		navPnl.add(dashbrdBtn);
		navPnl.add(reportsBtn);
		
		if(CommonObjs.bpoProps.getMode().equalsIgnoreCase("Y")){
			dashbrdBtn.setVisible(false);
			reportsBtn.setVisible(false);
		}
		
		initWidget(navPnl);
	}
	
	private void addInitialBtnHandlers() {
		
		adminBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent paramClickEvent) {
				CommonObjs.eventBus.fireEvent(new AdminViewEvent());
				selectAdminBtn();
			}
		});

		dashbrdBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent paramClickEvent) {
				CommonObjs.eventBus.fireEvent(new DashboardViewEvent());
				selectDashboardBtn();
			}
		});
		
		reportsBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent paramClickEvent) {
				CommonObjs.eventBus.fireEvent(new ReportsViewEvent());
				selectReportsBtn();
			}
		});
	}

	public void selectAdminBtn() {
		adminBtn.addStyleName("nav-btn-selected");
	}
	
	public void selectDashboardBtn() {
		dashbrdBtn.addStyleName("nav-btn-selected");
	}
	
	public void selectReportsBtn() {
		reportsBtn.addStyleName("nav-btn-selected");
	}

	public Button getAdminBtn() {
		return adminBtn;
	}

	public void setAdminBtn(Button adminBtn) {
		this.adminBtn = adminBtn;
	}

	public Button getStatsBtn() {
		return reportsBtn;
	}

	public void setStatsBtn(Button statsBtn) {
		this.reportsBtn = statsBtn;
	}

	public Button getDashbrdBtn() {
		return dashbrdBtn;
	}

	public void setDashbrdBtn(Button dashbrdBtn) {
		this.dashbrdBtn = dashbrdBtn;
	}
	
}
