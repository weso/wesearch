package org.weso.wesearch.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.weso.utils.WesearchException;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;

public class FindMinistros extends Steps {

	private Wesearch wesearch;
	private Matters matters;
	
	    @Given("an Ontology is loaded with Ministros")
	    public void loadMinistrosOntology() {
	    	// TODO
	    	/*OntModel ontologyMinistros = ModelFactory.createOntologyModel();
	    	ontologyMinistros.read("Ministros.owl");*/
	        //wesearch = new Wesearch();
	    	assert(false);
	    }
	 
	    @When("I ask for matters with $str")
	    public void AskForMatters(String str) {
	        try {
				matters = wesearch.getMatters(str);
			} catch (WesearchException e) {
				e.printStackTrace();
			}
	        assert(false);
	    }
	 
	    @Then("I should get matter $matterName")
	    @Pending
	    public void getMatter(String matterName) {
	    	Matter m;
			try {
				m = matters.findMatter(matterName);
				assert(m != null);
			} catch (WesearchException e) {
				assert(false);
			}
	        
	    }

	    @Then("I should not get matter $matterName")
	    @Pending
	    public void dontGetMatter(String matterName) {
	    	Matter m;
			try {
				m = matters.findMatter(matterName);
				assert(m == null);
			} catch (WesearchException e) {
				assert(false);
			}
	    	
	    }

}
