package org.weso.wesearch.domain.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;
import org.weso.utils.QueryBuilderException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.domain.Query;
import org.weso.wesearch.domain.impl.filters.Filter;
import org.weso.wesearch.domain.impl.filters.Filters;
import org.weso.wesearch.domain.impl.filters.Operator;
import org.weso.wesearch.domain.impl.filters.SPARQLFilters;

import weso.mediator.config.Configuration;

public class SPARQLQuery implements Query {
	
	private static Logger logger = Logger.getLogger(SPARQLQuery.class);
	
	private static List<String> variables = null;
	
	private Map<String, Filters> filters;
	private List<String> clauses;
	private int nextVar;
	
	public SPARQLQuery() throws IOException {
		filters = new HashMap<String, Filters>();
		clauses = new LinkedList<String>();
		nextVar = -1;
		if(variables == null) {
			InputStream input = Configuration.getLocalStream(
					Configuration.getProperty("sparql_variables"));
			ResourceBundle var = new PropertyResourceBundle(input);
			variables = new LinkedList<String>(var.keySet());
			Collections.sort(variables);
		}
	}

	@Override
	public String getQuery() {
		String query = "SELECT ?res WHERE { ";
		for(String clause : clauses) {
			query += clause + " . ";
		}
		for(String key : filters.keySet()) {
			Filters filters = this.filters.get(key);
			if(filters != null) {
				query += "FILTER( " + filters.toString() + " ) .";
			}
		}
		query += "}";
		return query;
	}
	
	public void addClause(String clause) {
		if(clause != null) {
			clauses.add(clause);
		}
	}
	
	public void addFilters(String varName, Filters filter) throws QueryBuilderException {
		if(varName == null || filter == null) {
			logger.error("Some part of the query are null");
			throw new QueryBuilderException("Some part of the query are null");
		}
		filters.put(varName, filter);
	}
	
	public void addFilter(String varName, Filter filter) throws QueryBuilderException {
		if(varName == null) {
			logger.error("Some part of the query are null");
			throw new QueryBuilderException("Some part of the query are null");
		}
		if(filter == null) {
			filters.put(varName, null);
		} else {
			Filters fil;
			if((fil = filters.get(varName)) == null ) {
				SPARQLFilters sparqlFilters = new SPARQLFilters(filter);
				filters.put(varName, sparqlFilters);
			} else {
				fil.addFilter(filter, Operator.AND);
			}
		}
	}

	@Override
	public String getNextVarName() throws WesearchException {
		nextVar++;
		if(nextVar >= 0 && nextVar < variables.size()) {
			return variables.get(nextVar);
		}
		logger.error("Index for next variable incorrect");
		throw new WesearchException("Index for next variable incorrect");
	}
	
	@Override
	public boolean isPropertyForResult() {
		Set<String> keys = filters.keySet();
		for(String key : keys) {
			if(filters.get(key) == null) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String getAuxiliarVarName() {
		Set<String> keys = filters.keySet();
		for(String key : keys) {
			if(filters.get(key) == null) {
				return key;
			}
		}
		throw new RuntimeException("There isn't any auxiliar variable that it isn't typed");
	}

}
