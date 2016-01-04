package com.svi.bpo.client.view.widgets;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
//import com.svi.webgnrcrtvl.client.view.intrfc.WidgetConstructInterface;
//import com.svi.webgnrcrtvl.object.SrchObj;

public class SimplePagingWidget extends Composite  {
	
	private Label resultLbl;
	private Label infoLbl;
	private Button nextBtn;
	private Button prevBtn;
	private Button lastBtn;
	private Button firstBtn;
	private int total;
	private Button viewAlbum;
	
	public int getTotal() {
		return total;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	private int start;
	private int end;
	
	public SimplePagingWidget() {
		AbsolutePanel mainPanel = constructPanel("pager-mainPnl");

		resultLbl = constructLabel("", "pager-resultLbl");

		AbsolutePanel container = constructPanel("pager-containerPnl");
	
		infoLbl = new Label();
		infoLbl.setStyleName("pager-containerLbl");
		nextBtn = new Button(">");
		nextBtn.setStyleName("pager-containerBtn-rht1");
		prevBtn = new Button("<");
		prevBtn.setStyleName("pager-containerBtn-lft1");
		lastBtn = new Button(">>");
		lastBtn.setStyleName("pager-containerBtn-rht2");
		firstBtn = new Button("<<");
		firstBtn.setStyleName("pager-containerBtn-lft2");
		
		container.add(firstBtn);
		container.add(prevBtn);
		container.add(infoLbl);
		container.add(nextBtn);
		container.add(lastBtn);
		
		mainPanel.add(resultLbl);
		mainPanel.add(container);		
		
		viewAlbum = new Button();
		viewAlbum.setStyleName("view-album");
		
//		viewAlbum.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				Widget w = spw.getParent();
//				Window.alert("CLICKED");
//				PopupPanel pnl = new PopupPanel(true);
//				
//				pnl.add(new PhotoAlbumWdgt(()w).asWidget());
//				
//				pnl.show();
//				
//			}
//		});

		
//		mainPanel.add(viewAlbum);
		
		initWidget(mainPanel);		
	}
	
	public void enableLeft(){
		prevBtn.setStyleName("pager-containerBtn-lft1");
		firstBtn.setStyleName("pager-containerBtn-lft2");
	}
	public void disableLeft(){
		prevBtn.setStyleName("pager-containerBtn-lft1-disabled");
		firstBtn.setStyleName("pager-containerBtn-lft2-disabled");
	}
	public void enableRight(){
		nextBtn.setStyleName("pager-containerBtn-rht1");
		lastBtn.setStyleName("pager-containerBtn-rht2");
	}
	public void disableRight(){
		
		nextBtn.setStyleName("pager-containerBtn-rht1-disabled");
		lastBtn.setStyleName("pager-containerBtn-rht2-disabled");
	}

	public AbsolutePanel constructPanel(String css) {
		AbsolutePanel pnl = new AbsolutePanel();
		pnl.setStyleName(css);
		return pnl;
	}


	public Label constructLabel(String text, String css) {
		Label lbl = new Label(text);
		lbl.setStyleName(css);
		return lbl;
	}


	public Button constructButton(String text, String css) {
		Button btn = new Button();
		btn.setText(text);
		btn.setStyleName(css);
		return btn;
	}
	
	
	public Label getResultLbl() {
		return resultLbl;
	}

	public Button getLastBtn() {
		return lastBtn;
	}

	public Button getViewAlbum() {
		return viewAlbum;
	}

	public Button getFirstBtn() {
		return firstBtn;
	}

	public void setText(int total, int start, int end){		
		this.total = total;
		this.start = start;
		this.end = end;
		infoLbl.setText(" "+start +" to " +end+ " of "+total+" ");
	}
	
	public Button getNextBtn(){
		return nextBtn;
	}
	
	public Button getPrevBtn(){
		return prevBtn;
	}
	
	
}
