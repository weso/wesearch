package org.weso.wesearch.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.domain.ValueSelector;

public class FindLocation extends Steps {

	private Wesearch wesearch;
	private ValueSelector valueSelector;
	
	@Given("an ontology is loaded with Minitros and nacido_en")
	@Pending
	public void loadMinistrosOntology() {
		//TODO
		assert(0 == 1);
	}
	
	@When("I ask for value selector of $p, $m and $str")
	@Pending
	public void askForValueSelector(String p, String m, String str) {
		//valueSelector = wesearch.getValueSelector(m, p, str);
		assert(valueSelector != null);
	}
	
	@Then("I should get value selector $valueSelectorType")
	@Pending
	public void getValueSelector(String valueSelectorType) {
		assert(valueSelector.getType().equals(valueSelectorType));
	}
}
