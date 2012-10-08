package org.weso.wesearch.stories;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.ValueSelector;

public class FindLocation extends Steps {

	private Wesearch wesearch;
	private ValueSelector valueSelector;
	
	@Given("an ontology is loaded with Minitros and nacido_en")
	public void loadMinistrosOntology() {
		//TODO
	}
	
	@When("I ask for value selector of $, $ and $")
	public void askForValueSelector(Property p, Matter m, String str) {
		valueSelector = wesearch.getValueSelector(m, p, str);
		assert(valueSelector != null);
	}
	
	@Then("I should get value selector $")
	public void getValueSelector(String valueSelectorType) {
		assert(valueSelector.getType().equals(valueSelectorType));
	}
}
