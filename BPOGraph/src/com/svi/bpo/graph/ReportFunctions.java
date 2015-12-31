package com.svi.bpo.graph;

import java.util.List;
import java.util.Map;

class ReportFunctions {
	private BPO bpo;
	
	protected ReportFunctions(BPO bpo) {
		this.bpo = bpo;
	}
 	/**
	 * This method will compare the current <b>node average processing performance/duration</b> with <b>node allowed processing performance/duration</b>.
	 * To determine who are the nodes who meets the target processing
	 * <br>
	 * <b>FORMULA</b>
	 * @param nodeId, <b>Put * to get all nodes</b>
	 * @return 
	 * List&lt;Map&lt;String, Object&gt;&gt;, Where List Elements = Each Node and Map<String, Object> consist of Report Fields:
	 * <br>
	 * <b>WHERE KEY = Value Are:</b>
	 * <br>
	 * <ul>
	 * 	<li><i><b>nodeId</b></i> = Unique Identification of the Node</li>
	 *  <li><i><b>nodeName</b></i> = Node Name/Description</li>
	 *  <li><i><b>Status</b></i> = TRUE or False, Wether node is meeting the target</li>
	 * </ul>
	 */
	public List<Map<String, Object>> getNodeProcessMetrics(String nodeId) {
		return bpo.getNodeFunctions().getNodeProcessMetrics(nodeId);
	}
	
	/**
	 * This method will compare the current <b>node average processing performance/duration</b> with <b>node allowed processing performance/duration</b>.
	 * To determine who are the nodes who meets the target processing
	 * <br>
	 * <b>FORMULA</b>
	 * @param nodeId, <b>Put * to get all nodes</b>
	 * @return 
	 * List&lt;Map&lt;String, Object&gt;&gt;, Where List Elements = Each Node and Map<String, Object> consist of Report Fields:
	 * <br>
	 * <b>WHERE KEY = Value Are:</b>
	 * <br>
	 * <ul>
	 * 	<li><i><b>nodeId</b></i> = Unique Identification of the Node</li>
	 *  <li><i><b>nodeName</b></i> = Node Name/Description</li>
	 *  <li><i><b>allowedProcessDuration</b></i> = Node Allowed Process Duration, Identified during Node Creation</li>
	 *  <li><i><b>averageProcessDuration</b></i> = Average Process Duration of the Node that is Computed Every Element Processed in the Node</li>
	 *  <li><i><b>Status</b></i> = TRUE or False, Wether node is meeting the target</li>
	 * </ul>
	 */
	public List<Map<String, Object>> getNodeProcessDurationMetrics(String nodeId) {
		return bpo.getNodeFunctions().getNodeProcessDurationMetrics(nodeId);
	}
	
	/**
	 * This method will compare the current <b>node average waiting performance/duration</b> with <b>node allowed waiting performance/duration</b>.
	 * To determine who are the nodes who meets the target waiting
	 * <br>
	 * <b>FORMULA</b>
	 * @param nodeId, <b>Put * to get all nodes</b>
	 * @return 
	 * List&lt;Map&lt;String, Object&gt;&gt;, Where List Elements = Each Node and Map<String, Object> consist of Report Fields:
	 * <br>
	 * <b>WHERE KEY = Value Are:</b>
	 * <br>
	 * <ul>
	 * 	<li><i><b>nodeId</b></i> = Unique Identification of the Node</li>
	 *  <li><i><b>nodeName</b></i> = Node Name/Description</li>
	 *  <li><i><b>allowedWaitingDuration</b></i> = Node Allowed Waiting Duration, Identified during Node Creation</li>
	 *  <li><i><b>averageWaitingDuration</b></i> = Average Waiting Duration of the Node that is Computed Every Element Processed in the Node</li>
	 *  <li><i><b>Status</b></i> = TRUE or False, Wether node is meeting the target</li>
	 * </ul>
	 */
	public List<Map<String, Object>> getNodeWaitingDurationMetrics(String nodeId) {
		return bpo.getNodeFunctions().getNodeWaitingDurationMetrics(nodeId);
	}
	
	/**
	 * This method will get <b>count of current waiting elements</b> and <b>count of current in-process elements</b> in the Node.
	 * <br>
	 * <b>FORMULA</b>
	 * @param nodeId, <b>Put * to get all nodes</b>
	 * @return 
	 * List&lt;Map&lt;String, Object&gt;&gt;, Where List Elements = Each Node and Map<String, Object> consist of Report Fields:
	 * <br>
	 * <b>WHERE KEY = Value Are:</b>
	 * <br>
	 * <ul>
	 * 	<li><i><b>nodeId</b></i> = Unique Identification of the Node</li>
	 *  <li><i><b>nodeName</b></i> = Node Name/Description</li>
	 *  <li><i><b>currentTotalWaitingElements</b></i> = Current Waiting Elements</li>
	 *  <li><i><b>currentTotalInProcessElements</b></i> = Current InProcess Elements</li>
	 * </ul>
	 */
	public List<Map<String, Object>> getNodeCurrentWaitingAndInProcessElementCount(String nodeId) {
		return bpo.getNodeFunctions().getNodeCurrentWaitingAndInProcessElementCount(nodeId);
	}
}
