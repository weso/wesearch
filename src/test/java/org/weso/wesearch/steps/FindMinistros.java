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

import weso.mediator.core.persistence.jena.JenaModelFileWrapper;

import com.hp.hpl.jena.rdf.model.Model;


public class FindMinistros extends Steps {

	private Wesearch wesearch;
	private Matters matters;
	
	    @Given("the ontology $onto")
	    public void loadMinistrosOntology(String onto) throws WesearchException,
	    	OntoModelException {
	    	String[] ontologies = {onto};
	    	WesearchFactory factory = new JenaWesearchFactory();
	    	OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
	    			new FileOntologyLoader(ontologies));
	    	JenaModelFileWrapper.getInstance().loadModelFromModel(
	    			(Model)modelWrapper.getModel());
	    	wesearch = factory.createWesearch(modelWrapper);
	    }
	 
	    @When("I ask for matters with $str")
	    public void AskForMatters(String str) throws WesearchException {
        	str = str.replace("\"", "");
			matters = wesearch.getMatters(str);
			assertNotNull(matters);        
	    }
	 
	    @Then("I should get matter $matterName")
	    public void getMatter(String matterName) throws WesearchException {
	    	Matter m = matters.findMatter(matterName);
			assertTrue(m != null);
			assertTrue(m.getLabel().equals(matterName));
	        
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
