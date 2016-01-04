package com.svi.bpo.tools;

import java.util.List;

import com.svi.bpo.graph.BPO;
import com.svi.bpo.objects.UpldRsltObj;
import com.svi.bpo.server.Controller;
import com.svi.bpo.tool.dto.ElementDto;
import com.svi.bpo.tool.dto.NodeDto;

public class BPOUploader {
	
	private BPO bpo ;// return to BPO webservice proxy
	private String filePath;
	//private boolean isElement;
	
	public BPOUploader(){}

	public BPOUploader(String endPoint, String filePath, boolean isElement){
		String endPointID = endPoint;
	System.out.println("ENDPOINT at bpouploader: " + endPointID);
		bpo.setEndpoint(endPointID);
		this.filePath = filePath;
		//this.isElement = isElement;

	}
	
	public BPOUploader(String endPoint, String filePath, boolean isElement, BPO bpo){
		String endPointID = endPoint;
		bpo.setEndpoint(endPointID);
		this.filePath = filePath;
		//this.isElement = isElement;
		this.bpo = bpo;
	}
		
	public UpldRsltObj bpoInsert() {

		CsvFileReader fileReader = new CsvFileReader(filePath, true);
		List<ElementDto> elmntDtoList = fileReader.getelementDtoList();
		UpldRsltObj rslt = new UpldRsltObj();
		int errCtr = 0;
		int cmpltdCtr = 0;
		
		for(ElementDto bpoDto:elmntDtoList) {
			//				result.setFileName(fileName);
			String result = "";		//	String result = bpo.insertElement(bpoDto.getElementID(), Integer.parseInt(bpoDto.getPriority()), bpoDto.getNodeName(), bpoDto.getWorkerID());
			if (bpoDto.getWorkerID() == null){
			 result = bpo.getElementFunctions().insertElement(bpoDto.getElementID(), bpoDto.getPriority(), bpoDto.getNodeName());
				
			}
			else{
			 result = bpo.getElementFunctions().insertElement(bpoDto.getElementID(), bpoDto.getPriority(), bpoDto.getNodeName(),bpoDto.getWorkerID());
			}		//String result = bpo.insertElement(bpoDto.getElementID(), bpoDto.getPriority(), bpoDto.getNodeName(), bpoDto.getWorkerID(),bpoDto.getGfsKey(), bpoDto.getExtra1(), bpoDto.getExtra2(), bpoDto.getExtra3());
							//log4jUtil.log(LoggingCnstnts.INFO.getValue(), bpoDto.getElementID() + ":" + result);
							
							if(result.contains("Element Added Successfuly")){
								rslt.putOnList(bpoDto.getElementID(), "Success");
								cmpltdCtr++;
							} else {
								rslt.putOnList(bpoDto.getElementID(), result);
								errCtr++;
							}
		}
		
		rslt.setNoOfCompleted(String.valueOf(cmpltdCtr));
		rslt.setNoOfError(String.valueOf(errCtr));
		
		return rslt;
	}
	
	public UpldRsltObj insertNode(){
		
		CsvFileReader fileReader = new CsvFileReader(filePath, false);
		List<NodeDto> nodeDtoList = fileReader.getNodeDtoList();
		UpldRsltObj rslt = new UpldRsltObj();
		int errCtr = 0;
		int cmpltdCtr = 0;
		
		for(NodeDto nodeDto : nodeDtoList){
			String result = bpo.getNodeFunctions().insertNode(nodeDto.getNodeId(), nodeDto.getNodeName(), nodeDto.getCluster(), nodeDto.getStdUnitOfMeasure(), nodeDto.getCost(), nodeDto.getAllowedWaitingTime(), nodeDto.getAllowedProcessTime(),
					nodeDto.getTargetOutputCount(), nodeDto.getAllowedErrorCount());	
				//String result = bpo.insertNode(nodeDto.getNodeId(), nodeDto.getNodeName());
			//log4jUtil.log(LoggingCnstnts.INFO.getValue(), nodeDto.getNodeName() + ":" + result);
			
			if(result.contains("Success")){
				rslt.putOnList(nodeDto.getNodeName(), "Success");
				cmpltdCtr++;
			} else {
				rslt.putOnList(nodeDto.getNodeName(), result);
				errCtr++;
			}
			
			rslt.setNoOfCompleted(String.valueOf(cmpltdCtr));
			rslt.setNoOfError(String.valueOf(errCtr));
			
		}
		
		return rslt;
	}


	
	
}
