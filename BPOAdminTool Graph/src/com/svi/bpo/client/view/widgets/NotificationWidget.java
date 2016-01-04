package com.svi.bpo.client.view.widgets;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.svi.bpo.constants.Notification;

public class NotificationWidget extends Composite {

	public Timer timer;
	public Timer exitTimer;
	
	public NotificationWidget(Notification value, String text) {
		
		FlowPanel logo = new FlowPanel();
		logo.setStyleName("notif-widget-logo");
		logo.addStyleName(value.getLogoStyle());
		
		HTMLPanel label = new HTMLPanel(text);
		label.setStyleName("notif-widget-lbl");
		
//		FlowPanel closeBtn = new FlowPanel();
//		closeBtn.setStyleName("notif-widget-close-btn");
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("notif-widget");
		mainPnl.addStyleName(value.getWidgetStyle());
		mainPnl.add(logo);
		mainPnl.add(label);
//		mainPnl.add(closeBtn);
		
		initWidget(mainPnl);
		
		timer = new Timer() {
			
			@Override
			public void run() {
				timer.cancel();
				NotificationWidget.this.removeFromParent();
			}
		};
		
		timer.schedule(3000);
	}
}
