package org.weso.wesearch.domain;

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
	void addFilter(String filter);

}
