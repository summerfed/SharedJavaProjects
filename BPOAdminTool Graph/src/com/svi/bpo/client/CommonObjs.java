package com.svi.bpo.client;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.FlowPanel;
import com.svi.bpo.client.view.widgets.NotificationWidget;
import com.svi.bpo.constants.Notification;
import com.svi.bpo.objects.BpoDtlsObj;
import com.svi.bpo.objects.SSOInfoDto;
import com.svi.bpo.objects.UserAcctObj;

public class CommonObjs {

	public final static HandlerManager eventBus = new HandlerManager(null);
	public final static ServiceFactory factory = new ServiceFactory();
	public static boolean access;
	public static BpoDtlsObj bpoProps = new BpoDtlsObj();
	public static UserAcctObj bpoUser = new UserAcctObj();
	public static SSOInfoDto ssoInfoDto = new SSOInfoDto();
	public static FlowPanel notifyPanel;
	
	public static int ElemRecordCount = 0;
	public static int ElemPageCount = 0;
	public static int ElemPageLimit = 10;
	public static int ElemPageCurrPage = 1;
	public static int ElemPageCurrNum = 0;
	
	public static void notify(Notification value, String text){
		notifyPanel.add(new NotificationWidget(value, text));
	}
}
