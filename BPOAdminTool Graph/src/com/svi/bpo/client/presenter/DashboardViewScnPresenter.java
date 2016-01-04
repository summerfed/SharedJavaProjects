package com.svi.bpo.client.presenter;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.client.svc.BpoSvcAsync;
import com.svi.bpo.client.view.widgets.dshbrd.EndpointNodesDshBrdWidget;
import com.svi.bpo.constants.BPOCnstnts;
import com.svi.bpo.constants.Notification;
import com.svi.bpo.objects.EndpointObj;
import com.svi.bpo.objects.NodeDshBrdObj;

public class DashboardViewScnPresenter  implements Presenter{

	public interface Display {
		public Widget asWidget();
		public void notify(Notification error, String string);
		EndpointNodesDshBrdWidget getDashBoard();
		FlowPanel getTblPnl();
		void addEndpoint(EndpointNodesDshBrdWidget widget,List<NodeDshBrdObj> nodes);
	}
	
	private Display display;

	private HasWidgets container;
	
	public DashboardViewScnPresenter(Display view) {
		this.display = view;
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;	
		bind();
		displayNodes();
		displayExceptionNodes();
	}

	/**
	 * Displays Node Table
	 * 
	 * @author ecruz
	 */
	private void displayNodes(){

		for (final EndpointObj dtls : CommonObjs.bpoUser.getEndpoints()) {

			if(dtls.isActive()){

				final EndpointNodesDshBrdWidget widget = new EndpointNodesDshBrdWidget(dtls.getLabel());

				((BpoSvcAsync)CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getSummarizedNodes(
						dtls.getEndpointId(), new AsyncCallback<List<NodeDshBrdObj>>() {

					@Override
					public void onSuccess(List<NodeDshBrdObj> result) {
						display.addEndpoint(widget, result);
						widget.stopLoading();
					}

					@Override
					public void onFailure(Throwable caught) {
						display.notify(Notification.ERROR, "Get Node Failed");
						widget.stopLoading();
					}
				});

			}
		}
		
	}
	
	/**
	 * Displays Exception Tab DashBoard
	 *  */
	private void displayExceptionNodes(){
		
		
	}
	
	private void bind() {
		container.clear();
		container.add(display.asWidget());
	}
}
