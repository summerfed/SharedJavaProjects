package com.svi.bpo.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.client.event.AdminViewEvent;
import com.svi.bpo.client.svc.LginSvcAsync;
import com.svi.bpo.constants.BPOCnstnts;
import com.svi.bpo.objects.UserAcctObj;
import com.svi.ssocookie.client.CookieUtil;
import com.svi.ssocookie.client.SSOCookieDto;

public class LginScnPresenter implements Presenter {
	
	public interface Display {
		Widget asWidget();
		TextBox getUsrTxtBx();
		PasswordTextBox getPswdTxtBx();
		Button getLginBtn();
		
		void notify(String text);
		void flip();
		void unFlip();
	}
	
	private final Display display;
	private HasWidgets container;
	private Timer timer;
	
	public LginScnPresenter(Display view) {
		this.display = view;
	}
	
	@Override
	public void go(HasWidgets container) {
		this.container = container;
		checkIfSSO();
	}
	
	private void checkIfSSO() {
		if (CommonObjs.ssoInfoDto != null && CommonObjs.ssoInfoDto.isSSO()) {
			extcCke();
		}else{
			bind();
		}
	}

	private void extcCke() {
		try {
			String pth = GWT.getModuleBaseURL();	
			String url[] = pth.split("/");		
			String getRootFldr = url[url.length-2];
			CookieUtil ckeUtil = new CookieUtil();
			String cke = ckeUtil.getEncryptedSystemCookie(getRootFldr);
			
			SSOCookieDto ssoDto = new SSOCookieDto(cke, CommonObjs.ssoInfoDto.getAppNm());	
			
			((LginSvcAsync) CommonObjs.factory.getService(BPOCnstnts.LGINSVC.getValue())).validateCrdntlsWithSSO(ssoDto.getUserId(), new AsyncCallback<UserAcctObj>() {
				
				@Override
				public void onSuccess(UserAcctObj result) {
					if (result.getLoginMsg().equals("Valid")) {
						
						CommonObjs.bpoUser = result;
						CommonObjs.eventBus.fireEvent(new AdminViewEvent());

					} else {
						LginScnPresenter.this.notify(result.getLoginMsg());
						display.getUsrTxtBx().setFocus(true);
						display.getUsrTxtBx().selectAll();
					}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					LginScnPresenter.this.notify("Validate Credentials Failed!");
					display.getUsrTxtBx().setFocus(true);
					display.getUsrTxtBx().selectAll();
				}
			});
					
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void bind() {
		
		container.clear();
		container.add(display.asWidget());
		
		display.getLginBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				login();
			}
		});
		
		display.getPswdTxtBx().addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(KeyCodes.KEY_ENTER == (int)event.getNativeKeyCode()){
					login();
				}
			}
		});
	}
	
	private void login(){
		
		if(display.getUsrTxtBx().getText().isEmpty()){

			LginScnPresenter.this.notify("Username is required");
			display.getUsrTxtBx().setFocus(true);
			display.getUsrTxtBx().selectAll();

		} else if(display.getPswdTxtBx().getText().isEmpty()) {

			LginScnPresenter.this.notify("Password is required");
			display.getUsrTxtBx().setFocus(true);
			display.getUsrTxtBx().selectAll();

		} else {

			((LginSvcAsync) CommonObjs.factory.getService(BPOCnstnts.LGINSVC.getValue())).validateCrdntls(
					display.getUsrTxtBx().getText(), 
					display.getPswdTxtBx().getText(), 
					new AsyncCallback<UserAcctObj>() {

						@Override
						public void onFailure(Throwable caught) {
							LginScnPresenter.this.notify("Validate Credentials Failed!");
							display.getUsrTxtBx().setFocus(true);
							display.getUsrTxtBx().selectAll();
						}

						@Override
						public void onSuccess(UserAcctObj result) {

							if (result.getLoginMsg().equals("Valid")) {

								CommonObjs.bpoUser = result;
								CommonObjs.eventBus.fireEvent(new AdminViewEvent());

							} else {
								LginScnPresenter.this.notify(result.getLoginMsg());
								display.getUsrTxtBx().setFocus(true);
								display.getUsrTxtBx().selectAll();
							}
						}

					});
		}
	}
	
	private void notify(String text){
		
		if(timer != null){
			timer.cancel();
		}
		
		display.notify(text);
		display.flip();

		timer = new Timer() {
			@Override
			public void run() {
				display.unFlip();
			}
		};

		timer.schedule(1000);
	}
	
//	private void setUserdetail(UserAcctObj user) {
//		CommonObjs.bpoUser = user;
//		((BpoSvcAsync)CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).setEndpointIds(user.getEndpoints(), new AsyncCallback<String>() {
//			
//			@Override
//			public void onSuccess(String result) {
//				System.out.println("Endpoints changed...");
//				CommonObjs.eventBus.fireEvent(new AdminViewEvent());
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//			}
//		});
//		
//	}

}

