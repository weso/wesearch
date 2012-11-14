package org.weso.wesearch.domain;

import com.hp.hpl.jena.vocabulary.RDF;

public class SPARQLQueryBuilder {
		
	public static String getTypeClause(String name, Matter matter) {
		String clause = "?" + name + " <" + RDF.type.toString() + "> " + 
				"<" + matter.getUri() + ">";
		return clause;
	}
	
	public static String getPropertyClause(String name, Property property, 
			String objName) {
		String query = "?" + name + " <" + property.getUri() + "> " 
				+ "?" + objName;
		return query;
	}
	
	public static String getFilterClause(String name, ValueSelector selector) {
		String filter = "FILTER(";
		if(selector.getType().equals(ValueSelector.TEXT)) {
			filter += "regex(?" + name + ", \"" + selector.getValue().getValue() + 
					"\", \"i\")";			
		} else if (selector.getType().equals(ValueSelector.NUMERIC)) {
			filter += "xsd:decimal(?" + name + ") = xsd:decimal('" + 
					selector.getValue().getValue() + "')";
		} else if (selector.getType().equals(ValueSelector.DATE)) {
			filter += "xsd:date(?" + name + ") = xsd:date('"
					+ selector.getValue().getValue() + "')";
		}
		filter += ")";
		return filter;
	}
	
}
