package com.svi.bpo.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.svi.bpo.client.svc.BpoSvc;
import com.svi.bpo.constants.ResultCnstnts;
import com.svi.bpo.graph.BPO;
import com.svi.bpo.graph.ElementAttributes;
import com.svi.bpo.graph.NodeFunctions;
import com.svi.bpo.graph.obj.ClusterObject;
import com.svi.bpo.graph.obj.ElementObject;
import com.svi.bpo.graph.obj.ExceptionNodeObject;
import com.svi.bpo.graph.obj.NodeObject;
import com.svi.bpo.objects.ElemDtlObj;
import com.svi.bpo.objects.ExceptionDtlObj;
import com.svi.bpo.objects.ExceptionNodeDshBrdObj;
import com.svi.bpo.objects.NodeDshBrdObj;
import com.svi.bpo.objects.NodeDtlObj;
import com.svi.bpo.objects.ResultObj;
import com.svi.bpo.objects.UpldRsltObj;
import com.svi.bpo.tools.BPOUploader;

/**
 * @author aenriquez
 * @developer mrongcales
 * 
 */
@SuppressWarnings("serial")
public class BpoSvcImpl extends RemoteServiceServlet implements BpoSvc {
	private static BpoSvcImpl modelInstance;

	public BPO bpo;


	
	
	@Override
	public void init() throws ServletException {
		
	}
	
	@Override
	public List<NodeDtlObj> getNodes(String endpoint) {
		if (bpo == null) {
			bpo = new BPO(Controller.endpointMap.get(endpoint));
			SampleServlet.setBpo(bpo);
			RealTimeServlet.setBpo(bpo);
			AveDurationServlet.setBpo(bpo);
			NodeProgressServlet.setBpo(bpo);
		}
		System.out.println("Endpoint is " + endpoint);
		// endpoint = "http://localhost:7474";
		System.out.println("@b getNodes3...");
		List<NodeDtlObj> list = new ArrayList<NodeDtlObj>();

		synchronized (bpo) {
			System.out.print("\t from " + endpoint);
			String endPnt = Controller.endpointMap.get(endpoint);
			System.out.println("endPnt is " + endPnt);

			try {
				bpo.setEndpoint(endPnt);

			} catch (Exception e) {
				bpo.setEndpoint(endpoint);
			}

			System.out.println("getEndpoint is " + bpo.getEndpoint());

			// NodeFunctions functions =
			// String[] nodes = bpo.getNodeFunctions().viewNodeTable();

			NodeObject[] nodeObject = bpo.getNodeFunctions().viewNodes("*");
			if (nodeObject != null) {
				for (NodeObject nodeDtls : nodeObject) {

					NodeDtlObj nodeObj = new NodeDtlObj();

					
					String nodeId = nodeDtls.getNodeId();
					nodeObj.setNodeId(nodeDtls.getNodeId());
					nodeObj.setNodeName(nodeDtls.getNodeName());
					nodeObj.setEndpointId(endpoint);

					nodeObj.setCluster(nodeDtls.getClusterId());// null
					nodeObj.setStdUnitOfMeasure(nodeDtls.getStdUnitOfMeasure());
					nodeObj.setCanMeetTarget(nodeDtls.getCanMeetTarget());
					nodeObj.setCost(nodeDtls.getCost());
					nodeObj.setAllowedProcessingTime(nodeDtls
							.getAllowedProcessingTime());
					nodeObj.setAllowedWaitingTime(nodeDtls
							.getAllowedWaitingTime());
					nodeObj.setTargetOutputCount(nodeDtls.getTargetOutput());
					nodeObj.setTargetOutput(nodeDtls.getTargetOutput());
					nodeObj.setAllowedError(nodeDtls.getAllowedError() + "");

					nodeObj.setElmtsWaiting(nodeDtls.getCurrentTotalWaitingElements());
						nodeObj.setElmtsInprogress(nodeDtls.getCurrentTotalInProcessElements());
						nodeObj.setCurrentElem(nodeDtls.getCurrentTotalWaitingElements()
								+ nodeDtls.getCurrentTotalInProcessElements());
					
					list.add(nodeObj);
				}
			}

		}
		return list;
	}

	@Override
	public List<String> addNode(List<NodeDtlObj> nodes) {

		List<String> results = new ArrayList<String>();

		

		for (NodeDtlObj node : nodes) {
			bpo.setEndpoint(Controller.endpointMap.get(node.getEndpointId()));
			results.add(bpo.getNodeFunctions().insertNode(node.getNodeId(),
					node.getNodeName(), node.getCluster(),
					node.getStdUnitOfMeasure(), node.getCost(),
					node.getAllowedWaitingTime(),
					node.getAllowedProcessingTime(),
					node.getTargetOutputCount(), node.getAllowedErrorCount()));
		}

		return results;
	}

	@Override
	public List<String> deleteNode(List<NodeDtlObj> nodes) {

		List<String> results = new ArrayList<String>();

		synchronized (bpo) {
			for (NodeDtlObj node : nodes) {

				try {
					bpo.setEndpoint(Controller.endpointMap.get(node
							.getEndpointId()));
					;

				} catch (Exception e) {
					bpo.setEndpoint((node.getEndpointId()));
				}

				results.add(bpo.getNodeFunctions().deleteNode(node.getNodeId()));
			}
		}

		return results;
	}

	@Override
	public List<ElemDtlObj> getElements(NodeDtlObj node) {
		List<ElemDtlObj> temp = new ArrayList<ElemDtlObj>();
		// BPO prxy = new BPO();
		System.out.println("nodeID : " + node.getEndpointId());
		try {
			bpo.setEndpoint(Controller.endpointMap.get(node.getEndpointId()));
			

		} catch (Exception e) {
			bpo.setEndpoint((node.getEndpointId()));
		}

		System.out.println("bpo.getEndpoint(): " + bpo.getEndpoint());
		ElementObject[] elements = getAllElements(node.getNodeId());
		int wElemCtr = 0;
		int pElemCtr = 0;
		if (elements != null) {
			for (ElementObject e : elements) {

				if (e.getStatus().equals("W")) {
					wElemCtr++;
				} else if (e.getStatus().equals("P")) {
					pElemCtr++;
				}
				ElemDtlObj elemObj = new ElemDtlObj(e.getNodeId(),
						e.getElementId(), e.getPriority(), e.getStatus(),
						e.getExtra1(), e.getExtra2(), e.getExtra3(),
						e.getFilePointer(), e.getTargetCompletionDuration(),
						e.getEstimatedCompletionDuration(), e.getWorkerId());
				elemObj.setElementLocation("node");
				

				temp.add(elemObj);
			}

		}

		// System.out.println(wElemCtr +" - " + pElemCtr);
	//	node.setElmtsWaiting(String.valueOf(wElemCtr));
	//	node.setElmtsInprogress(String.valueOf(pElemCtr));
	//	node.setCumElemCount(String.valueOf(wElemCtr + pElemCtr));

		return temp;

	}

	@Override
	public NodeDtlObj getNodeWithElements(NodeDtlObj node) {
		List<ElemDtlObj> temp = new ArrayList<ElemDtlObj>();
		try {
			bpo.setEndpoint(Controller.endpointMap.get(node.getEndpointId()));
		} catch (Exception e) {
			bpo.setEndpoint((node.getEndpointId()));
		}
		ElementObject[] elements = getAllElements(node.getNodeId());
		// System.out.println("Size of wqsElements: " + wqsElements.length);
		int wElemCtr = 0;
		int pElemCtr = 0;
		if (elements != null) {
			for (ElementObject e : elements) {
				// String eWaitStart;
				// String eProcStart;
				//
				// if(e.getEwaitstart().equals("null")){
				// eWaitStart = "";
				// } else {
				// eWaitStart = e.getEwaitstart();
				// }
				//
				// if(e.getEprocstart().equals("null")){
				// eProcStart = "";
				// } else {
				//
				// eProcStart = e.getEprocstart();
				// }

				// ElemDtlObj elemObj = new ElemDtlObj(e.getEid(),
				// e.getStatus(), e.getPriority()
				// , eWaitStart, eProcStart);

				ElemDtlObj elemObj = new ElemDtlObj(e.getNodeId(),
						e.getElementId(), e.getPriority(), e.getStatus(),
						e.getExtra1(), e.getExtra2(), e.getExtra3(),
						e.getFilePointer(), e.getTargetCompletionDuration(),
						e.getEstimatedCompletionDuration(), e.getWorkerId());// workerId
																				// added
																				// by
																				// jm

				// System.out.println(elemObj.toString());

				temp.add(elemObj);
				// System.out.println("Status : " + e.getStatus());
				if (e.getStatus().equals("W")) {
					wElemCtr++;
				} else if (e.getStatus().equals("P")) {
					pElemCtr++;
				}

			}
			// the reason why insert element returns a count
	//		node.setElmtsWaiting(String.valueOf(wElemCtr));
	//		node.setElmtsInprogress(String.valueOf(pElemCtr));
	//		node.setCumElemCount(String.valueOf(wElemCtr + pElemCtr));
		} else {
			/**
			 * Added by emil
			 */
			node.setElmtsWaiting(0);
			node.setElmtsInprogress(0);
		}

		node.setElementList(temp);

		return node;

	}

	@Override
	public ResultObj insertElement(List<ElemDtlObj> elements, NodeDtlObj node) {

		ResultObj result = new ResultObj();

		// BPO prxy = new BPO();
		try {
			bpo.setEndpoint(Controller.endpointMap.get(node.getEndpointId()));
		} catch (Exception e) {
			bpo.setEndpoint((node.getEndpointId()));
		}
		System.out.println("Size of elements: " + elements.size());
		for (ElemDtlObj e : elements) {
			/*
			 * public String insertElement(String elementId, int priority,
			 * String nodeId, String filePointer, String extra1, String extra2,
			 * String extra3, int totalOuput, int totalError) {
			 */
			result.putToMap(
					e.getElementId(),
					bpo.getElementFunctions().insertElement(e.getElementId(),
							e.getPriority(), e.getNodeId(), e.getFilePointer(),
							e.getExtra1(), e.getExtra2(), e.getExtra3(), 0, 0));
			// result.putToMap(e.getElemId(),prxy.insertElement(e.getElemId(),
			// e.getPriority(), node.getNodeName(), e.getWorkerId(),
			// e.getGfsKey(), e.getExtra1(), e.getExtra2(), e.getExtra3()));
		}
		System.out.println(result.toString());
		return result;

	}

	// check for updates *searchFeature
	@Override
	public ElemDtlObj searchElement(String endpointId, String elementId) {

		System.out.println("SEARCH ELEMENT: endpointId = " + endpointId
				+ " elementName : " + elementId);
		synchronized (bpo) {
			bpo.setEndpoint(Controller.endpointMap.get(endpointId));

			NodeObject[] allNodes = bpo.getNodeFunctions().viewNodes("*");

			for (NodeObject node : allNodes) {
				ElementObject[] elemObj = bpo.getElementFunctions()
						.viewElementsInNode(node.getNodeId(), 0, 0);
				if (elemObj != null) {
					for (ElementObject eO : elemObj) {
						if (eO.getElementId().equalsIgnoreCase(elementId)) {
							ElemDtlObj elem = new ElemDtlObj(node.getNodeId(),
									eO.getElementId(), eO.getPriority(),
									eO.getStatus(), eO.getExtra1(),
									eO.getExtra2(), eO.getExtra3(),
									eO.getFilePointer(),
									eO.getTargetCompletionDuration(),
									eO.getEstimatedCompletionDuration(),
									eO.getWorkerId());
							elem.setNormalFlow(eO.getNormalFlowLocation());
							elem.setArrivalTime(eO.getArrivalTime());
							elem.setCompTime(eO.getCompletionTime());
						elem.setProcessingState(eO.getProcessingState());
						elem.setProcessingDesc(eO.getProcessingDescription());
							return elem;

							
							/*public ElemDtlObj(String nodeId, String elementId, int priority, String status,
			
							 * */
						}
					}

				}
			}

		}

		return null;
	}

	// check for updates *searchFeatures
	@Override
	public List<NodeDtlObj> searchNode(List<String> endpointIds, String nodeName) {

		List<NodeDtlObj> list = new ArrayList<NodeDtlObj>();
		for (String id : endpointIds) {

			synchronized (bpo) {
				bpo.setEndpoint((Controller.endpointMap.get(id)));
				System.out.println("bpo endpoint: " + bpo.getEndpoint());
				System.out.println("NodeName at Search Node " + nodeName);
				String[] nodes = bpo.getNodeFunctions().viewNodeTable();
				System.out.println("Length of returmed nodes " + nodes.length);
				if (nodes != null) {

					for (String node : nodes) {

						if (nodeName.trim().equals(
								(node.split("\\|")[0]).trim())) {
							NodeDtlObj nodeObj = new NodeDtlObj();
							nodeObj.setNodeId(node.split("\\|")[0]);
							nodeObj.setNodeName(node.split("\\|")[1]);
							nodeObj.setEndpointId(id);
							System.out.println("Node Name " + nodeName);
							// ElementObject[] elements =
							// bpo.getElementFunctions()
							// .viewElements(node.split("\\|")[0]);
							ElementObject[] elements = getAllElements(node
									.split("\\|")[0]);
							if (elements != null) {
								int wElemCtr = 0;
								int pElemCtr = 0;
								for (ElementObject e : elements) {
									if (e.getStatus().equals("W")) {
										wElemCtr++;
									} else if (e.getStatus().equals("P")) {
										pElemCtr++;
									}
								}

								nodeObj.setElmtsWaiting(nodeObj.getElmtsWaiting());
								nodeObj.setElmtsInprogress(nodeObj.getElmtsInprogress());

							}
							list.add(nodeObj);
						}
					}
				}
			}
		}

		return list;
	}

	// check for updates *moveElement
	@Override
	public String transferElement(String elemId, String currNode,
			String nextNode, String endpointId) {
		synchronized (bpo) {
			try {
				bpo.setEndpoint(Controller.endpointMap.get(endpointId));

			} catch (Exception e) {
				bpo.setEndpoint((endpointId));
			}

			return bpo.getElementFunctions().transferElement(elemId, currNode,
					nextNode);
			// return null;
		}
	}

	// check for updates *changePriority
	@Override
	public String changeElmtPrty(String endpoint, String elementId,
			String nodeName, int newPriority) {
		// System.out.println("elemId: " + elemId);
		// System.out.println("nodeName: " + nodeName);
		// System.out.println("New Priority: " + newPriority);
		synchronized (bpo) {
			try {

				bpo.setEndpoint(Controller.endpointMap.get(endpoint));

			} catch (Exception e) {
				bpo.setEndpoint((endpoint));
			}
			// String x =
			// bpo.getElementFunctions().editElementPriority(nodeName, elemId,
			// Integer.parseInt(newPriority));

			// ElementAttributes elemAttr = new ElementAttributes();

			Map<ElementAttributes, Object> prop = new HashMap<>();
			prop.put(ElementAttributes.PRIORITY, newPriority);

			String x = bpo.getElementFunctions().updateElementAttributes(
					elementId, prop);

			return "x";
			// return null;
		}
	}

	@Override
	public String changeWorkerId(String endpoint, String elementId,
			String nodeId, String workerId) {
		synchronized (bpo) {
			try {
				bpo.setEndpoint(Controller.endpointMap.get(endpoint));
			} catch (Exception e) {
				bpo.setEndpoint(endpoint);
			}
			// return
			// bpo.getElementFunctions().replaceWorkerOfElements(nodeName,
			// elemId, newWorkerId);
			return bpo.getElementFunctions().assignWorkerToElement(nodeId,
					elementId, workerId);
			// return null;
		}
	}

	@Override
	public UpldRsltObj uploadBtch(String fileName, String endpointId,
			String type) {

		BPOUploader uploader;
		String filePth = this.getServletContext().getRealPath("/temp/")
				+ fileName;

		if (type.equals(ResultCnstnts.NODE.getValue())) {

			uploader = new BPOUploader(Controller.endpointMap.get(endpointId),
					filePth, false);
			return uploader.insertNode();

		} else if (type.equals(ResultCnstnts.ELEMENTS.getValue())) {

			uploader = new BPOUploader((endpointId), filePth, true);
			return uploader.bpoInsert();

		}

		return null;

	}

	@Override
	public UpldRsltObj uploadElmtsFrmBtch(String fileName, String endpoint) {
		// TODO: validations and actual upload

		String endpointId = endpoint;
		String elementFile = this.getServletContext().getRealPath("/temp/")
				+ fileName;
		BPOUploader uploader = new BPOUploader(endpointId, elementFile, true);

		return uploader.bpoInsert();
	}

	@Override
	public UpldRsltObj uploadNodesFrmBtch(String fileName, String endpoint) {
		// TODO: validations and actual upload
		String endpointId = endpoint;
		String nodeFile = this.getServletContext().getRealPath("/temp/")
				+ fileName;
		BPOUploader uploader = new BPOUploader(endpointId, nodeFile, false);

		return uploader.insertNode();
	}

	@Override
	public ResultObj deleteElement(List<ElemDtlObj> elements, NodeDtlObj node) {

		ResultObj result = new ResultObj();

		System.out.println("Element: " + elements);
		System.out.println("delete : " + elements.size() + " elements...");

		// BPO prxy = new BPO();

		try {
			bpo.setEndpoint(Controller.endpointMap.get(node.getEndpointId()));
		} catch (Exception e) {
			bpo.setEndpoint((node.getEndpointId()));
		}
		for (ElemDtlObj e : elements) {
			System.out.println("node.getName: " + node.getNodeName());
			System.out.println("e.getWorkerid: " + e.getWorkerId());
			result.putToMap(e.getElementId(), bpo.getElementFunctions()
					.deleteElement(e.getElementId()));

		}

		return result;
	}

	public List<Object> getElement(String elemId) {
		List<Object> element = new ArrayList<Object>();
		return element;
	}

	@Override
	public List<NodeDshBrdObj> getSummarizedNodes(String endpoint) {

		List<NodeDshBrdObj> list = new ArrayList<NodeDshBrdObj>();

		synchronized (bpo) {

			System.out.print("\t from " + endpoint);
			String endPnt = Controller.endpointMap.get(endpoint);
			bpo.setEndpoint(endPnt);

			String[] nodes = bpo.getNodeFunctions().viewNodeTable();

			NodeObject[] nodeObject = bpo.getNodeFunctions().viewNodes("*");

			if (nodes != null) {
				for (NodeObject node : nodeObject) {

					NodeDshBrdObj nodeObj = new NodeDshBrdObj();

					String nodeName = node.getNodeName();
					// String nodeId = node.split("\\|")[0];
					nodeObj.setNodeId(node.getNodeId());
					nodeObj.setNodeName(nodeName);
					nodeObj.setEndpointId(endpoint);

					// ElementObject[] elements =
					// bpo.getElementFunctions().viewElements(node.split("\\|")[0]);
					ElementObject[] elements = getAllElements(node.getNodeId());
					if (elements != null) {

						int wElemCtr = 0;
						int pElemCtr = 0;
						// int cElemCtr = 0;

						for (ElementObject e : elements) {
							if (e.getStatus().equals("W")) {
								wElemCtr++;
							} else if (e.getStatus().equals("P")) {
								pElemCtr++;
							}
							// else if (e.getStatus().equals("C")) {
							// cElemCtr++;
							// }
						}

						nodeObj.setElmtsWaiting(node.getCurrentTotalWaitingElements());
						nodeObj.setElmtsInprogress(node.getCurrentTotalInProcessElements());
						// nodeObj.setCumElemCount(String.valueOf(cElemCtr));
						// nodeObj.setElmtsCmpltd(String.valueOf(cElemCtr));
					}

					// NodeObject dtls = bpo.viewNodeDetails(nodeId);// nodeId

					// nodeObj.setAveWaitTime(Long.toString(bpo.getAveWaitTime(nodeName)));
					// nodeObj.setAveProcTime(Long.toString(bpo.getAveProcTime(nodeName)));

					/* NOTE: */

					nodeObj.setAveWaitTime(""
							+ node.getAverageWaitingDuration());
					nodeObj.setAveProcTime(""
							+ node.getAverageProcessDuration());

					System.out.println("ID: " + nodeObj.getNodeId()
							+ "Dashboard: waitime " + nodeObj.getAveWaitTime());
					System.out.println("Dashboard: proc time "
							+ nodeObj.getAveProcTime());

					list.add(nodeObj);

				}
			}

		}
		return list;
	}

	@Override
	public List<ElemDtlObj> getElements(NodeDtlObj node, int start, int limit) {

		List<ElemDtlObj> temp = new ArrayList<ElemDtlObj>();
		// BPO prxy = new BPO();
		System.out.println("nodeID : " + node.getEndpointId());

		try {
			bpo.setEndpoint(Controller.endpointMap.get(node.getEndpointId()));
		} catch (Exception e) {
			bpo.setEndpoint((node.getEndpointId()));
		}
		System.out.println("bpo.getEndpoint(): " + bpo.getEndpoint());
		ElementObject[] elements = getAllElements(node.getNodeId(), start,
				limit);
		int wElemCtr = 0;
		int pElemCtr = 0;
		if (elements != null) {
			for (ElementObject e : elements) {

				if (e.getStatus().equals("W")) {
					wElemCtr++;
				} else if (e.getStatus().equals("P")) {
					pElemCtr++;
				}
				ElemDtlObj elemObj = new ElemDtlObj(e.getNodeId(),
						e.getElementId(), e.getPriority(), e.getStatus(),
						e.getExtra1(), e.getExtra2(), e.getExtra3(),
						e.getFilePointer(), e.getTargetCompletionDuration(),
						e.getEstimatedCompletionDuration(), e.getWorkerId());
				elemObj.setNormalFlow(e.getNormalFlowLocation());
				// elemObj.setCanMeetDeadline(e.isCanMeetDeadline());
				elemObj.setProcessingState(e.getProcessingState());
				elemObj.setProcessingDesc(e.getProcessingDescription());
				elemObj.setArrivalTime(e.getArrivalTime());
				elemObj.setCompTime(e.getCompletionTime());

				temp.add(elemObj);
			}

		}

		// System.out.println(wElemCtr +" - " + pElemCtr);
		node.setElmtsWaiting((wElemCtr));
		node.setElmtsInprogress((pElemCtr));
		node.setCumElemCount(String.valueOf(wElemCtr + pElemCtr));

		return temp;

		// TODO Auto-generated method stub

	}

	public ElementObject[] getAllElements(String nodeId) {

		return bpo.getElementFunctions().viewElementsInNode(nodeId, 0, 500);

	}

	public ElementObject[] getAllElements(String nodeId, int startPage,
			int limit) {
		
		int start = ((startPage - 1) * limit);
		return bpo.getElementFunctions().viewElementsInNode(nodeId, start,
				limit);

	}

	@Override
	public String[] getCluster(String endpoint) {
		// TODO Auto-generated method stub
		if (bpo == null) {
			bpo = new BPO(Controller.endpointMap.get(endpoint));
		}

		try {
			System.out.println("ENDPOINT IS " + endpoint);
			bpo.setEndpoint(Controller.endpointMap.get(endpoint));

		} catch (Exception e) {
			System.out.println("ENDPOINT IS "
					+ Controller.endpointMap.get(endpoint));
			bpo.setEndpoint(endpoint);
		}

		// bpo.setEndpoint(Controller.endpointMap.get(endpoint));
		ClusterObject[] clustObj = bpo.getNodeFunctions().viewClusters();
		String returnedString[] = new String[clustObj.length];
		int i = 0;
		for (ClusterObject co : clustObj) {
			returnedString[i] = co.getClusterId();
			i++;
		}
		return returnedString;

	}

	@Override
	public List<NodeDtlObj> getClusterNodes(String endpoint, String clusterId) {
		// TODO Auto-generated method stub
		if (bpo == null) {
			bpo = new BPO(Controller.endpointMap.get(endpoint));
		}

		System.out.println("Endpoint is " + endpoint);
		// endpoint = "http://localhost:7474";
		System.out.println("@b getNodes3...");
		List<NodeDtlObj> list = new ArrayList<NodeDtlObj>();

		synchronized (bpo) {

			String endPnt = Controller.endpointMap.get(endpoint);

			// bpo.setEndpoint(endPnt);
			try {
				bpo.setEndpoint(endPnt);
			} catch (Exception e) {
				bpo.setEndpoint(endpoint);
			}

			System.out.println("getEndpoint is " + bpo.getEndpoint());

			NodeObject[] nodeObject = bpo.getNodeFunctions()
					.viewNodeByClusterId(clusterId);
			if (nodeObject != null) {
				for (NodeObject nodeDtls : nodeObject) {

					NodeDtlObj nodeObj = new NodeDtlObj();

					String nodeId = nodeDtls.getNodeId();
					nodeObj.setNodeId(nodeDtls.getNodeId());
					nodeObj.setNodeName(nodeDtls.getNodeName());
					nodeObj.setEndpointId(endPnt);

					nodeObj.setCluster(nodeDtls.getClusterId());// null
					nodeObj.setStdUnitOfMeasure(nodeDtls.getStdUnitOfMeasure());
					nodeObj.setCanMeetTarget(nodeDtls.getCanMeetTarget());
					nodeObj.setCost(nodeDtls.getCost());
					nodeObj.setAllowedProcessingTime(nodeDtls
							.getAllowedProcessingTime());
					nodeObj.setAllowedWaitingTime(nodeDtls
							.getAllowedWaitingTime());
					nodeObj.setTargetOutputCount(nodeDtls.getTargetOutput());

					nodeObj.setTargetOutput(nodeDtls.getTargetOutput());
					nodeObj.setAllowedError(nodeDtls.getAllowedError() + "");
System.out.println("nodeDtls.getCurrentTotalWaitingElements() " + nodeDtls.getCurrentTotalWaitingElements());
	System.out.println("nodeDtls.getCurrentTotalInProcessElements() " + nodeDtls.getCurrentTotalInProcessElements());		
ElementObject[] elements = getAllElements(nodeId);

					if (elements != null) {
						int wElemCtr = nodeDtls.getCurrentTotalWaitingElements();
						int pElemCtr = nodeDtls.getCurrentTotalInProcessElements();
					//	for (ElementObject e : elements) {
					//		if (e.getStatus().equals("W")) {
					//			wElemCtr++;
					//		} else if (e.getStatus().equals("P")) {
					//			pElemCtr++;
					//		}
						//}

						nodeObj.setElmtsWaiting((wElemCtr));
						nodeObj.setElmtsInprogress((pElemCtr));
						nodeObj.setCumElemCount(String.valueOf(wElemCtr
								+ pElemCtr));
					}
					list.add(nodeObj);
				}
			}

		}
		return list;
	}

	@Override
	public List<ExceptionDtlObj> getNodesException(String endpoint) {
		// TODO Auto-generated method stub
		if (bpo == null) {
			bpo = new BPO(Controller.endpointMap.get(endpoint));
		}

		System.out.println("Endpoint is " + endpoint);
		// endpoint = "http://localhost:7474";
		System.out.println("@b getNodes3...");
		List<ExceptionDtlObj> list = new ArrayList<ExceptionDtlObj>();

		synchronized (bpo) {
			System.out.print("\t from " + endpoint);
			String endPnt = Controller.endpointMap.get(endpoint);
			System.out.println("endPnt is " + endPnt);

			try {
				bpo.setEndpoint(endPnt);

			} catch (Exception e) {
				bpo.setEndpoint(endpoint);
			}
			System.out.println("getEndpoint is " + bpo.getEndpoint());

			// NodeFunctions functions =
			// String[] nodes = bpo.getNodeFunctions().viewNodeTable();

			ExceptionNodeObject[] nodeObject = bpo.getExceptionNodeFunctions()
					.viewExceptionNodes("*");
			if (nodeObject != null) {
				for (ExceptionNodeObject exceptNodeDtls : nodeObject) {

					ExceptionDtlObj exceptObj = new ExceptionDtlObj();

					// String nodeName = node.split("\\|")[1];
					// String nodeId = exceptNodeDtls.getExceptionCode();
					exceptObj.setExceptionCode(exceptNodeDtls
							.getExceptionCode());
					exceptObj.setExceptionName(exceptNodeDtls
							.getExceptionName());

					// nodeObj.setEndpointId(endPnt);
					exceptObj.setCurrentTotalWaitingElements(exceptNodeDtls
							.getCurrentTotalWaitingElements());
					exceptObj.setCurrentTotalInProcessElements(exceptNodeDtls
							.getCurrentTotalInProcessElements());
					exceptObj.setAllowedWaitingDuration(exceptNodeDtls
							.getAllowedWaitingDuration());
					exceptObj.setAllowedProcessDuration(exceptNodeDtls
							.getAllowedProcessDuration());
					exceptObj.setCurrentTotalWaitingElements(exceptNodeDtls.getCurrentTotalWaitingElements());
					exceptObj.setCurrentTotalInProcessElements(exceptNodeDtls.getCurrentTotalInProcessElements());
					exceptObj.setEndPoint(endpoint);
					
					/*
					 * ElementObject[] elements = getAllElements(nodeId);
					 * 
					 * if (elements != null) { int wElemCtr = 0; int pElemCtr =
					 * 0; for (ElementObject e : elements) { if
					 * (e.getStatus().equals("W")) { wElemCtr++; } else if
					 * (e.getStatus().equals("P")) { pElemCtr++; } }
					 * 
					 * }
					 */
					list.add(exceptObj);
				}
			}

		}
		return list;

	}

	@Override
	public List<String> addExceptionNode(List<ExceptionDtlObj> nodes) {
		// TODO Auto-generated method stub

		System.out.println("Size of add exception to be added: ");
		List<String> results = new ArrayList<String>();

		/*
		 * /** public String insertNode(String nodeId, String nodeName, String
		 * cluster, String stdUnitOfMeasure, double cost, int
		 * allowedWaitingTime, int allowedProcessTime, int targetOutputCount,
		 * int allowedErrorCount)
		 */

		for (ExceptionDtlObj node : nodes) {
			// bpo.setEndpoint(Controller.endpointMap.get(node.getEndpointId()));

			results.add(bpo
					.getExceptionNodeFunctions()
					.insertExceptionNode(node.getNodeId(),
							node.getExceptionCode(), node.getExceptionName(),
							node.getAllowedWaitingDuration(),
							node.getAllowedProcessDuration()).toString());
		}

		return results;
	}

	@Override
	public List<String> deleteExceptionNode(List<ExceptionDtlObj> nodes) {
		// TODO Auto-generated method stub
		List<String> results = new ArrayList<String>();
		// TODO
		synchronized (bpo) {
			for (ExceptionDtlObj node : nodes) {

				// bpo.setEndpoint((node.getEndpointId()));
				// results.add(bpo.getExceptionNodeFunctions().(node.getNodeId()));
				// results.add(bpo.getNodeFunctions().deleteNode(node.getNodeId()));
			}
		}

		return results;
	}

	@Override
	public List<ExceptionNodeDshBrdObj> getSummarizedExNodes(String endpoint) {
		// TODO Auto-generated method stub
		List<ExceptionNodeDshBrdObj> list = new ArrayList<ExceptionNodeDshBrdObj>();

		synchronized (bpo) {

			System.out.print("\t from " + endpoint);
			String endPnt = Controller.endpointMap.get(endpoint);
			bpo.setEndpoint(endPnt);

			ExceptionNodeObject[] eNodeObj = bpo.getExceptionNodeFunctions()
					.viewExceptionNodes("*");
			if (eNodeObj != null) {
				for (ExceptionNodeObject node : eNodeObj) {

					ExceptionNodeDshBrdObj nodeObj = new ExceptionNodeDshBrdObj();

					String nodeName = node.getExceptionName();
					// String nodeId = node.split("\\|")[0];
					nodeObj.setNodeId(node.getExceptionCode());
					nodeObj.setExceptionCode(node.getExceptionCode());
					nodeObj.setExceptionName(nodeName);
					nodeObj.setAllowedProcessDuration(node
							.getAllowedProcessDuration());
					nodeObj.setAllowedWaitingDuration(node
							.getAllowedWaitingDuration());
					// nodeObj.setEndpointId(endpoint);
					// bpo.getExceptionNodeFunctions().viewExceptionElements(nodeId,
					// node.getExceptionCode());

					list.add(nodeObj);
				}

			}

			// NodeObject dtls = bpo.viewNodeDetails(nodeId);// nodeId

			// nodeObj.setAveWaitTime(Long.toString(bpo.getAveWaitTime(nodeName)));
			// nodeObj.setAveProcTime(Long.toString(bpo.getAveProcTime(nodeName)));

			/* NOTE: */

		}

		return list;
	}


	@Override
	public List<ElemDtlObj> getExceptionElements(ExceptionDtlObj except) {
		// TODO Auto-generated method stub
		
		 List<ElemDtlObj> elemList = new ArrayList<ElemDtlObj>();

		 bpo.setEndpoint(Controller.endpointMap.get(except.getEndPoint()));
	//	 bpo.getExceptionNodeFunctions().getExceptionElement(nodeId, exceptionCode, elementId, workerId)
	//	List<ElemDtlObj> elem = bpo.getExceptionNodeFunctions().getExceptionElement(except.getNodeId(), except.getExceptionCode(), elementId, workerId)
		
		 ElementObject[] elem = bpo.getExceptionNodeFunctions().viewExceptionElements(except.getNodeId(), except.getExceptionCode());
		 
		 for(ElementObject newElem : elem){
			 ElemDtlObj elems = new ElemDtlObj();
			 elems.setArrivalTime(newElem.getArrivalTime());
			 elems.setCompTime(newElem.getCompletionTime());
			 elems.setElementId(newElem.getElementId());
			 elems.setElementLocation("Exception");
			 elems.setExtra1(newElem.getExtra1());
			 elems.setExtra2(newElem.getExtra2());
			 elems.setExtra3(newElem.getExtra3());
			 elems.setNormalFlow(newElem.getNormalFlowLocation());
			 elems.setProcessingState(newElem.getProcessingState());
			 elems.setProcessingDesc(newElem.getProcessingDescription());
			 elems.setFilePointer(newElem.getFilePointer());
			 elems.setStatus(newElem.getStatus());
			 elems.setNodeId(newElem.getNodeId());
			 elems.setWorkerId(newElem.getWorkerId());
			 elemList.add(elems);
			 System.out.println("WORKER ID: " + newElem.getWorkerId());
			 
		 }
		 
		 
		 
		 return elemList;
	}

}
