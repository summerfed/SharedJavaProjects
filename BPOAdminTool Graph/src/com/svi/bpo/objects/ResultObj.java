package com.svi.bpo.objects;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResultObj implements IsSerializable {

	public Map<String, String> map = new HashMap<String, String>();
	
	public ResultObj() {
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void putToMap(String key, String value) {
		map.put(key, value);
	}

	@Override
	public String toString() {
		return "ResultObj [map=" + map + "]";
	}

	
}
