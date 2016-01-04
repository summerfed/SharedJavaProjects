package com.svi.bpo.client.view.widgets.admin.batch;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.svi.bpo.client.CommonObjs;
import com.svi.bpo.client.view.widgets.SwitchButton;
import com.svi.bpo.constants.ResultCnstnts;
import com.svi.bpo.objects.EndpointObj;

public class BatchUploadWidget extends Composite{

	private FormPanel formPnl;
	private FileUpload fileUpld;
	private SwitchButton upldBtn;
	private ResultCnstnts type;
	private ListBox listBx;

	public BatchUploadWidget(String lbl, ResultCnstnts type){
		
		this.type = type;
		
		HTMLPanel header = new HTMLPanel(lbl);
		header.setStyleName("btch-upload-widget-hdr");
		
		upldBtn = new SwitchButton("");
		upldBtn.setDisableStyle("btch-upload-widget-upld-btn-disable");
		upldBtn.setEnableStyle("btch-upload-widget-upld-btn-enable");
		upldBtn.disable();
		
		fileUpld = new FileUpload();
		fileUpld.setName("fileUpload");
		
		formPnl = new FormPanel();
		formPnl.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPnl.setMethod(FormPanel.METHOD_POST);
		formPnl.add(fileUpld);

		listBx = new ListBox();
		listBx.setStyleName("btch-upload-widget-list");
		listBx.getElement().setAttribute("disabled", "");
		listBx.setVisibleItemCount(1);
		
		FlowPanel upldCtrlPnl = new FlowPanel();
		upldCtrlPnl.setStyleName("btch-upload-widget-ctrl");
		upldCtrlPnl.add(upldBtn);
		upldCtrlPnl.add(formPnl);
		upldCtrlPnl.add(listBx);
		
		FlowPanel body = new FlowPanel();
		body.setStyleName("btch-upload-widget-body");
		body.add(upldCtrlPnl);
		
		FlowPanel mainPnl = new FlowPanel();
		mainPnl.add(header);
		mainPnl.add(body);
		
		initWidget(mainPnl);

		fileUpld.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if(fileUpld.getFilename().toUpperCase().endsWith(".CSV")){
					upldBtn.enable();
					listBx.getElement().removeAttribute("disabled");
					listBx.clear();
					for(EndpointObj obj : CommonObjs.bpoUser.getEndpoints()){
						if(obj.isActive()){
							listBx.addItem(obj.getEndpointId());
						}
					}
				} else {
					upldBtn.disable();
					listBx.getElement().setAttribute("disabled", "");
					listBx.clear();
				}
			}
		});

	}
	
	public void reset(){
		fileUpld.getElement().setPropertyString("value", "");
		upldBtn.disable();
		listBx.getElement().setAttribute("disabled", "");
		listBx.clear();
	}
	
	public void submit(){
		formPnl.setAction(GWT.getModuleBaseURL() + "upldSvc?");
		formPnl.submit();
	}
	
	public String getFileName(){
		String fileName = fileUpld.getFilename();
		String filePth = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length());
		return filePth;
	}

	public FormPanel getFormPnl() {
		return formPnl;
	}

	public FileUpload getFileUpld() {
		return fileUpld;
	}

	public SwitchButton getUpldBtn() {
		return upldBtn;
	}
	
	public ResultCnstnts getType() {
		return type;
	}
	
	public String getSelectedEndpoint(){
		if(listBx.getItemCount() > 0){
			return listBx.getItemText(listBx.getSelectedIndex());
		}
		return null;
	}
	
}
