package org.weso.wesearch.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.Query;
import org.weso.wesearch.domain.ValueSelector;

public class CreateQuery extends Steps {
	
	private Wesearch wesearch;
	private Matter matter;
	private Property property;
	private ValueSelector selector;
	private Query query;
	
	@Given("a matter $m, property $p and value selector $value")
	@Pending
	public void loadQueryParameters(String m, String p, String value) {
		//TODO initialize Wesearch
		assert(matter != null);
		assert(property != null);
		assert(selector != null);
	}
	
	@When("I ask for a query")
	@Pending
	public void askQuery() {
		query = wesearch.createQuery(matter, property, selector);
		assert(query != null);
	}
	
	@Then("I get query $sparqlQuery")
	@Pending
	public void getQuery(String sparqlQuery) {
		String q = query.getQuery();
		assert(q.equals(sparqlQuery));
	}

}
