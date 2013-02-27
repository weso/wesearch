package org.weso.wesearch.domain.impl.filters;

/**
 * It's an interface that represents a set of filters. A set of filters is
 * two or more filters applied over one variable and separed by operators.
 * @author Ignacio Fuertes Bernardo
 *
 */
public interface Filters {
	
	/**
	 * This method add a new filter and an operator to the set
	 * @param filter The new filter to add in the set
	 * @param op The operator that separates the others filter and the new 
	 * filter 
	 */
	void addFilter(Filter filter, Operator op);

}
