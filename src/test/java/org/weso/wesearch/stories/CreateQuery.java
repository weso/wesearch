package org.weso.wesearch.stories;

import org.jbehave.core.annotations.Given;
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
	
	@Given("a matter $, property $ and value selector $")
	public void loadQueryParameters(Matter m, Property p, ValueSelector value) {
		//TODO initialize Wesearch
		this.matter = m;
		this.property = p;
		this.selector = value;
		assert(matter != null);
		assert(property != null);
		assert(selector != null);
	}
	
	@When("I ask for a query")
	public void askQuery() {
		query = wesearch.createQuery(matter, property, selector);
		assert(query != null);
	}
	
	@Then("I get query $")
	public void getQuery(String sparqlQuery) {
		String q = query.getSparqlQuery();
		assert(q.equals(sparqlQuery));
	}

}
