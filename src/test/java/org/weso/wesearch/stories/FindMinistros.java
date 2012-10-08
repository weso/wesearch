package org.weso.wesearch.stories;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.weso.utils.WesearchException;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class FindMinistros extends Steps {

	private Wesearch wesearch;
	private Matters matters;
	
	    @Given("an Ontology is loaded with Ministros")
	    public void loadMinistrosOntology() {
	    	// TODO
	    	OntModel ontologyMinistros = ModelFactory.createOntologyModel();
	    	ontologyMinistros.read("Ministros.owl");
	        //wesearch = new Wesearch();
	    }
	 
	    @When("I ask for matters with $str")
	    public void AskForMatters(String str) {
	        matters = wesearch.getMatters(str);
	    }
	 
	    @Then("I should get matter $matter")
	    public void getMatter(String matterName) {
	    	Matter m;
			try {
				m = matters.findMatter(matterName);
				assert(m != null);
			} catch (WesearchException e) {
				assert(false);
			}
	        
	    }

	    @Then("I should not get matter $matter")
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
