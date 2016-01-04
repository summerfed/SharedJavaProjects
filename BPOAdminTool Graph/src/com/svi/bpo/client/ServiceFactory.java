package com.svi.bpo.client;

import com.google.gwt.core.client.GWT;
import com.svi.bpo.client.svc.BpoSvc;
import com.svi.bpo.client.svc.BpoSvcAsync;
import com.svi.bpo.client.svc.LginSvc;
import com.svi.bpo.client.svc.LginSvcAsync;
import com.svi.bpo.constants.BPOCnstnts;

public class ServiceFactory {

	public ServiceFactory() {

	}

	public Object getService(String criteria) {

		if (criteria.equalsIgnoreCase(BPOCnstnts.LGINSVC.getValue())) {
			LginSvcAsync rpcService = GWT.create(LginSvc.class);
			return rpcService;
		} else if(criteria.equalsIgnoreCase(BPOCnstnts.BPOSVC.getValue())){
			BpoSvcAsync rpcService = GWT.create(BpoSvc.class);
			return rpcService;
		}
		return null;
	}
}
