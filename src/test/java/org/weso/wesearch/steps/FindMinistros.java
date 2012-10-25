package org.weso.wesearch.steps;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.factories.WesearchFactory;
import org.weso.wesearch.factories.impl.JenaWesearchFactory;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;


public class FindMinistros extends Steps {

	private Wesearch wesearch;
	private Matters matters;
	
	    @Given("an Ontology is loaded with Ministros")
	    public void loadMinistrosOntology() throws WesearchException, OntoModelException {
	    	String[] ontologies = {"src/test/resources/ontoTest2.owl"};
	    	WesearchFactory factory = new JenaWesearchFactory();
	    	OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(new FileOntologyLoader(ontologies));
	    	wesearch = factory.createWesearch(modelWrapper);
	    }
	 
	    @When("I ask for matters with $str")
	    public void AskForMatters(String str) {
	        try {
	        	str = str.replace("\"", "");
				matters = wesearch.getMatters(str);
				assertNotNull(matters);
			} catch (WesearchException e) {
				assert(false);
			}	        
	    }
	 
	    @Then("I should get matter $matterName")
	    public void getMatter(String matterName) {
	    	Matter m;
			try {
				m = matters.findMatter(matterName);
				assertTrue(m != null);
				assertTrue(m.label().equals(matterName));
			} catch (WesearchException e) {
				assertTrue(false);
			}
	        
	    }

	    @Then("I should not get matter $matterName")
	    public void dontGetMatter(String matterName) {
			try { 
				matters.findMatter(matterName);
			} catch (WesearchException e) {
				assertTrue(true);
			}
	    	
	    }

}
