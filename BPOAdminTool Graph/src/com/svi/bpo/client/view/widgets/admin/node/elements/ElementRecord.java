package com.svi.bpo.client.view.widgets.admin.node.elements;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.svi.bpo.objects.ElemDtlObj;

public class ElementRecord extends Composite {
	
	private ElemDtlObj elem;
	
	ElementRecord(ElemDtlObj elem){
		
		this.elem = elem;
		
		HTMLPanel elemIdCell = getStyledPnl(elem.getElementId(), "elem-tbl-id-col");
		HTMLPanel statusCell = getStyledPnl(elem.getStatus(), "elem-tbl-status-col");
		HTMLPanel priorCell = getStyledPnl(String.valueOf(elem.getPriority()), "elem-tbl-prior-col");
//		HTMLPanel waitTimeCell = getStyledPnl(elem.getWaitStartTime(), "elem-tbl-wait-time");
//		HTMLPanel procTimeCell = getStyledPnl(elem.getProcessStartTime(), "elem-tbl-proc-time");
	
		HTMLPanel wrkrCell = getStyledPnl(elem.getWorkerId(), "elem-tbl-wrkr-col");

		FlowPanel mainPnl = new FlowPanel();
		mainPnl.add(elemIdCell);
		mainPnl.add(statusCell);
		mainPnl.add(priorCell);
//		mainPnl.add(waitTime);
//		mainPnl.add(procTime);
		mainPnl.add(wrkrCell);
		
		initWidget(mainPnl);
	}

	private HTMLPanel getStyledPnl(String text, String style) {
		HTMLPanel elemIdCell = new HTMLPanel(text);
		elemIdCell.setStyleName(style);
		return elemIdCell;
	}
	
	public ElemDtlObj getElemObj() {
		return elem;
	}

	public void setNode(ElemDtlObj elem) {
		this.elem = elem;
	}
}
