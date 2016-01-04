package com.svi.bpo.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.svi.bpo.client.CommonObjs;

public class AdminViewEvent extends GwtEvent<AdminViewEventHandler>{

	public static Type<AdminViewEventHandler> TYPE = new Type<AdminViewEventHandler>();
	
	public AdminViewEvent () {
		CommonObjs.access = true;
	}
	
	@Override
	protected void dispatch(AdminViewEventHandler handler) {
		handler.onAdminView(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AdminViewEventHandler> getAssociatedType() {
		return TYPE;
	}

}
