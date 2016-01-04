package com.svi.bpo.constants;

public enum Notification {
	
	SUCCESS("notif-logo-success", "notif-bg-success"),
	INFO("notif-logo-info", "notif-bg-info"),
	WARNING("notif-logo-warning", "notif-bg-warning"),
	ERROR("notif-logo-error", "notif-bg-error")
	
	;
	
	private String logoStyle;
	private String widgetStyle;
	
	Notification(String logoStyle, String widgetStyle) {
		this.logoStyle = logoStyle;
		this.widgetStyle = widgetStyle;
	}

	public String getLogoStyle() {
		return this.logoStyle;  
    }

	public String getWidgetStyle() {
		return this.widgetStyle;  
    }
}
