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
		switch(selector.getType()) {
		case ValueSelector.TEXT:
			filter += "regex(?" + name + ", \"" + selector.getValue().getValue() + 
				"\", \"i\")";
			break;
		case ValueSelector.NUMERIC:
			filter += "xsd:decimal(?" + name + ") = xsd:decimal('" + 
					selector.getValue().getValue() + "')";
			break;
		case ValueSelector.DATE:
			filter += "xsd:date(?" + name + ") = xsd:date('"
					+ selector.getValue().getValue() + "')";
			break;
		}
		filter += ")";
		return filter;
	}
	
}
