package com.svi.bpo.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.svi.bpo.client.CommonObjs;

public class LginEvent extends GwtEvent<LginEventHandler>{
	public static Type<LginEventHandler> TYPE = new Type<LginEventHandler>();
	
	public LginEvent() {
		CommonObjs.access = false;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LginEventHandler handler) {
		handler.onLgin(this);
	}

}
