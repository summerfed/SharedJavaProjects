package com.svi.bpo.client.view;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.svi.bpo.client.presenter.LginScnPresenter.Display;

public class LginScn extends Composite implements Display {
	
	private TextBox usrNameTxtBox; 
	private PasswordTextBox pswdTxtBox;
	private Button lginBtn;
	private Button pswdBtn;
	private FlowPanel rootPnl;
	private Label notificationLbl;
	private FlipWidget flipWdgt;

	public LginScn() {
		rootPnl = new FlowPanel();
		rootPnl.setStyleName("lgnScn-mnPnl");
		rootPnl.add(bldBGImage());
//		rootPnl.add(bldLogo());
		rootPnl.add(bldInptPanel());
		rootPnl.add(bldFooter());
		initWidget(rootPnl);
	}
	
	public Widget bldBGImage() {
		final FlowPanel bgPnl = new FlowPanel();
		bgPnl.setStyleName("lgnScn-bgPnl");
		bgPnl.getElement().getStyle().setProperty("backgroundPosition", "50% 50%");
		
		rootPnl.addDomHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				
				int x = event.getNativeEvent().getClientX();
				int y = event.getNativeEvent().getClientY();
				float xPct = (((Window.getClientWidth()/2) - x) * 100.0f)/Window.getClientWidth();
				float yPct = (((Window.getClientHeight()/2) - y) * 100.0f)/Window.getClientHeight();

				bgPnl.getElement().getStyle().setProperty("backgroundPosition", 
						(50-xPct/10) + "% " +
						(50-yPct/10) + "%");
				
			}
		},MouseMoveEvent.getType());
		
		return bgPnl;
	}
	
	public Widget bldLogo() {
		FlowPanel logoPnl = new FlowPanel();
		logoPnl.setStyleName("lgnScn-sviLogo");
		return logoPnl;
	}

	private Widget bldInptPanel() {
		
		notificationLbl = new Label();
		notificationLbl.setStyleName("lgnScn-notif-lbl ");
		
		FlowPanel bpoLogo = new FlowPanel();
		FlowPanel notifyPnl = new FlowPanel();
		notifyPnl.add(notificationLbl);
		
		usrNameTxtBox = new TextBox();
		pswdTxtBox = new PasswordTextBox();
		lginBtn = new Button("Sign In");
		pswdBtn = new Button("Forgot Password?");
		
		pswdTxtBox.getElement().setAttribute("placeholder", "Password");
		usrNameTxtBox.getElement().setAttribute("placeholder", "Username");
		usrNameTxtBox.setStyleName("lgnScn-usr");
		pswdTxtBox.setStyleName("lgnScn-pswd");
		lginBtn.setStyleName("lgnScn-btn");
		pswdBtn.setStyleName("lgnScn-pswdBtn");
		bpoLogo.setStyleName("lgnScn-appLogo");
		notifyPnl.setStyleName("lgnScn-notif-pnl");

		flipWdgt = new FlipWidget(bpoLogo, notifyPnl);
		
		FlowPanel inputTable = new FlowPanel();
		inputTable.setStyleName("lgnScn-inptPnl");
		inputTable.add(flipWdgt);
		inputTable.add(usrNameTxtBox);
		inputTable.add(pswdTxtBox);
		inputTable.add(lginBtn);
		inputTable.add(pswdBtn);
		
		return inputTable;
	}
	
	public FlowPanel bldFooter() {
		FlowPanel footer = new FlowPanel();
		Label foot = new Label("Copyrights 2002 - 2014 SVI All rights reserved");
		footer.setStyleName("lgnScn-foot");
		footer.add(bldLogo());
		footer.add(foot);
		return footer;
	}

	@Override
	public void notify(String text){
		notificationLbl.setText(text);
	}

	@Override
	public TextBox getUsrTxtBx() {
		return usrNameTxtBox;
	}
	
	@Override
	public PasswordTextBox getPswdTxtBx() {
		return pswdTxtBox;
	}
	
	@Override
	public Button getLginBtn() {
		return lginBtn;
	}

	@Override
	public void flip() {
		flipWdgt.flip();
	}

	@Override
	public void unFlip() {
		flipWdgt.unFlip();
	}

	public class FlipWidget extends Composite{
		
		private FlowPanel flipper;

		public FlipWidget(ComplexPanel frontPnl, ComplexPanel backPnl){
			
			frontPnl.addStyleName("front");
			backPnl.addStyleName("back");
			
			flipper = new FlowPanel();
			flipper.setStyleName("flipper");
			flipper.add(frontPnl);
			flipper.add(backPnl);
			
			FlowPanel mainPnl = new FlowPanel();
			mainPnl.setStyleName("flip-container");
			mainPnl.getElement().setAttribute("ontouchstart", "this.classList.toggle('hover')");
			mainPnl.add(flipper);
			
			initWidget(mainPnl);
			
		}
		
		public void flip(){
			flipper.getElement().getStyle().setProperty("transform", "rotateY(180deg)");
		}
		
		public void unFlip(){
			flipper.getElement().getStyle().setProperty("transform", "rotateY(0deg)");
		}
		
	}
	
}
