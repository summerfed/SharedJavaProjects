package com.svi.bpo.client.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.client.svc.BpoSvcAsync;
import com.svi.bpo.client.view.widgets.dshbrd.EndPointsExceptionDashboardWidget;
import com.svi.bpo.client.view.widgets.dshbrd.EndpointNodesDshBrdWidget;
import com.svi.bpo.constants.BPOCnstnts;
import com.svi.bpo.constants.Notification;
import com.svi.bpo.objects.EndpointObj;
import com.svi.bpo.objects.ExceptionNodeDshBrdObj;
import com.svi.bpo.objects.NodeDshBrdObj;

public class DashboardViewScnPresenter  implements Presenter{

	public interface Display {
		public Widget asWidget();
		public void notify(Notification error, String string);
		EndpointNodesDshBrdWidget getDashBoard();
		FlowPanel getTblPnl();
		void addEndpoint(EndpointNodesDshBrdWidget widget,List<NodeDshBrdObj> nodes);
		void addExceptionEndpoint(EndPointsExceptionDashboardWidget widget,List<ExceptionNodeDshBrdObj> nodes);
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
						
						addOnClickPopUpHandlers(widget.getRowPanels());
						
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
	
	/// Adds on click handler on every row of flowPanel
	protected void addOnClickPopUpHandlers(HashMap<String, FlowPanel> hashMap) {
		
		for(Map.Entry<String, FlowPanel> entry : hashMap.entrySet()){
			final String nodeId = entry.getKey();
			FlowPanel panel = entry.getValue();
			
			panel.addDomHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent paramClickEvent) {
					System.err.println(GWT.getModuleBaseURL());
					System.out.println("clicked "+nodeId);
					
					String rptUrl = GWT.getModuleBaseURL() + "../reportgraphs/index.html?nodeId=" + nodeId;
					Window.open(rptUrl, "_blank", "");
//					Window.open("http://127.0.0.1:8888/reportgraphs/index.html?nodeId="+nodeId, "_blank", "");
				}
			}, ClickEvent.getType());
		}
		
	}

	/**
	 * Displays Exception Tab DashBoard
	 *  */
	private void displayExceptionNodes(){
		
		for (final EndpointObj dtls : CommonObjs.bpoUser.getEndpoints()) {

			if(dtls.isActive()){

				final EndPointsExceptionDashboardWidget widget = new EndPointsExceptionDashboardWidget(dtls.getLabel());

				((BpoSvcAsync)CommonObjs.factory.getService(BPOCnstnts.BPOSVC.getValue())).getSummarizedExNodes(
						dtls.getEndpointId(), new AsyncCallback<List<ExceptionNodeDshBrdObj>>() {

					@Override
					public void onSuccess(List<ExceptionNodeDshBrdObj> result) {
						display.addExceptionEndpoint(widget, result);
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
	
	private void bind() {
		container.clear();
		container.add(display.asWidget());
	}
}
