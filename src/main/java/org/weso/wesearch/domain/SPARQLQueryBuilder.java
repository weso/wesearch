package org.weso.wesearch.domain;

import java.util.List;

import org.apache.log4j.Logger;
import org.weso.utils.OntoModelException;
import org.weso.utils.QueryBuilderException;
import org.weso.wesearch.domain.impl.filters.Filter;
import org.weso.wesearch.domain.impl.filters.Operator;
import org.weso.wesearch.domain.impl.filters.SPARQLFilter;
import org.weso.wesearch.domain.impl.filters.SPARQLFilters;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.OntologyHelper;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.vocabulary.RDF;

public class SPARQLQueryBuilder {
	
	private static Logger logger = Logger.getLogger(SPARQLQueryBuilder.class);
	
	public static String getTypeClause(String name, String objName) 
			throws QueryBuilderException {
		if(name == null || objName == null) {
			logger.error("Some part of the query are null");
			throw new QueryBuilderException("Some part of the query are null");
		}
		String clause = "?" + name + " <" + RDF.type.toString() + "> ?" +
				objName;
		return clause;
	}
	
	public static String getPropertyClause(String name, Property property, 
			String objName) throws QueryBuilderException {
		if(name == null || property == null || objName == null) {
			logger.error("Some part of the query are null");
			throw new QueryBuilderException("Some part of the query are null");
		}
		String query = "?" + name + " <" + property.getUri() + "> " 
				+ "?" + objName;
		return query;
	}
	
	public static Filter getFilter(String name, ValueSelector selector) 
			throws QueryBuilderException {
		if(name == null || selector == null || selector.getValue() == null || 
				selector.getValue().getValue() == null) {
			logger.error("Some part of the query are null");
			throw new QueryBuilderException("Some part of the query are null");
		}
		String clause = "";
		if(selector.getType().equals(ValueSelector.TEXT) ||
				selector.getType().equals(ValueSelector.UNDEFINED)) {
			clause += "regex(?" + name + ", \"" + selector.getValue().getValue() + 
					"\", \"i\")";			
		} else if (selector.getType().equals(ValueSelector.NUMERIC)) {
			clause += "xsd:decimal(?" + name + ") = xsd:decimal('" + 
					selector.getValue().getValue() + "')";
		} else if (selector.getType().equals(ValueSelector.DATE)) {
			clause += "xsd:date(?" + name + ") = xsd:date('"
					+ selector.getValue().getValue() + "')";
		}
		if(clause.equals("")) {
			logger.error("Invalid value selector for filter clause");
			throw new QueryBuilderException("Invalid value selector for filter clause");
		}
		return new SPARQLFilter(clause);
	}
	
	public static SPARQLFilters getClassFilter(String varName, Matter matter, 
			OntoModelWrapper model) throws OntoModelException, QueryBuilderException {
		if(varName == null || matter == null || model == null) {
			logger.error("Some part of the query are null");
			throw new QueryBuilderException("Some part of the query are null");
		}
		List<String> classes = OntologyHelper.extractSubclasses(matter, 
				(OntModel)model.getModel());
		String clause = "?" + varName + " = <" + matter.getUri() + "> ";
		SPARQLFilters result = new SPARQLFilters(new SPARQLFilter(clause));
		SPARQLFilters aux = result;
		for(int i = 0; i < classes.size(); i++) {
			String clazz = classes.get(i);
			aux.setOp(Operator.OR);
			String auxClause = "?" + varName + " = <" + clazz + "> ";
			aux.setFilters(new SPARQLFilters(new SPARQLFilter(auxClause)));
			aux = (SPARQLFilters)aux.getFilters();			
		}
		return result;
	}
	
}
