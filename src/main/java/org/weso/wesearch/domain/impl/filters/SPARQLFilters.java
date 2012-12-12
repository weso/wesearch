package org.weso.wesearch.domain.impl.filters;

public class SPARQLFilters implements Filters{
	
	private SPARQLFilter filter;
	private Operator op;
	private Filters filters;
	
	public SPARQLFilters(SPARQLFilter filter) {
		this.filter = filter;
		op = null;
		filters = null;
	}
	
	public SPARQLFilters() {
		this.filter = null;
		this.op = null;
		this.filters = null;
	}
	
	
	public Filter getFilter() {
		return filter;
	}

	public void setFilter(SPARQLFilter filter) {
		this.filter = filter;
	}

	public Operator getOp() {
		return op;
	}

	public SPARQLFilters(SPARQLFilter filter, Operator op, 
			Filters filters) {
		this.filter = filter;
		this.op = op;
		this.filters = filters;
	}
	
	public void setOp(Operator op) {
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
			this.filters = new SPARQLFilters((SPARQLFilter)filter);
		} else {
			filters.addFilter(filter, op);
		}
	}
	
	public String toString() {
		String result = "";
		result += filter.getClause();
		if(op != null && filters != null) {
			result += op.value() + " " + filters.toString();
		}
		return result;
	}

}
