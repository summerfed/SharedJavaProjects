package com.svi.bpo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.svi.bpo.client.event.AdminViewEvent;
import com.svi.bpo.client.event.AdminViewEventHandler;
import com.svi.bpo.client.event.DashboardViewEvent;
import com.svi.bpo.client.event.DashboardViewEventHandler;
import com.svi.bpo.client.event.LginEvent;
import com.svi.bpo.client.event.LginEventHandler;
import com.svi.bpo.client.event.ReportsViewEvent;
import com.svi.bpo.client.event.ReportsViewEventHandler;
import com.svi.bpo.client.presenter.AdminViewScnPresenter;
import com.svi.bpo.client.presenter.DashboardViewScnPresenter;
import com.svi.bpo.client.presenter.LginScnPresenter;
import com.svi.bpo.client.presenter.Presenter;
import com.svi.bpo.client.presenter.ReportsViewScnPresenter;
import com.svi.bpo.client.svc.LginSvcAsync;
import com.svi.bpo.client.view.AdminViewScn;
import com.svi.bpo.client.view.DashboardViewScn;
import com.svi.bpo.client.view.LginScn;
import com.svi.bpo.client.view.ReportsViewScn;
import com.svi.bpo.constants.BPOCnstnts;
import com.svi.bpo.objects.BpoDtlsObj;
import com.svi.bpo.objects.SSOInfoDto;
import com.svi.ssocookie.client.CookieUtil;
public class AppController implements Presenter, ValueChangeHandler<String> {

	private HasWidgets container;
	private CookieUtil ckeUtil = new CookieUtil();	
	
	public AppController() {
		bind();
	}

	public void bind() {
		loadBpoDtls();
		CommonObjs.eventBus.addHandler(AdminViewEvent.TYPE, new AdminViewEventHandler() {

			@Override
			public void onAdminView(AdminViewEvent adminViewEvent) {
				History.newItem("admin");
				History.fireCurrentHistoryState();
			}
		});

		CommonObjs.eventBus.addHandler(DashboardViewEvent.TYPE, new DashboardViewEventHandler() {

			@Override
			public void onDashboardView(DashboardViewEvent dashboardViewEvent) {
				History.newItem("dashboard");
				History.fireCurrentHistoryState();
			}
		});

		CommonObjs.eventBus.addHandler(ReportsViewEvent.TYPE, new ReportsViewEventHandler() {

			@Override
			public void onStatisticsView(ReportsViewEvent statisticsViewEvent) {
				History.newItem("reports");
				History.fireCurrentHistoryState();
			}
		});


		CommonObjs.eventBus.addHandler(LginEvent.TYPE, new LginEventHandler() {

			@Override
			public void onLgin(LginEvent lginEvent) {
				if (CommonObjs.access) {
					History.newItem("login");
				}
				Window.Location.reload();
			}
		});

		History.addValueChangeHandler(this);
	}

	private void loadBpoDtls() {
		((LginSvcAsync) CommonObjs.factory.getService(BPOCnstnts.LGINSVC.getValue())).getBpoDtls(new AsyncCallback<BpoDtlsObj>() {

			@Override
			public void onSuccess(BpoDtlsObj result) {
				CommonObjs.bpoProps = result;
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Get BPO Details Failed");
			}
		});
	}

	@Override
	public void go(HasWidgets container) {
		
		this.container = container;
		
		isSso();
		
	}

	private void isSso() {
		((LginSvcAsync) CommonObjs.factory.getService(BPOCnstnts.LGINSVC.getValue())).getSsoInfo(new AsyncCallback<SSOInfoDto>() {
			
			@Override
			public void onSuccess(SSOInfoDto result) {
				CommonObjs.ssoInfoDto = result;
				
				if(result != null){
					if(!CommonObjs.ssoInfoDto.isDisconnected()){
						if (CommonObjs.ssoInfoDto.isSSO()) {
							if (isExstgCke()) {
								gotAdmnScn();
							} else Window.Location.replace(CommonObjs.ssoInfoDto.getSsoPrtlUrl());
						} 
						else goToLoginScr();
					}
				} else {
					// TODO : Eric alert message
					Window.alert("Cannot connect to the server.");
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				//TODO : Eric alert message
				Window.alert("checkSSO FAILED");
			}
		});

	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {

		String token = event.getValue();
		Presenter presenter = null;

		if (token != null) {
			if (CommonObjs.access) {

				if (History.getToken().equals("admin")) {
					presenter = new AdminViewScnPresenter(new AdminViewScn());
				} else
					if (History.getToken().equals("dashboard")){
						presenter = new DashboardViewScnPresenter(new DashboardViewScn());
					} else
						if (History.getToken().equals("reports")){
							presenter = new ReportsViewScnPresenter(new ReportsViewScn());
						}

			} else {
				presenter = new LginScnPresenter(new LginScn());
			}

			if (presenter != null) {
				presenter.go(container);
			}

		}

	}

	private void goToLoginScr() {
		History.newItem("login");
		History.fireCurrentHistoryState();
	}
	
	private void gotAdmnScn() {
		History.newItem("admin");
		History.fireCurrentHistoryState();
	}

	private Boolean isExstgCke(){
		try {	
			String pth = GWT.getModuleBaseURL();
			String url[] = pth.split("/");		
			String getRootFldr = url[url.length-2];		
			String cke = ckeUtil.getEncryptedSystemCookie(getRootFldr);
			
			if (cke != null){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
