package com.svi.bpo.client.view;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.svi.bpo.client.presenter.ReportsViewScnPresenter.Display;
import com.svi.bpo.constants.Notification;

public class ReportsViewScn extends BsScn implements Display{

	@Override
	public FlowPanel bldCtnt() {
		getNavPnl().selectReportsBtn();
		FlowPanel defPanel = new FlowPanel();
		defPanel.add(getReportGraphsFrame());
		defPanel.setStyleName("reportgraphcanvas");
		return defPanel;
	}

	@Override
	public void notify(Notification notification, String text){
		super.notify(notification, text);
	}
	
	private Frame getReportGraphsFrame() {
		Frame frame = new Frame("reportgraphs/index.html");
		frame.setStyleName("reportgraphcanvas");
		return frame;
	}
}
