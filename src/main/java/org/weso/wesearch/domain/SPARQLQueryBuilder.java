package org.weso.wesearch.domain;

import org.apache.log4j.Logger;
import org.weso.utils.SPARQLQueryBuilderException;

import com.hp.hpl.jena.vocabulary.RDF;

public class SPARQLQueryBuilder {
	
	private static Logger logger = Logger.getLogger(SPARQLQueryBuilder.class);
		
	public static String getTypeClause(String name, Matter matter) 
			throws SPARQLQueryBuilderException {
		if(name == null || matter == null) {
			logger.error("Some part of the query are null");
			throw new SPARQLQueryBuilderException("Some part of the query are null");
		}
		String clause = "?" + name + " <" + RDF.type.toString() + "> " + 
				"<" + matter.getUri() + ">";
		return clause;
	}
	
	public static String getPropertyClause(String name, Property property, 
			String objName) throws SPARQLQueryBuilderException {
		if(name == null || property == null || objName == null) {
			logger.error("Some part of the query are null");
			throw new SPARQLQueryBuilderException("Some part of the query are null");
		}
		String query = "?" + name + " <" + property.getUri() + "> " 
				+ "?" + objName;
		return query;
	}
	
	public static String getFilterClause(String name, ValueSelector selector) 
			throws SPARQLQueryBuilderException {
		if(name == null || selector == null || selector.getValue() == null || 
				selector.getValue().getValue() == null) {
			logger.error("Some part of the query are null");
			throw new SPARQLQueryBuilderException("Some part of the query are null");
		}
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
