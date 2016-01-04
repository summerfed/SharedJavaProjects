package com.svi.bpo.client.view.widgets.admin.node;

public class ExceptionSearchableEndpointNodesWidget extends ExceptionEndPointsWidget {
	private PopSearchWidget searchWdgt;
public ExceptionSearchableEndpointNodesWidget(String endpointName) {
	super(endpointName);
	System.out.println(endpointName);
	searchWdgt = new PopSearchWidget("Search Element");
	}


	
	

	@Override
	public void loading(){
		super.loading();
		super.getHdr().remove(searchWdgt);
	}

	public void stopLoading(){
		super.stopLoading();
		super.getHdr().add(searchWdgt);
	}
	
	public PopSearchWidget getSearchWdgt() {
		return searchWdgt;
	}
	
}
