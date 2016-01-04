package com.svi.bpo.client.view.widgets.admin.batch;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.svi.bpo.client.view.widgets.SwitchButton;
import com.svi.bpo.constants.ResultCnstnts;
import com.svi.bpo.objects.UpldRsltObj;

public class ResultWidget extends Composite {
	
	private UpldRsltObj result;
	private ResultCnstnts type;
	private SwitchButton viewDtlBtn;
	private Label viewDtlLbl;
	private FlowPanel dtlPnl;
	private Button closeBtn;
	
	ResultWidget(UpldRsltObj result, ResultCnstnts type){
		
		this.result = result;
		this.type = type;
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("upld-rslt-wdgt");
		mainPnl.add(bldSummryPnl());
		mainPnl.add(bldDetailPnl());
		
		initWidget(mainPnl);
	}
	
	private FlowPanel bldSummryPnl(){
		
		closeBtn = new Button();
		closeBtn.setStyleName("upld-rslt-smmry-close-btn");
		
		HTMLPanel hdr = new HTMLPanel("Upload Results");
		hdr.setStyleName("upld-rslt-smmry-hdr");
		hdr.add(closeBtn);
		
		HTMLPanel cmpltdLbl = new HTMLPanel("Completed:");
		HTMLPanel cmpltdNo = new HTMLPanel(result.getNoOfCompleted());
		FlowPanel cmpltdDtlPnl = new FlowPanel();
		cmpltdDtlPnl.add(cmpltdLbl);
		cmpltdDtlPnl.add(cmpltdNo);
		
		HTMLPanel errorLbl = new HTMLPanel("Error:");
		HTMLPanel errorNo = new HTMLPanel(result.getNoOfError());
		FlowPanel errorDtlPnl = new FlowPanel();
		errorDtlPnl.add(errorLbl);
		errorDtlPnl.add(errorNo);
		
		FlowPanel body = new FlowPanel();
		body.setStyleName("upld-rslt-smmry-body");
		body.add(cmpltdDtlPnl);
		body.add(errorDtlPnl);
		
		viewDtlBtn = new SwitchButton("");
		viewDtlBtn.setEnableStyle("vw-dtl-btn-see");
		viewDtlBtn.setDisableStyle("vw-dtl-btn-hide");
		viewDtlLbl = new Label();
		
		FlowPanel ftr = new FlowPanel();
		ftr.setStyleName("upld-rslt-smmry-ftr");
		ftr.add(viewDtlBtn);
		ftr.add(viewDtlLbl);
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("upld-rslt-smmry-pnl");
		mainPnl.add(hdr);
		mainPnl.add(body);
		mainPnl.add(ftr);
		
		return mainPnl;
		
	}
	
	private FlowPanel bldDetailPnl(){

		HTMLPanel hdr1;
		
		if(type.equals(ResultCnstnts.NODE)){
			hdr1 = new HTMLPanel("Node ID");
		} else if(type.equals(ResultCnstnts.ELEMENTS)){
			hdr1 = new HTMLPanel("Element ID");
		} else {
			hdr1 = new HTMLPanel("");
		}

		HTMLPanel hdr2 = new HTMLPanel("Result");
		FlowPanel hdr = new FlowPanel();
		hdr.setStyleName("upld-rslt-dtl-hdr");
		hdr.add(hdr1);
		hdr.add(hdr2);
		
		FlowPanel body = new FlowPanel();
		body.setStyleName("upld-rslt-dtl-body");
		
		FlowPanel dataPnl;
		String[] keys;
		for(String key : result.getDtlList()){
			dataPnl = new FlowPanel();
			keys = key.split("\\|");
			dataPnl.add(new HTMLPanel(keys[0]));
			dataPnl.add(new HTMLPanel(keys[1]));
			body.add(dataPnl);
		}
		
		dtlPnl = new FlowPanel();
		dtlPnl.setStyleName("upld-rslt-dtl-pnl");
		dtlPnl.add(hdr);
		dtlPnl.add(body);
		
		hideDetails();
		
		return dtlPnl;
	}
	
	public void hideDetails(){
		viewDtlLbl.setText("See Details");
		viewDtlBtn.enable();
		dtlPnl.getElement().getStyle().setDisplay(Display.NONE);
	}
	
	public void seeDetails(){
		viewDtlLbl.setText("Hide Details");
		viewDtlBtn.disable();
		dtlPnl.getElement().getStyle().setDisplay(Display.BLOCK);
	}

	public SwitchButton getViewDtlBtn() {
		return viewDtlBtn;
	}

	public Button getCloseBtn() {
		return closeBtn;
	}
	
}
