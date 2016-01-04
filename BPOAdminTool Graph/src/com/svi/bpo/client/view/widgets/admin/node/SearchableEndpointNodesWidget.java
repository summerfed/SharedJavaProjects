package com.svi.bpo.client.view.widgets.admin.node;



public class SearchableEndpointNodesWidget extends EndpointNodesWidget {

	private PopSearchWidget searchWdgt;
	
	public SearchableEndpointNodesWidget(String endpointName) {
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
