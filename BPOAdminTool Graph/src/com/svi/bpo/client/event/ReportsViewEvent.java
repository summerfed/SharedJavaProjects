package com.svi.bpo.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.svi.bpo.client.CommonObjs;

public class ReportsViewEvent extends GwtEvent<ReportsViewEventHandler>{

	public static Type<ReportsViewEventHandler> TYPE = new Type<ReportsViewEventHandler>();
	
	public ReportsViewEvent () {
		CommonObjs.access = true;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ReportsViewEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ReportsViewEventHandler handler) {
		handler.onStatisticsView(this);
	}
	

}
