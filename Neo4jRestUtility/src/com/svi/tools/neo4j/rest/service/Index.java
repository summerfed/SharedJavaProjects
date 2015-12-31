package com.svi.tools.neo4j.rest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Index {
	UrlLocations urls;
	
	protected Index(UrlLocations urls) {
		this.urls = urls;
	}
	
	protected Set<String> createIndex(String label, Collection<String> propertiesToIndex) {
		Set<String> dataToReturn = new HashSet<>();
		List<String> currentIndexes = getIndexes(label);
		for(String each: propertiesToIndex) {
			if(!currentIndexes.contains(each)) {
				StringBuilder jsonPostBuilder = buildCreateIndexJSON(each);
		        WebResource resource = Client.create().resource(urls.getIndexes()+"/"+label);
		        ClientResponse response = resource
		                .accept( MediaType.APPLICATION_JSON )
		                .type( MediaType.APPLICATION_JSON )
		                .entity(jsonPostBuilder.toString())
		                .post( ClientResponse.class );
		        dataToReturn.add(response.getEntity(String.class));
		        response.close();
			}
		}
		return dataToReturn;
	}
	
	protected List<String> getIndexes(String label) {
		List<String> dataToReturn = new ArrayList<>();
        WebResource resource = Client.create().resource(urls.getIndexes()+"/"+label);
       
        String response = resource.accept( MediaType.APPLICATION_JSON ).get(String.class);
        
        try {
			JSONArray resultObject = new JSONArray(response);
			for(int i=0; i<resultObject.length(); i++) {
				JSONObject object = resultObject.getJSONObject(i);
				JSONArray indexArray = object.getJSONArray("property_keys");
				for(int y=0; y<indexArray.length(); y++) {
					dataToReturn.add(indexArray.getString(y));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataToReturn;
	}
	
	private StringBuilder buildCreateIndexJSON(String propertyName) {
		StringBuilder jsonPostBuilder = new StringBuilder();
		jsonPostBuilder.append("{\"property_keys\": [");
		jsonPostBuilder.append("\""+propertyName+"\"");
        jsonPostBuilder.append("]}");
        return jsonPostBuilder;
	}
	
}
