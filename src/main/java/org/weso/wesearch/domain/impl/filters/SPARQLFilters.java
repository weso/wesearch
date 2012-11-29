package org.weso.wesearch.domain.impl.filters;

public class SPARQLFilters implements Filters{
	
	private Filter filter;
	private Operator op;
	private Filters filters;
	
	public SPARQLFilters(Filter filter) {
		this.filter = filter;
		op = null;
		filters = null;
	}
	
	public SPARQLFilters(Filter filter, Operator op, 
			Filters filters) {
		this.filter = filter;
		this.op = op;
		this.filters = filters;
	}
	
	public void setOperator(Operator op) {
		this.op = op;
	}
	
	public void setFilters(Filters filters) {
		this.filters = filters;
	}
	
	public Filters getFilters() {
		return filters;
	}
	
	public void addFilter(Filter filter, Operator op) {
		if(filters == null) {
			this.op = op;
			this.filters = new SPARQLFilters(filter);
		} else {
			filters.addFilter(filter, op);
		}
	}
	
	public String toString() {
		String result = "";
		result += filter.getClause();
		if(op != null && filters != null) {
			result += op + " " + filters.toString();
		}
		return result;
	}

}
