package com.svi.bpo.client.view.widgets.admin.node.elements;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.svi.bpo.objects.ElemDtlObj;

public class InsertElementPanel extends Composite{

	private FlowPanel txtBxWidgetPnl;
	private Button addElemBtn;
	private Button insertElemTxtBxsBtn;
	
	public InsertElementPanel(){
		
		FlowPanel header = new FlowPanel();
		header.setStyleName("insert-elem-hdr");
		
		txtBxWidgetPnl = new FlowPanel();
		txtBxWidgetPnl.setStyleName("insert-elem-txtbx-pnl");
		txtBxWidgetPnl.add(new InsertElementTextBoxes());
		
		insertElemTxtBxsBtn = new Button();
		insertElemTxtBxsBtn.setStyleName("insert-elem-add-txtbx-widget-btn");
		insertElemTxtBxsBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				txtBxWidgetPnl.add(new InsertElementTextBoxes());
				txtBxWidgetPnl.add(insertElemTxtBxsBtn);
			}
		});
		txtBxWidgetPnl.add(insertElemTxtBxsBtn);
		
		addElemBtn = new Button("Add");
		addElemBtn.setStyleName("insert-elem-btn");
		
		FlowPanel addNodeBtnPnl = new FlowPanel();
		addNodeBtnPnl.setStyleName("insert-elem-btn-pnl");
		addNodeBtnPnl.add(addElemBtn);
		
		FlowPanel body = new FlowPanel();
		body.setStyleName("insert-elem-body");
		body.add(txtBxWidgetPnl);
		body.add(addNodeBtnPnl);
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("insert-elem-pnl");
		mainPnl.add(body);
		mainPnl.add(header);
		
		initWidget(mainPnl);
		
		this.getElement().getStyle().setDisplay(Display.NONE);
	}
	
	public List<ElemDtlObj> getListOfElemsToBeAdded(){
		
		List<ElemDtlObj> elems = new ArrayList<ElemDtlObj>();
		
		for(Widget widget : txtBxWidgetPnl){
			
			if(widget instanceof InsertElementTextBoxes){
				InsertElementTextBoxes txbx = (InsertElementTextBoxes) widget;
				if(txbx.getElemIdTxtbx().getText().equals("")){
					return null;
				}
				ElemDtlObj elemObj = new ElemDtlObj(txbx.getElemIdTxtbx().getText(),
						txbx.getNodeNameTxtbx().getText(),
						txbx.getFilePointerTxtbx().getText(),
						Integer.parseInt(txbx.getPriorTxtbx().getText()),
						txbx.getExtra1Txtbx().getText(),
						txbx.getExtra2Txtbx().getText(),
						txbx.getExtra3Txtbx().getText());
				
				elems.add(elemObj);
			}
		}
		
		return elems;
	}
	
	public void show(){
		clearNodes();
		this.getElement().getStyle().setDisplay(Display.BLOCK);
	}
	
	public void hide(){
		this.getElement().getStyle().setDisplay(Display.NONE);
		clearNodes();
	}
	
	private void clearNodes(){
		txtBxWidgetPnl.clear();
		txtBxWidgetPnl.add(new InsertElementTextBoxes());
		txtBxWidgetPnl.add(insertElemTxtBxsBtn);
	}

	public Button getAddElemBtn() {
		return addElemBtn;
	}
	
}
