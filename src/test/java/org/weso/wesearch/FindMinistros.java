package org.weso.wesearch;

import org.jbehave.core.annotations.*;
import org.weso.wesearch.domain.*;

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
	    	Matter m = matters.findMatter(matterName);
	        assert(m != null);
	    }

	    @Then("I should not get matter $matter")
	    public void dontGetMatter(String matterName) {
	    	Matter m = matters.findMatter(matterName);
	    	assert(m == null);
	    }

}
