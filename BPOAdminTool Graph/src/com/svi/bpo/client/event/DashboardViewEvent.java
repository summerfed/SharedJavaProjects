package com.svi.bpo.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.svi.bpo.client.CommonObjs;

public class DashboardViewEvent extends GwtEvent<DashboardViewEventHandler>{

	public static Type<DashboardViewEventHandler> TYPE = new Type<DashboardViewEventHandler>();
	
	public DashboardViewEvent() {
		CommonObjs.access = true;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DashboardViewEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DashboardViewEventHandler handler) {
		handler.onDashboardView(this);
	}

}
