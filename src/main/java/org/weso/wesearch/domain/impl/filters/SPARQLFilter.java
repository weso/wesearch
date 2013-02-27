package org.weso.wesearch.domain.impl.filters;

/**
 * It's an implementation of the interface Filter that represent a SPARQL filter
 * @author Ignacio Fuertes Bernardo
 *
 */
public class SPARQLFilter implements Filter{
	
	/**
	 * The clause of the query corresponding with the filter
	 */
	private String clause;
	
	/**
	 * It's a constructor of the class
	 * @param clause The clause of the filter
	 */
	public SPARQLFilter(String clause) {
		this.clause = clause;
	}
	
	/**
	 * It's a constructor of the class. Initialize the clause with an empty 
	 * string
	 */
	public SPARQLFilter() {
		this.clause = "";
	}
	
	/**
	 * This method sets a new value for the clause
	 * @param clause The new clause
	 */
	public void setString(String clause) {
		this.clause = clause;
	}
	
	@Override
	public String getClause() {
		return this.clause;
	}

}
