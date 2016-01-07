package com.svi.bpo.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class SampleServlet extends HttpServlet {
	private static final String IN_PROCESS = "in process";
	private static final String WAITING = "waiting";
	private static final String TIME = "time";
	private static BPO bpo;
	private static JSONArray tmpJsonArray = new JSONArray();
	
	private static Map<String, Map<String,JSONArray>> dataStorage = new HashMap<>();
	private static Map<String, JSONArray> currentData;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 987L;

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
		if(bpo!=null) {
			String nodeId = request.getParameter("nodeId").trim();
			if(!nodeId.isEmpty()) {
				String currentTime = new SimpleDateFormat("hh':'mm':'ss").format(new Date());
				List<Map<String, Object>> tmp = getBpo().getReportFunctions().getNodeCurrentWaitingAndInProcessElementCount(nodeId);
				JSONArray tmpNodeData = currentData.get(nodeId);
				System.out.println("NodeId: " + nodeId);
				if(tmpNodeData!=null) {
					jsonData = tmpNodeData;
				} else {
					jsonData = new JSONArray();
					currentData.put(nodeId, jsonData);
				}
				
				for(Map<String, Object> each: tmp) {
					int waiting = (int) each.get("currentTotalWaitingElements");
					int process = (int) each.get("currentTotalInProcessElements");
					JSONObject dataObj = new JSONObject();
					try {
						dataObj.put(TIME, currentTime);
						dataObj.put(WAITING, waiting);
						dataObj.put(IN_PROCESS, process);
						jsonData.put(dataObj);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		PrintWriter out = response.getWriter();
		out.print(jsonData);
	}

	public static BPO getBpo() {
		return bpo;
	}

	public static void setBpo(BPO bpo) {
		SampleServlet.bpo = bpo;
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
