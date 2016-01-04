package com.svi.bpo.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.svi.bpo.constants.Notification;

/***
 * 
 * @author rfuentes
 *
 */
public class ReportsViewScnPresenter implements Presenter {

	public interface Display {
		public Widget asWidget();
		public void notify(Notification error, String string);
		//public IFrameElement getIFrame(); 
	}
	
	private Display display;

	private HasWidgets container;
	
	public ReportsViewScnPresenter(Display view) {
		this.display = view;
	}
	
	@Override
	public void go(HasWidgets container) {
		this.container = container;	
		bind();
	}

	private void bind() {
		container.clear();
		container.add(display.asWidget());
	}
	
}
