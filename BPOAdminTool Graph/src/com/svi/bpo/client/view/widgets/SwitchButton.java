package com.svi.bpo.client.view.widgets;

import com.google.gwt.user.client.ui.Button;

public class SwitchButton extends Button{
	
	private String enableStyle;
	private String disableStyle;
	private boolean enable;
	
	public SwitchButton(String html){
		super(html);
	}
	
	public void setEnableStyle(String style){
		enableStyle = style;
	}
	
	public void setDisableStyle(String style){
		disableStyle = style;
	}
	
	public void enable(){
		enable = true;
		this.setStyleName(enableStyle);
	}
	
	public void disable(){
		enable = false;
		this.setStyleName(disableStyle);
	}
	
	public boolean isEnable(){
		return enable;
	}

}
