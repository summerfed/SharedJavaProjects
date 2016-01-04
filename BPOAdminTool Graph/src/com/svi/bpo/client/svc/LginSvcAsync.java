package com.svi.bpo.client.svc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.svi.bpo.objects.BpoDtlsObj;
import com.svi.bpo.objects.SSOInfoDto;
import com.svi.bpo.objects.UserAcctObj;

public interface LginSvcAsync {

	void getScnToken(String type, AsyncCallback<String> callback);

	void getBpoDtls(AsyncCallback<BpoDtlsObj> callback);

	void validateCrdntls(String user, String pass,
			AsyncCallback<UserAcctObj> callback);

	void getSsoInfo(AsyncCallback<SSOInfoDto> callback);

	void validateCrdntlsWithSSO(String user, AsyncCallback<UserAcctObj> callback);

}
