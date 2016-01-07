package com.svi.bpo.client.view.widgets.admin.node.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.svi.bpo.client.view.widgets.SimplePagingWidget;
import com.svi.bpo.objects.ElemDtlObj;



public class ElementTable extends Composite{
	
	private FlowPanel body;
	private Map<String, ElemDtlObj> elementsSelected;
	
	public ElementTable(){
		
		elementsSelected = new HashMap<String, ElemDtlObj>();
		
		FlowPanel header = new FlowPanel();
		header.setStyleName("elem-tbl-hdr");
		
		HTMLPanel elemIdHdr = getStyledPnl("Element ID", "elem-tbl-id-col");
		HTMLPanel statusHdr = getStyledPnl("Status", "elem-tbl-status-col");
		HTMLPanel priorHdr = getStyledPnl("Priority", "elem-tbl-prior-col");
//		HTMLPanel waitTime = getStyledPnl("Waiting Start Time", "elem-tbl-wait-time");
//		HTMLPanel procTime = getStyledPnl("Process Start Time", "elem-tbl-proc-time");
		HTMLPanel arrvHdr = getStyledPnl("Arrival Time", "elem-tbl-arrv-col");
		HTMLPanel compHdr = getStyledPnl("Comp. Time", "elem-tbl-comp-col");

		HTMLPanel wrkrHdr = getStyledPnl("Worker Name", "elem-tbl-wrkr-col");
				HTMLPanel flowHdr = getStyledPnl("Flow", "elem-tbl-flow-col");
		HTMLPanel statColorHdr = getStyledPnl("  ", "elem-tbl-color-col");
		header.add(elemIdHdr);
		header.add(statusHdr);
		header.add(priorHdr);
		header.add(arrvHdr);
		header.add(compHdr);
		header.add(wrkrHdr);
		header.add(flowHdr);
		header.add(statColorHdr);
		body = new FlowPanel();
		body.setStyleName("elem-tbl-body");
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("elem-tbl-pnl");
		mainPnl.add(header);
		mainPnl.add(body);
		
		initWidget(mainPnl);
	}

	private HTMLPanel getStyledPnl(String text, String style) {
		HTMLPanel elemIdCell = new HTMLPanel(text);
		elemIdCell.setStyleName(style);
		return elemIdCell;
	}

//	public FlowPanel getTableContents() {
//		return body;
//	}
	
	public void add(ElementRecord record){
		body.add(record);
	}
	
	public void clear(){
		body.clear();
	}
	
	public Map<String, ElemDtlObj> getMapOfSelectedElements(){
		return elementsSelected;
	}

	public List<ElemDtlObj> getElementsSelected() {
		List<ElemDtlObj> elements = new ArrayList<ElemDtlObj>();
		elements.addAll(elementsSelected.values());
		return elements;
	}
	
	public void clearSelectedElements(){
		elementsSelected.clear();
	}
	
}
