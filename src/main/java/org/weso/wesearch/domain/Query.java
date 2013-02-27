package org.weso.wesearch.domain;

import org.weso.utils.QueryBuilderException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.domain.impl.filters.Filter;
import org.weso.wesearch.domain.impl.filters.Filters;

/**
 * This is an interface that represents a template for a query
 * @author Ignacio Fuertes Bernardo
 *
 */
public interface Query {
	
	/**
	 * This method returns a query in a string
	 * @return The string of the query
	 */
	String obtainQuery();
	
	/**
	 * This method adds a new clause to the query. An example of SPARQL clause
	 * is "?x rdf:type foaf:Person".
	 * @param clause The clause to add to the query
	 */
	void addClause(String clause);
	
	/**
	 * This method adds a set of filters to the query. A set of filters is two 
	 * or more filters separated by an operator. An example of a set of SPARQL
	 * filters is "FILTER (xsd:int(?year) = xsd:int ('2010') || xsd:int(?year) 
	 * = xsd:int('2013') || xsd:int(?year) = xsd:int('2015')) ."
	 * @param varName The name of the var that must be filtered
	 * @param filter The filters that will be applied over the variable
	 * @throws QueryBuilderException This exception is thrown if there is a 
	 * problem building the query
	 */
	void addFilters(String varName, Filters filter) 
			throws QueryBuilderException;
	
	/**
	 * This method adds a new single filter to the query. A filter is a 
	 * sentence of the query that limits the values of a variable. An example 
	 * of SPARQL filter is "FILTER (str(?name) = str("Juan")) ."
	 * @param varName The name of the var that must be filtered
	 * @param filter The filter that will be applied over the variable
	 * @throws QueryBuilderException This exception is thrown if there is a 
	 * problem building the query
	 */
	void addFilter(String varName, Filter filter) throws QueryBuilderException;
	
	/**
	 * This method returns the name of the next variable that have to be used in
	 * the query
	 * @return The name of the next variable
	 * @throws WesearchException This exception is thrown if there is a problem
	 */
	String getNextVarName() throws WesearchException;
	
	
	/**
	 * This method returns if the next property to add to the query is for
	 * the result or for an auxiliary variable
	 * @return A boolean indicates if the next variable is for the result or no
	 */
	boolean isPropertyForResult();
	
	
	/**
	 * This method returns the name of the auxiliary variable that it isn't 
	 * typed 
	 * @return The name of the auxiliary variable that it isn't typed
	 */
	String obtainAuxiliarVarName();

}
