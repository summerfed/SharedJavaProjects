package com.svi.bpo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class BpoAdmin implements EntryPoint {

	@Override
	public void onModuleLoad() {
		CommonObjs.access = false;
		AppController appCntrllr = new AppController();
		appCntrllr.go(RootPanel.get());
	}

}
