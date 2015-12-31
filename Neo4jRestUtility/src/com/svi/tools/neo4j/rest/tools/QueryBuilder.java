package com.svi.tools.neo4j.rest.tools;

import java.util.List;

import com.svi.tools.neo4j.rest.enums.ReservedSymbolsConstants;
import com.svi.tools.neo4j.rest.enums.ReservedWordsConstants;
import com.svi.tools.neo4j.rest.objects.NodeObj;


public class QueryBuilder {

	private boolean useFilter;
	private boolean useDistinctResult;
	private String space = " ";

	public QueryBuilder() {
		this.useFilter = true;
		this.useDistinctResult = true;
	}
	
	public String buildRetrieveQuery(List<NodeObj> list){
		StringBuilder builder = new StringBuilder();
		if (list.size() < 1) {
			return "NO QUERY";
		}
		
		builder.append(	ReservedWordsConstants._MATCH_.getValue());
		
		for (NodeObj node : list) {
			String smlCaps = node.getNodeName().toLowerCase();
			
			builder.append(	ReservedSymbolsConstants.PARENTHESIS_OPEN.getValue() +
							smlCaps +
							ReservedSymbolsConstants.COLON.getValue() +
							node.getNodeName() +
							ReservedSymbolsConstants.PARENTHESIS_CLOSE.getValue() +
							ReservedSymbolsConstants.COMMA.getValue());
		
		}
		builder.replace(	builder.lastIndexOf(ReservedSymbolsConstants.COMMA.getValue()), 
							builder.length(), "");
		
		
		if (useFilter) {
			builder.append(	ReservedWordsConstants._WHERE_.getValue());
			for (NodeObj node : list) {
				String smlCaps = node.getNodeName().toLowerCase();
				
				for (String filter : node.getFilterList()) {
					builder.append(	smlCaps +
									ReservedSymbolsConstants.PERIOD.getValue() +
									filter + 
									space +
									ReservedSymbolsConstants.EQUALS.getValue() +
									ReservedSymbolsConstants.TILDE.getValue() + 
									space +
									ReservedSymbolsConstants.CURLY_BRACKET_OPEN.getValue() +
									filter + 
									ReservedSymbolsConstants.CURLY_BRACKET_CLOSE.getValue() +
									ReservedWordsConstants._AND_.getValue());
				}
			}
			
			builder.replace(	builder.lastIndexOf(ReservedWordsConstants._AND_.getValue()), 
					builder.length()-1, space);

		}
		
		builder.append(	ReservedWordsConstants._RETURN_.getValue());
		
		if (useDistinctResult) {
			builder.append(	ReservedWordsConstants._DISTINCT_.getValue());
		}
		
		for (NodeObj node : list) {
			String smlCaps = node.getNodeName().toLowerCase();
			
			for (String attrib : node.getAttribList()) {
				builder.append(	smlCaps +
								ReservedSymbolsConstants.PERIOD.getValue() +
								attrib +
								ReservedWordsConstants._AS_.getValue() +
								attrib + 
								ReservedSymbolsConstants.COMMA.getValue() +
								space);
			}
		}
		
		builder.replace(	builder.lastIndexOf(ReservedSymbolsConstants.COMMA.getValue()), 
				builder.length(), "");

		builder.append(	ReservedSymbolsConstants.SEMICOLON.getValue());
		
		return builder.toString();
	}

	public void setUseFilter(boolean useFilter) {
		this.useFilter = useFilter;
	}

	public void setUseDistinctResult(boolean useDistinctResult) {
		this.useDistinctResult = useDistinctResult;
	}

	@Override
	public String toString() {
		return "QueryBuilder [useFilter=" + useFilter + ", useDistinctResult="
				+ useDistinctResult + "]";
	}
	
	
}
