package com.svi.bpo.client.view.widgets.admin.node;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.svi.bpo.client.CommonObjs;

public class PopSearchWidget extends Composite {

	private TextBox txtbx;
	private Button btn;
	
	public PopSearchWidget(String value){
		
		txtbx = new TextBox();
		txtbx.getElement().setAttribute("placeholder", value);
		txtbx.setStyleName("pop-srch-wdgt-txtbx");
		txtbx.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(KeyCodes.KEY_ENTER == (int)event.getNativeKeyCode()){
					btn.click();
				}
			}
		});
		
		btn = new Button();
		btn.setStyleName("pop-srch-wdgt-btn");
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("pop-srch-wdgt");
		mainPnl.add(txtbx);
		mainPnl.add(btn);

		if(CommonObjs.bpoProps.getMode().equalsIgnoreCase("Y")){
			txtbx.setVisible(false);
			btn.setVisible(false);
		}
		
		initWidget(mainPnl);
	}

	public String getText(){
		return txtbx.getText();
	}

	public Button getBtn() {
		return btn;
	}
}
