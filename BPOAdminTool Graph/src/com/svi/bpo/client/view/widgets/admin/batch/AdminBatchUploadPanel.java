package com.svi.bpo.client.view.widgets.admin.batch;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.svi.bpo.constants.ResultCnstnts;
import com.svi.bpo.objects.UpldRsltObj;

public class AdminBatchUploadPanel extends Composite{

	private FlowPanel mainPnl;
	private BatchUploadWidget nodeUpldWidget;
	private BatchUploadWidget elemUpldWidget;
	private FlowPanel resultPnl;
	private ResultWidget resultWdgt;
	
	public AdminBatchUploadPanel(){
		
		nodeUpldWidget = new BatchUploadWidget("Upload Batch of Nodes", ResultCnstnts.NODE);
		nodeUpldWidget.setStyleName("admin-btch-upld-node-pnl");
		
		elemUpldWidget = new BatchUploadWidget("Upload Batch of Elements", ResultCnstnts.ELEMENTS);
		elemUpldWidget.setStyleName("admin-btch-upld-elem-pnl");
		
		resultPnl = new FlowPanel();
		resultPnl.setStyleName("admin-btch-result-pnl");
		
		mainPnl = new FlowPanel();
		mainPnl.setStyleName("admin-btch-upld-pnl");
		mainPnl.add(nodeUpldWidget);
		mainPnl.add(elemUpldWidget);

		hideResults();
		mainPnl.add(resultPnl);
		
		initWidget(mainPnl);
	}
	
	public void viewResults(UpldRsltObj result, ResultCnstnts type){
		resultPnl.getElement().getStyle().setDisplay(Display.BLOCK);
		resultPnl.clear();
		
		resultWdgt = new ResultWidget(result, type);
		resultWdgt.getCloseBtn().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				hideResults();
			}
		});
		resultWdgt.getViewDtlBtn().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if(resultWdgt.getViewDtlBtn().isEnable()){
					resultWdgt.seeDetails();
				} else {
					resultWdgt.hideDetails();
				}
			}
		});
		resultPnl.add(resultWdgt);
	}
	
	public void hideResults(){
		resultPnl.getElement().getStyle().setDisplay(Display.NONE);
		nodeUpldWidget.reset();
		elemUpldWidget.reset();
	}

	public BatchUploadWidget getNodeUpldWidget() {
		return nodeUpldWidget;
	}

	public BatchUploadWidget getElemUpldWidget() {
		return elemUpldWidget;
	}

	public ResultWidget getResultWdgt() {
		return resultWdgt;
	}
	
	public List<BatchUploadWidget> getUpldWidgts(){
		
		List<BatchUploadWidget> widgets = new ArrayList<BatchUploadWidget>();
		
		for(int i = 0; i < mainPnl.getWidgetCount(); i++){
			if(mainPnl.getWidget(i) instanceof BatchUploadWidget){
				widgets.add((BatchUploadWidget) mainPnl.getWidget(i));
			}
		}
		
		return widgets;
	}
	
}
