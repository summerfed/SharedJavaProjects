package com.svi.tools.neo4j.rest.tools;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DynamicQueryBuilder {
	private String query;
	private Map<String, Object> queryProperties;
	private Map<String, String> propertyTemplate;
	private Map<String, String> propertyVariables;
	
	public DynamicQueryBuilder(String query) {
		this.query = query;
		queryProperties = generateQueryPropertyMap();
		propertyVariables = new Hashtable<>();
	}
	
	public void setProperty(String property, String value) {
		String valueToPut = propertyTemplate.get(property);
		valueToPut = valueToPut.replaceAll("\\{\\}", value);
		queryProperties.put(property, valueToPut);
	}
	
	/*public void addParameter(String propertyName, Object value) {
		queryProperties.put(propertyName, value);
	}*/
	
	public Set<String> getProperties() {
		return queryProperties.keySet();
	}
	
	public String getPropertyValue(String property) {
		return queryProperties.get(property).toString();
	}
	
	public String getQuery() {
		query = query.replaceAll("[\\*\\^]", "");
		
		generatePropertyVariables();
		
		for(Entry<String, Object> entry:queryProperties.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue().toString();
			if(value.isEmpty()) {
				String dataToReplace = "[{"+key+"}]";
				query.replaceAll(dataToReplace, "");
			} else if(value.equals(".*")) {
				String dataToReplace = propertyVariables.get(key);
				query = query.replace(dataToReplace, " ");
				queryProperties.remove(key);
			}
		}
		normalizeQueryFilters();
//		System.out.println("QUERY: " + query);
//		System.out.println("PROEPRTIES: " + queryProperties);
		return query;
	}

	private void normalizeQueryFilters() {
		String WHERE = "WHERE";
		String RETURN = "RETURN";
		int startFilterIdx = query.indexOf(WHERE)+6;
		int endFilterIdx = query.indexOf(RETURN);
		String toNormalize = query.substring(startFilterIdx, endFilterIdx);
		String[] tmp = toNormalize.trim().split("\\s+");
		StringBuilder filter = new StringBuilder();
		boolean isProperty = true;
		int toNormalizeLength = tmp.length;
		boolean hasPredicate = false;
		
		if(toNormalizeLength>2) {
			hasPredicate = true;
		}
		
		boolean isLastItemAProperty = false;
		for(int i=0; i<toNormalizeLength; i++) {
			String each = tmp[i];
			
			isProperty = checkIfProperty(each);
			
			if(!isProperty) {
				if(checkIfLastItemInArray(toNormalizeLength, i) && hasPredicate && i!=0) {
					if(checkIfProperty(tmp[i+1]) && isLastItemAProperty) {
						filter.append(each);
						isLastItemAProperty = false;
					} 
				}
			} else {
				filter.append(each);
				isLastItemAProperty = true;
			}
			filter.append(" ");
		}
		
		System.out.println("QUERY BEFORE NORMALIZATION: " +query);
		System.out.println("TO NORMALIZE: " + toNormalize);
		System.out.println("FILTER: " + filter);
		query = query.replace(toNormalize,filter.toString());
	}

	private boolean checkIfLastItemInArray(int arrayLength, int i) {
		boolean isLastMemberOfArray = false;
		if(i!=(arrayLength-1)) {
			isLastMemberOfArray = true;
		}
		return isLastMemberOfArray;
	}

	private boolean checkIfProperty(String each) {
		boolean isProperty;
		if(each.contains("{") || each.contains("(") || each.contains("[")) {
			isProperty = true;
		} else {
			isProperty = false;
		}
		return isProperty;
	}
	
	public Map<String, Object> getQueryProperties(){
		return queryProperties;
	}
	
	private Map<String, Object> generateQueryPropertyMap() {
		 String regex = "\\*?\\{([^{}]+)\\}\\*?";
	     Matcher m = Pattern.compile(regex).matcher(query);
	     Map<String, Object> propertyMap = new ConcurrentHashMap<>();
	     propertyTemplate = new ConcurrentHashMap<>();
	     while(m.find()) {
	    	 String result = m.group().replaceAll("[{}]", "");
	    	 StringBuilder template = new StringBuilder();
	    	 
	    	 if(result.contains("^")) {
	    		 template.append("(?i)");
	    	 }
	    	 if(result.charAt(0) == '*') {
	    		 template.append(".*{}");
	    	 } else {
	    		 template.append("{}");
	    	 }
	    	 
	    	 if(result.charAt(result.length()-1) == '*') {
	    		 template.append(".*");
	    	 }
	    	 
	    	 result = result.replaceAll("[\\*\\^]", "");
	    	 propertyMap.put(result, ".*");
	    	 propertyTemplate.put(result, template.toString());
	     }
	     return propertyMap;
	}
	
	private String getValueBetweenBraces(String data) {
		String result = data.substring(data.indexOf("{")+1,data.indexOf("}"));
    	return result;
	}
	
	private void generatePropertyVariables() {
		String regex = "([^\\s]+)";
		Matcher m = Pattern.compile(regex).matcher(query);
		while(m.find()) {
			String result = m.group();
			if(result.contains("=")) {
				String key = getValueBetweenBraces(result);
				String value = result;
				propertyVariables.put(key, value);
			}
		}
	}
	
}
