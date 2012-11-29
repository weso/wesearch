package org.weso.wesearch.domain;

import org.weso.utils.QueryBuilderException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.domain.impl.filters.Filter;
import org.weso.wesearch.domain.impl.filters.Filters;

/*
 * Represents a template for a SPARQL Query
 */
public interface Query {
	
	/*
	 * Return a query in a string
	 */
	String getQuery();
	
	/*
	 * Add new clause to query
	 */
	void addClause(String clause);
	
	/*
	 * Add new filter to query
	 */
	void addFilters(String varName, Filters filter) throws QueryBuilderException;
	
	/*
	 * Add new filter to query
	 */
	void addFilter(String varName, Filter filter) throws QueryBuilderException;
	
	/*
	 * Return the name of the next variable to use in query
	 */
	String getNextVarName() throws WesearchException;
	
	/*
	 * Return if the next property to add to the query is for the result or 
	 * for an auxiliar variable
	 */
	boolean isPropertyForResult();
	
	/*
	 * Return the name of the auxiliar var that it isn´t typed
	 */
	String getAuxiliarVarName();

}
