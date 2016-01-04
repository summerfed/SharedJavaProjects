package com.svi.bpo.client.svc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.svi.bpo.objects.BpoDtlsObj;
import com.svi.bpo.objects.SSOInfoDto;
import com.svi.bpo.objects.UserAcctObj;

@RemoteServiceRelativePath("lginSvc")
public interface LginSvc extends RemoteService{

	public String getScnToken(String type);

	public BpoDtlsObj getBpoDtls();

	public UserAcctObj validateCrdntls(String user, String pass);

	public SSOInfoDto getSsoInfo();

	public UserAcctObj validateCrdntlsWithSSO(String user);

}
