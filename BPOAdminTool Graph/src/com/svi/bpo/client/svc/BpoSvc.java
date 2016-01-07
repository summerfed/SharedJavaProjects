package com.svi.bpo.client.svc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import com.svi.bpo.objects.ElemDtlObj;
import com.svi.bpo.objects.ExceptionDtlObj;
import com.svi.bpo.objects.ExceptionNodeDshBrdObj;
import com.svi.bpo.objects.NodeDshBrdObj;
import com.svi.bpo.objects.NodeDtlObj;
import com.svi.bpo.objects.ResultObj;
import com.svi.bpo.objects.UpldRsltObj;

/**
 * @author aenriquez
 *
 */
@RemoteServiceRelativePath("bpoSvc")
public interface BpoSvc extends RemoteService{
	
	/**
	 * @param nodes 
	 * @return List of result details
	 */
	List<String> addNode(List<NodeDtlObj> nodes);

	/**
	 * @param nodes
	 * @return List of result details
	 */
	List<String> deleteNode(List<NodeDtlObj> nodes);
	List<String> deleteExceptionNode(List<ExceptionDtlObj> nodes);

	/**
	 * @param elements
	 * @param node
	 * @return List of result details
	 */
	ResultObj insertElement(List<ElemDtlObj> elements, NodeDtlObj node);

	/**
	 * @param elements
	 * @param node
	 * @return List of result details
	 */
	ResultObj deleteElement(List<ElemDtlObj> elements, NodeDtlObj node);

	/**
	 * @param fileName
	 * @param endpoint
	 * @return List of result details
	 */
	UpldRsltObj uploadNodesFrmBtch(String fileName, String endpoint);

	/**
	 * @param fileName
	 * @param endpoint
	 * @return 
	 */
	UpldRsltObj uploadElmtsFrmBtch(String fileName, String endpoint);

	UpldRsltObj uploadBtch(String fileName, String endpointId, String type);

	List<NodeDtlObj> getNodes(String endpoint);
	List<ExceptionDtlObj> getNodesException(String endpoint);
List<NodeDtlObj> getClusterNodes(String endpoint, String clusterId);
	List<ElemDtlObj> getElements(NodeDtlObj node);
	List<ElemDtlObj> getElements(NodeDtlObj node,int start,int limit);

	String changeElmtPrty(String endpoint, String nodeName, String elemId,
			int newPriority);

	ElemDtlObj searchElement(String endpointId, String elementName);

	List<NodeDtlObj> searchNode(List<String> endpointIds, String nodeName);

	String transferElement(String elemId, String currNode, String nextNode,
			String endpointId);

	NodeDtlObj getNodeWithElements(NodeDtlObj node);

	List<NodeDshBrdObj> getSummarizedNodes(String endpoint);

	String changeWorkerId(String endpoint, String nodeName, String elemId,
			String newWorkerId);
	String[] getCluster(String endpoint);
	List<String> addExceptionNode(List<ExceptionDtlObj> nodes);

	List<ExceptionNodeDshBrdObj> getSummarizedExNodes(String endpoint);
}
