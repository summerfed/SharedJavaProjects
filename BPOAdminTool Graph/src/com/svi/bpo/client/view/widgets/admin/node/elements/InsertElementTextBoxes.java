package com.svi.bpo.client.view.widgets.admin.node.elements;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class InsertElementTextBoxes extends Composite{
	
	private LabeledTxtBx elemIdTxtbx;
	private LabeledTxtBx priorTxtbx;
	private LabeledTxtBx workerIdTxtbx;
	public static  LabeledTxtBx nodeNameTxtbx;// made public by jm
	private LabeledTxtBx extra1Txtbx;
	private LabeledTxtBx filePointerTxtbx;
	
	
	private LabeledTxtBx extra2Txtbx;
	private LabeledTxtBx extra3Txtbx;
	
	private Label errorMsgPrioity;
	public InsertElementTextBoxes(){
		
		elemIdTxtbx = new LabeledTxtBx("Element ID:",true);
		elemIdTxtbx.setStyleName("insert-elem-txtbx-widget-input");
		priorTxtbx = new LabeledTxtBx("Priority:");
		priorTxtbx.setStyleName("insert-elem-txtbx-widget-input");
		errorMsgPrioity = new Label("L");
		errorMsgPrioity.setStyleName("errorMsgPriority");
		errorMsgPrioity.setVisible(false);
		priorTxtbx.getTxtBx().addFocusHandler(new FocusHandler(){

			@Override
			public void onFocus(FocusEvent arg0) {
				// TODO Auto-generated method stub
			//	priorTxtbx.getTxtBx().removeStyleName("error-border");
				errorMsgPrioity.setVisible(false);
			}});
	
		priorTxtbx.getTxtBx().addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent paramBlurEvent) {
				// TODO Auto-generated method stub
			if("".equals(priorTxtbx.getTxtBx().getText()))
				return ;
				int num = 0;	
				try{
					num = Integer.parseInt(priorTxtbx.getTxtBx().getText());
					
				}
			catch(Exception e){
				errorMsgPrioity.setVisible(true);
				errorMsgPrioity.setText("Numbers only on the priority textbox!");
			//	Window.alert("Numbers only on the priority textbox!"); 
				//priorTxtbx.getTxtBx().setStyleName("error-border");
				priorTxtbx.getTxtBx().setText("");
			}
				if (num < 0){
					errorMsgPrioity.setVisible(true);
					errorMsgPrioity.setText("No number below zero on priority textbox!");

				//	priorTxtbx.getTxtBx().setStyleName("error-border");
				//	Window.alert("No number below zero on priority textbox!"); 
					priorTxtbx.getTxtBx().setText("");
				}
				else if(num > 9){
					errorMsgPrioity.setVisible(true);
					errorMsgPrioity.setText("No number above 9 on priority textbox!");

				//	priorTxtbx.getTxtBx().setStyleName("error-border");
				//	Window.alert("No number above 9 on priority textbox!"); 
					priorTxtbx.getTxtBx().setText("");
				}
			
			}});
		
		workerIdTxtbx = new LabeledTxtBx("Worker ID:");
		workerIdTxtbx.setStyleName("insert-elem-txtbx-widget-input");
		nodeNameTxtbx = new LabeledTxtBx("Node ID:");
		nodeNameTxtbx.setStyleName("insert-elem-txtbx-widget-input");
		filePointerTxtbx = new LabeledTxtBx("File Pointer:");
		filePointerTxtbx.setStyleName("insert-elem-txtbx-widget-input");
		extra1Txtbx = new LabeledTxtBx("Extra Detail 1:");
		extra1Txtbx.setStyleName("insert-elem-txtbx-widget-input");
		extra2Txtbx = new LabeledTxtBx("Extra Detail 2:");
		extra2Txtbx.setStyleName("insert-elem-txtbx-widget-input");
		extra3Txtbx = new LabeledTxtBx("Extra Detail 3:");
		extra3Txtbx.setStyleName("insert-elem-txtbx-widget-input");

		FlowPanel txtPnl1 = new FlowPanel();
		txtPnl1.setStyleName("insert-elem-col1");
		txtPnl1.add(elemIdTxtbx);
		txtPnl1.add(priorTxtbx);
		txtPnl1.add(errorMsgPrioity);
		//txtPnl1.add(workerIdTxtbx);
		txtPnl1.add(filePointerTxtbx);
		txtPnl1.add(nodeNameTxtbx);
		FlowPanel txtPnl2 = new FlowPanel();
		txtPnl2.setStyleName("insert-elem-col2");
		txtPnl2.add(extra1Txtbx);
		txtPnl2.add(extra2Txtbx);
		txtPnl2.add(extra3Txtbx);
		
		Button closeBtn = new Button();
		closeBtn.setStyleName("insert-elem-txtbx-widget-close-btn");
		closeBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				InsertElementTextBoxes.this.removeFromParent();
			}
		});

		FlowPanel btnPnl = new FlowPanel();
		btnPnl.setStyleName("insert-elem-x-col");
		btnPnl.add(closeBtn);
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.setStyleName("insert-elem-txtbx-widget");
		mainPnl.add(txtPnl1);
		mainPnl.add(txtPnl2);
		mainPnl.add(btnPnl);
		
		initWidget(mainPnl);
	
	}

	public TextBox getElemIdTxtbx() {
		return elemIdTxtbx.getTxtBx();
	}

	public TextBox getPriorTxtbx() {
		return priorTxtbx.getTxtBx();
	}

	public TextBox getWorkerIdTxtbx() {
		return workerIdTxtbx.getTxtBx();
	}
	
	public TextBox getNodeNameTxtbx() {
		return nodeNameTxtbx.getTxtBx();
	}

	public TextBox getExtra1Txtbx() {
		return extra1Txtbx.getTxtBx();
	}

	public TextBox getExtra2Txtbx() {
		return extra2Txtbx.getTxtBx();
	}

	public TextBox getExtra3Txtbx() {
		return extra3Txtbx.getTxtBx();
	}
	public TextBox getFilePointerTxtbx() {
		return filePointerTxtbx.getTxtBx();
	}

	

	class LabeledTxtBx extends Composite {
		
		private TextBox txtBx;
		
		public LabeledTxtBx(String lbl){
			
			HTMLPanel label = new HTMLPanel(lbl);
			label.setStyleName("lbl-txtbx-label");
			
			txtBx = new TextBox();
			txtBx.setStyleName("lbl-txtbx-textbox");
			
			FlowPanel mainPnl = new FlowPanel();
			mainPnl.add(label);
			mainPnl.add(txtBx);
			
			initWidget(mainPnl);
			
		}

		public LabeledTxtBx(String lbl,boolean required){
			
			HTMLPanel requiredlabel = new HTMLPanel("*");
			requiredlabel.setStyleName("lbl-txtbx-req-label");
			
			HTMLPanel label = new HTMLPanel(lbl);
			label.setStyleName("lbl-txtbx-label");
			
			txtBx = new TextBox();
			txtBx.setStyleName("lbl-txtbx-textbox");
			
			FlowPanel mainPnl = new FlowPanel();
			mainPnl.add(label);
			if (required)
			mainPnl.add(requiredlabel);
			mainPnl.add(txtBx);
			
			initWidget(mainPnl);
			
		}
		
		public TextBox getTxtBx() {
			return txtBx;
		}
		
	}
	
}
