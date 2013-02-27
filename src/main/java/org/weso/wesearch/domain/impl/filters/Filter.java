package org.weso.wesearch.domain.impl.filters;

/**
 * It's an interface that represents a filter of a query. A filter is a sentence
 * that filter the value of one variable of the query
 * @author Ignacio Fuertes Bernardo
 *
 */
public interface Filter {
	
	/**
	 * This method returns the clause of the filter 
	 * @return The clause of the filter
	 */
	String getClause();

}
