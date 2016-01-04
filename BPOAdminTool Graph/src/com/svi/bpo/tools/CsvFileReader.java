package com.svi.bpo.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.svi.bpo.tool.dto.ElementDto;
import com.svi.bpo.tool.dto.NodeDto;


public class CsvFileReader {
	
	private String filePath;
	private boolean isElement;
	private List<ElementDto> elmentDtoList;
	private List<NodeDto> nodeDtoList;
	
	public CsvFileReader(String path, boolean isElement){
		this.filePath = path;
		this.isElement = isElement;
		readCsvFile();
		
	}
	
	
	private void readCsvFile(){
		elmentDtoList = new ArrayList<ElementDto>();
		nodeDtoList = new ArrayList<NodeDto>();
		System.out.println("FILE PATH: " + filePath);
		Path path = Paths.get(filePath);
		List<String> stringList = new ArrayList<String>();
		
		try {
			stringList = Files.readAllLines(path, StandardCharsets.UTF_8);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(stringList.size() > 0){	
			if(isElement){
				System.out.println(stringList);
				for(String s : stringList){
					String[] elemDtls = s.split(",%", -1);
					System.out.println("ELEMENT : " + elemDtls);
					if (elemDtls.length == 4)
					{
						System.out.println("ELEMENT : " + elemDtls);
						ElementDto dto = new ElementDto();
						dto.setElementID(elemDtls[0]);
						dto.setPriority(Integer.parseInt(elemDtls[2]));
						dto.setNodeName(elemDtls[1]);
						dto.setWorkerID(elemDtls[3]);
						elmentDtoList.add(dto);
					}
					else if (elemDtls.length == 3)
					{
						ElementDto dto = new ElementDto();
						dto.setElementID(elemDtls[0]);
						dto.setPriority(Integer.parseInt(elemDtls[2]));
						dto.setNodeName(elemDtls[1]);
						elmentDtoList.add(dto);
					}
					//not on input
//					dto.setGfsKey(elemDtls[4]);
//					dto.setExtra1(elemDtls[5]);
//					dto.setExtra2(elemDtls[6]);
//					dto.setExtra3(elemDtls[7]);
					
				}
			} else {
				for(String s : stringList){
					String[] nodeDtls = s.split(",%");
					System.out.println("NODE : " + nodeDtls);
					NodeDto dto = new NodeDto();
					dto.setNodeId(nodeDtls[0]);
					dto.setNodeName(nodeDtls[1]);
					dto.setCluster(nodeDtls[2]);
					dto.setStdUnitOfMeasure(nodeDtls[3]);
					dto.setCost(Double.parseDouble(nodeDtls[4]));
					dto.setAllowedWaitingTime(Integer.parseInt(nodeDtls[5]));
					dto.setAllowedProcessTime(Integer.parseInt(nodeDtls[6]));
					dto.setTargetOutputCount(Integer.parseInt(nodeDtls[7]));
					dto.setAllowedErrorCount(Integer.parseInt(nodeDtls[8]));
					/*
					dto.setAllowedMinWait(nodeDtls[4]);
					dto.setAllowedMaxWait(nodeDtls[5]);
					dto.setAllowedAveWait(nodeDtls[6]);
					dto.setAllowedMinProc(nodeDtls[7]);
					dto.setAllowedMaxProc(nodeDtls[8]);
					dto.setAllowedAveProc(nodeDtls[9]);
					dto.setTargetOutput(nodeDtls[10]);
					dto.setAllowedError(nodeDtls[11]);
					dto.setErrorNode(nodeDtls[12]);
					dto.setNodeExceptionOf(nodeDtls[13]);
					*/
					nodeDtoList.add(dto);
					
				}
			}
		}
	}
	
	public List<ElementDto> getelementDtoList(){
		return this.elmentDtoList;
	}
	
	public List<NodeDto> getNodeDtoList(){
		return this.nodeDtoList;
	}

}
