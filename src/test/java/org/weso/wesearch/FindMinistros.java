package org.weso.wesearch;

import static org.jbehave.Ensure.ensureThat;
import com.dosideas.business.LoginService;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.steps.Steps;

public class FindMinistros extends Steps {

	private Wesearch wesearch;
	private Matters matters;
	
	    @Given("an Ontology is loaded with Ministros")
	    public void loadMinistrosOntology() {
	    	// TODO
	    	Ontology ontologyMinistros = new Ontology("Ministros.owl"); 
	        wesearch = new Wesearch();
	    }
	 
	    @When("I ask for matters with $str")
	    public void AskForMatters(String str) {
	        matters = wesearch.getMatters(str);
	    }
	 
	    @Then("I should get matter $matter")
	    public void getMatter(String matterName) {
	        ensureThat(matters.findbyLabel(matterName));
	    }

	    @Then("I should not get matter $matter")
	    public void getMatter(String matterName) {
	        ensureThat(matters.findbyLabel(matterName) == false);
	    }

}
