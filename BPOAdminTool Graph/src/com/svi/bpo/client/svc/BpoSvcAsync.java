package com.svi.bpo.client.svc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.svi.bpo.graph.obj.ClusterObject;
import com.svi.bpo.objects.ElemDtlObj;
import com.svi.bpo.objects.ExceptionDtlObj;
import com.svi.bpo.objects.ExceptionNodeDshBrdObj;
import com.svi.bpo.objects.NodeDshBrdObj;
import com.svi.bpo.objects.NodeDtlObj;
import com.svi.bpo.objects.ResultObj;
import com.svi.bpo.objects.UpldRsltObj;

public interface BpoSvcAsync {

	void addNode(List<NodeDtlObj> nodes, AsyncCallback<List<String>> callback);
	
	void deleteNode(List<NodeDtlObj> nodes, AsyncCallback<List<String>> callback);

	void insertElement(List<ElemDtlObj> elements, NodeDtlObj node,
			AsyncCallback<ResultObj> callback);

	void deleteElement(List<ElemDtlObj> elements, NodeDtlObj node,
			AsyncCallback<ResultObj> callback);

	void uploadNodesFrmBtch(String fileName, String endpoint,
			AsyncCallback<UpldRsltObj> callback);

	void uploadElmtsFrmBtch(String fileName, String endpoint,
			AsyncCallback<UpldRsltObj> callback);

	void uploadBtch(String fileName, String endpointId, String type,
			AsyncCallback<UpldRsltObj> callback);

	void getNodes(String endpoint, AsyncCallback<List<NodeDtlObj>> callback);

	void getElements(NodeDtlObj node, AsyncCallback<List<ElemDtlObj>> callback);

	void changeElmtPrty(String endpoint, String nodeName, String elemId,
			int newPriority, AsyncCallback<String> callback);

	void searchElement(String endpointId, String elementName,
			AsyncCallback<ElemDtlObj> callback);

	void searchNode(List<String> endpointIds, String nodeName,
			AsyncCallback<List<NodeDtlObj>> callback);

	void transferElement(String elemId, String currNode, String nextNode,
			String endpointId, AsyncCallback<String> callback);

	void getNodeWithElements(NodeDtlObj node, AsyncCallback<NodeDtlObj> callback);

	void getSummarizedNodes(String endpoint,
			AsyncCallback<List<NodeDshBrdObj>> callback);

	void changeWorkerId(String endpoint, String nodeName, String elemId,
			String newWorkerId, AsyncCallback<String> callback);

	void getElements(NodeDtlObj node, int start, int limit,
			AsyncCallback<List<ElemDtlObj>> callback);

	void getCluster(String endpoint, AsyncCallback<String[]> callback);

	void getClusterNodes(String endpoint, String clusterId,
			AsyncCallback<List<NodeDtlObj>> callback);

	void getNodesException(String endpoint,
			AsyncCallback<List<ExceptionDtlObj>> callback);

	void addExceptionNode(List<ExceptionDtlObj> nodes,
			AsyncCallback<List<String>> callback);

	void deleteExceptionNode(List<ExceptionDtlObj> nodes,
			AsyncCallback<List<String>> callback);

	void getSummarizedExNodes(String endpoint,
			AsyncCallback<List<ExceptionNodeDshBrdObj>> callback);

	void getExceptionElements(ExceptionDtlObj except,
			AsyncCallback<List<ElemDtlObj>> callback);

	

}
