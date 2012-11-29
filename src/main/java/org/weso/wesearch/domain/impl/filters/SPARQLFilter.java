package org.weso.wesearch.domain.impl.filters;

public class SPARQLFilter implements Filter{
	
	private String clause;
	
	public SPARQLFilter(String clause) {
		this.clause = clause;
	}
	
	@Override
	public String getClause() {
		return this.clause;
	}

}
