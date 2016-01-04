package com.svi.bpo.client.view;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.client.view.widgets.BannerPanel;
import com.svi.bpo.client.view.widgets.EndpointListPanel;
import com.svi.bpo.client.view.widgets.NavigationPanel;
import com.svi.bpo.client.view.widgets.NotificationWidget;
import com.svi.bpo.constants.Notification;

public abstract class BsScn extends Composite {

	private BannerPanel bannrPnl;
	private NavigationPanel navPnl;
	private FlowPanel viewPanel;
	private EndpointListPanel endpointPnl;
	private FlowPanel loadingPnl;
	
	public BsScn() {

		bannrPnl = new BannerPanel();
		bannrPnl.setStyleName("bnr-pnl");
		
		navPnl = new NavigationPanel();
		navPnl.setStyleName("nav-pnl");

		endpointPnl = new EndpointListPanel();
		endpointPnl.setStyleName("endpt-list-pnl");
		
		viewPanel = new FlowPanel();
		viewPanel.setStyleName("view-pnl");
		viewPanel.add(bldCtnt());
		viewPanel.add(endpointPnl);
		
		FlowPanel appPnl = new FlowPanel();
	    appPnl.setStyleName("app-body");
	    appPnl.add(navPnl);
	    appPnl.add(viewPanel);
	    
	    FlowPanel logo = new FlowPanel();
		logo.setStyleName("bnr-bpo-logo-loading");
	    logo.add(new FlowPanel());
		
		loadingPnl = new FlowPanel();
		loadingPnl.setStyleName("loading-pnl");
		loadingPnl.getElement().getStyle().setDisplay(Display.NONE);
		loadingPnl.add(logo);
		
		FlowPanel contentPnl = new FlowPanel();
		contentPnl.setStyleName("base-app-pnl");
	    contentPnl.add(bannrPnl);
	    contentPnl.add(appPnl);
	    contentPnl.add(loadingPnl);

		CommonObjs.notifyPanel = new FlowPanel();
		CommonObjs.notifyPanel.setStyleName("notif-pnl");
		
		FlowPanel basePnl = new FlowPanel();
		basePnl.setStyleName("base-scn");
		basePnl.add(contentPnl);
		basePnl.add(CommonObjs.notifyPanel);
	    
		initWidget(basePnl);
	}

	public abstract FlowPanel bldCtnt();

	public NavigationPanel getNavPnl() {
		return navPnl;
	}

	public BannerPanel getBannrPnl() {
		return bannrPnl;
	}

	public FlowPanel getViewPanel() {
		return viewPanel;
	}

	public EndpointListPanel getEndpointPnl() {
		return endpointPnl;
	}
	
	public void showLoadingScn(){
		loadingPnl.getElement().getStyle().setDisplay(Display.BLOCK);
	}
	
	public void hideLoadingScn(){
		loadingPnl.getElement().getStyle().setDisplay(Display.NONE);
	}
	
	public void notify(Notification notification, String text){
		CommonObjs.notifyPanel.add(new NotificationWidget(notification, text));
	}
	
}
