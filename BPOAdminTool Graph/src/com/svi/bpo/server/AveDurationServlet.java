package com.svi.bpo.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.svi.bpo.graph.BPO;
import com.svi.bpo.graph.utils.DataUtilities;

public class AveDurationServlet extends HttpServlet {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7059167345304800328L;
	private static final String AVE_PROCESS_DURATION = "aveProcessDuration";
	
	private static BPO bpo;
	private static JSONArray tmpJsonArray = new JSONArray();
	
	private static Map<String, Map<String,JSONArray>> dataStorage = new HashMap<>();
	private static Map<String, JSONArray> currentData;

	
	@Override
	public void init() throws ServletException {
		setCurrentData(bpo);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doRequest(request, response);
	}
	
	protected void doRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONArray jsonData = tmpJsonArray;
		if(bpo!=null){
			String nodeId = request.getParameter("nodeId").trim();
			System.err.println("Node Id: " + nodeId);
			if(!nodeId.isEmpty()){
//				List<Map<String, Object>> tmp = getBpo().getReportFunctions().getNodeTotalErrorAndOutputCount(nodeId);
				List<Map<String, Object>> tmp = getBpo().getReportFunctions().getNodeAverageProcessDuration(nodeId);
//				System.err.println("NODE ID: " + nodeId);
				System.err.println("List Object:" + tmp);
				JSONArray tmpNodeData = currentData.get(nodeId);
				if (tmpNodeData!=null){
					jsonData = tmpNodeData;
				}
				else{
					jsonData = new JSONArray();
					currentData.put(nodeId, jsonData);
				}
				
				for(Map<String, Object> each: tmp){
//					int aveDuration = (int) each.get("averageProcessDuration");
					long aveDuration = DataUtilities.toLongValue(each.get("averageProcessDuration"));
					System.err.println("Average Duration: " + aveDuration);
									
					JSONObject dataObj = new JSONObject();
					try{
						dataObj.put(AVE_PROCESS_DURATION, aveDuration);
						jsonData.put(dataObj);
					}
					catch(JSONException e){
						e.printStackTrace();
					}
				}
			}
		}
		System.err.println("JSON DATA: " + jsonData);
		PrintWriter out = response.getWriter();
		out.print(jsonData);
	}
	
	public static BPO getBpo() {
		return bpo;
	}

	public static void setBpo(BPO bpo) {
		AveDurationServlet.bpo = bpo;
		setCurrentData(bpo);
	}
	
	private static void setCurrentData(BPO bpo) {
		if(bpo!=null) {
			String endpoint = bpo.getEndpoint();
			
			if(!dataStorage.containsKey(endpoint)) {
				Map<String, JSONArray> tmp = new HashMap<>();
				dataStorage.put(bpo.getEndpoint(), tmp);
			}
			currentData = dataStorage.get(endpoint);
		}
	}
}
