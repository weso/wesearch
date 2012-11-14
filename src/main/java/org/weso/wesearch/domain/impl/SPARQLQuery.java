package org.weso.wesearch.domain.impl;

import java.util.LinkedList;
import java.util.List;

import org.weso.wesearch.domain.Query;

public class SPARQLQuery implements Query {
	
	private List<String> clauses;
	private List<String> filters;
	
	public SPARQLQuery() {
		clauses = new LinkedList<String>();
		filters = new LinkedList<String>();
	}

	@Override
	public String getQuery() {
		String query = "SELECT ?res WHERE { ";
		for(String clause : clauses) {
			query += clause + " . ";
		}
		for(String filter : filters) {
			query += filter + " . ";
		}
		query += "}";
		return query;
	}
	
	public void addClause(String clause) {
		if(clause != null) {
			clauses.add(clause);
		}
	}
	
	public void addFilter(String filter) {
		if(filter != null) {
			filters.add(filter);
		}
	}

}
