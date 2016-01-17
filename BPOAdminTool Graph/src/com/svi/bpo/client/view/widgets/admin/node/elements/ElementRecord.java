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
		HTMLPanel compCell = getStyledPnl(elem.getCompTime(), "elem-tbl-comp-col");
		HTMLPanel arrvCell = getStyledPnl(elem.getArrivalTime(), "elem-tbl-arrv-col");
		HTMLPanel wrkrCell = getStyledPnl(elem.getWorkerId(), "elem-tbl-wrkr-col");
		HTMLPanel flowCell = getStyledPnl(elem.getNormalFlow(), "elem-tbl-flow-col");
		HTMLPanel statusColorCell = getStyledPnl("COLOR", "elem-tbl-color-col");
		
		
		if(elem.getProcessingState() == 1){
		//statusCell = new HTMLPanel(" ");
			statusColorCell.setStyleName("node-status-green");
			statusColorCell.setTitle(elem.getProcessingDesc());
		}
		else if (elem.getProcessingState() == 3) {
	//	statusCell = new HTMLPanel(" ");
			statusColorCell.setStyleName("node-status-red");
			statusColorCell.setTitle(elem.getProcessingDesc());
		}
		else if (elem.getProcessingState() == 2) {
			//	statusCell = new HTMLPanel(" ");
					statusColorCell.setStyleName("node-status-orange");
					statusColorCell.setTitle(elem.getProcessingDesc());
				}
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.add(elemIdCell);
		mainPnl.add(statusCell);
		mainPnl.add(priorCell);
		mainPnl.add(arrvCell);
		mainPnl.add(compCell);
//		mainPnl.add(waitTime);
//		mainPnl.add(procTime);
		mainPnl.add(wrkrCell);
		mainPnl.add(flowCell);
		mainPnl.add(statusColorCell);
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
