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
import org.weso.wesearch.domain.impl.filters.SPARQLFilter;
import org.weso.wesearch.domain.impl.filters.SPARQLFilters;

import weso.mediator.config.Configuration;

/**
 * It's an implementation of the interface Query that represents SPARQL queries
 */
public class SPARQLQuery implements Query {
	
	private static Logger logger = Logger.getLogger(SPARQLQuery.class);
	
	/**
	 * A list of variable to use in the queries
	 */
	private static List<String> variables = null;
	/**
	 * A map that a contains a relantion between a variable and a filter that
	 * are applied over the variable
	 */
	private Map<String, Filters> filters;
	/**
	 * A list of clauses that form the query
	 */
	private List<String> clauses;
	/**
	 * Is the position in the list of the next variable to use in the query
	 */
	private int nextVar;
	/**
	 * The string form of the query
	 */
	private String query;
	
	/**
	 * It's the constructor of the class
	 * @throws IOException This exception is thrown if there is a problem 
	 * reading the file that contains the name of the variables to use in the 
	 * queries
	 */
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
		query = "";
	}

	@Override
	public String obtainQuery() {
		String query = "SELECT DISTINCT ?res WHERE { ";
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
	
	@Override
	public void addClause(String clause) {
		if(clause != null) {
			clauses.add(clause);
		}
	}
	
	@Override
	public void addFilters(String varName, Filters filter) 
			throws QueryBuilderException {
		if(varName == null || filter == null) {
			logger.error("Some part of the query are null");
			throw new QueryBuilderException("Some part of the query are null");
		}
		filters.put(varName, filter);
	}
	
	@Override
	public void addFilter(String varName, Filter filter) 
			throws QueryBuilderException {
		if(varName == null) {
			logger.error("Some part of the query are null");
			throw new QueryBuilderException("Some part of the query are null");
		}
		if(filter == null) {
			filters.put(varName, null);
		} else {
			Filters fil;
			if((fil = filters.get(varName)) == null ) {
				SPARQLFilters sparqlFilters = new SPARQLFilters(
						(SPARQLFilter)filter);
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
	public String obtainAuxiliarVarName() {
		Set<String> keys = filters.keySet();
		for(String key : keys) {
			if(filters.get(key) == null) {
				return key;
			}
		}
		throw new RuntimeException("There isn't any auxiliar variable that " +
				"it isn't typed");
	}

	/**
	 * This method returns the list of variables
	 * @return The list of variables to use in the query
	 */
	public static List<String> getVariables() {
		return variables;
	}

	/**
	 * This method sets a new list of variables
	 * @param variables The new list of variables
	 */
	public static void setVariables(List<String> variables) {
		SPARQLQuery.variables = variables;
	}

	/**
	 * This method returns the map that contains the relations between variables
	 * and filters
	 * @return A map with the relations between variables and filters
	 */
	public Map<String, Filters> getFilters() {
		return filters;
	}

	/**
	 * This method sets a new map with the relation between variables and them
	 * filters
	 * @param filters The new map
	 */
	public void setFilters(Map<String, Filters> filters) {
		this.filters = filters;
	}

	/**
	 * This method return the list of clauses of the query
	 * @return The list of clauses
	 */
	public List<String> getClauses() {
		return clauses;
	}

	/**
	 * This method sets a new list of clauses
	 * @param clauses The new list of clauses of the query
	 */
	public void setClauses(List<String> clauses) {
		this.clauses = clauses;
	}

	/**
	 * This method returns the position of the next variable to use in the query
	 * @return The position in the list of the next variable to use in the query
	 */
	public int getNextVar() {
		return nextVar;
	}

	/**
	 * This method sets a new position in the list for the next variable to use
	 * @param nextVar The position in the list of the next var
	 */
	public void setNextVar(int nextVar) {
		this.nextVar = nextVar;
	}

	/**
	 * This method returns the string of the SPARQL query
	 * @return The string of the SPARQL query
	 */
	public String getQuery() {
		query = obtainQuery();
		return query;
	}

	/**
	 * This method sets a new string to the query
	 * @param query The new string to the query
	 */
	public void setQuery(String query) {
		this.query = query;
	}

}
