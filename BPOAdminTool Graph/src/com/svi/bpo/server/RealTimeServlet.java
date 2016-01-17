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

public class RealTimeServlet extends HttpServlet {	
	private static final String PERCENT_OUTPUT = "percentOutput";
	private static final String PERCENT_ERROR = "percentError";
	
	private static BPO bpo;
	private static JSONArray tmpJsonArray = new JSONArray();
	
	private static Map<String, Map<String,JSONArray>> dataStorage = new HashMap<>();
	private static Map<String, JSONArray> currentData;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1559084690146224664L;
	
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
			if(!nodeId.isEmpty()){
				List<Map<String, Object>> tmp = getBpo().getReportFunctions().getNodeTotalErrorAndOutputCount(nodeId);
				System.err.println("NODE ID: " + nodeId);
				System.err.println("tmp:" + tmp);
				JSONArray tmpNodeData = currentData.get(nodeId);
				if (tmpNodeData!=null){
					jsonData = tmpNodeData;
				}
				else{
					jsonData = new JSONArray();
					currentData.put(nodeId, jsonData);
				}
				
				for(Map<String, Object> each: tmp){
					int errorCnt = (int) each.get("totalErrorCount");
					System.err.println("Error Count: " + errorCnt);
					
					int outputCnt = (int) each.get("totalOutputCount");
					System.err.println("Output Count: " + outputCnt);					
					JSONObject dataObj = new JSONObject();
					try {
						float percentOutput = (float) (outputCnt * 100) / (outputCnt + errorCnt);
						float percentError = (float) (errorCnt * 100) / (outputCnt + errorCnt);
						System.err.println("Percent Error: " + String.format("%.2f",percentOutput));
						dataObj.put(PERCENT_OUTPUT, String.format("%.2f",percentOutput));
						dataObj.put(PERCENT_ERROR, String.format("%.2f",percentError));
						jsonData.put(dataObj);
					} catch (JSONException e) {
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
		RealTimeServlet.bpo = bpo;
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
