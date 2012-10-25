package org.weso.wesearch.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;

public class FindNacidoEn extends Steps {
	
	private Wesearch wesearch;
	private Properties properties;
	
	@Given("an ontology is loaded with Ministros, nacido_en and representa_a")
	@Pending
	public void loadMinistrosOntology(){
		//TODO
	}
	
	@When("I ask for property with $str and Matter $matter")
	@Pending
	public void AskForProperties(String str, String matter) {
		//properties = wesearch.getProperties(matter, str);
		assert(properties != null);
	}
	
	@Then("I should get property $propertyName")
	@Pending
	public void getProperty(String propertyName) {
		//Property p = properties.getPropertyByName(propertyName);
		//assert(p != null);
	}
	
	@Then("I should not get property $propertyName")
	@Pending
	public void dontGetProperty(String propertyName) {
		//Property p = properties.getPropertyByName(propertyName);
		//assert(p == null);
	}

}
