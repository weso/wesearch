package org.weso.wesearch.domain.impl.filters;

/**
 * It's an implementation of the interface Filters that represents a set of
 * sparql filters
 * @author Ignacio Fuertes Bernardo
 *
 */
public class SPARQLFilters implements Filters{
	
	/**
	 * A single sparql filter
	 */
	private SPARQLFilter filter;
	/**
	 * The operator that separates one filter to the others
	 */
	private Operator op;
	/**
	 * The rest filters of the query
	 */
	private Filters filters;
	
	/**
	 * It's a constructor of the class
	 * @param filter The single filter of the object
	 */
	public SPARQLFilters(SPARQLFilter filter) {
		this.filter = filter;
		op = null;
		filters = null;
	}
	
	/**
	 * It's a constructor of the class. Initialize with null the properties of 
	 * the object
	 */
	public SPARQLFilters() {
		this.filter = null;
		this.op = null;
		this.filters = null;
	}
	
	/**
	 * This method returns the single filter of the object
	 * @return The single filter
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * This method sets a new single filter to the object
	 * @param filter A new single filter
	 */
	public void setFilter(SPARQLFilter filter) {
		this.filter = filter;
	}

	/**
	 * This method returns the operator
	 * @return The operator of the filters
	 */
	public Operator getOp() {
		return op;
	}

	/**
	 * It's a constructor of the class
	 * @param filter The single filter
	 * @param op The operator
	 * @param filters The rest of filters
	 */
	public SPARQLFilters(SPARQLFilter filter, Operator op, 
			Filters filters) {
		this.filter = filter;
		this.op = op;
		this.filters = filters;
	}
	
	/**
	 * This method sets a new operator
	 * @param op The new operator
	 */
	public void setOp(Operator op) {
		this.op = op;
	}
	
	/**
	 * This method sets a new set of filters
	 * @param filters New set of filter
	 */
	public void setFilters(Filters filters) {
		this.filters = filters;
	}
	
	/**
	 * This method returns the set of filter of the object
	 * @return
	 */
	public Filters getFilters() {
		return filters;
	}
	
	@Override
	public void addFilter(Filter filter, Operator op) {
		if(filters == null) {
			this.op = op;
			this.filters = new SPARQLFilters((SPARQLFilter)filter);
		} else {
			filters.addFilter(filter, op);
		}
	}
	
	@Override
	public String toString() {
		String result = "";
		result += filter.getClause();
		if(op != null && filters != null) {
			result += op.value() + " " + filters.toString();
		}
		return result;
	}

}
