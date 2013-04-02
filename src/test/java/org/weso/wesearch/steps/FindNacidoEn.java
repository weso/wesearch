package org.weso.wesearch.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.factories.WesearchFactory;
import org.weso.wesearch.factories.impl.JenaWesearchFactory;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

import weso.mediator.core.persistence.jena.JenaModelFileWrapper;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;

public class FindNacidoEn extends Steps {
	
	private Wesearch wesearch;
	private Properties properties;
	
	@Given("the ontology $onto")
	public void loadMinistrosOntology(String onto) throws WesearchException, 
		OntoModelException{
		String[] ontologies = {onto};
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(ontologies));
		WesearchFactory factory = new JenaWesearchFactory();
		wesearch = factory.createWesearch(modelWrapper);
		JenaModelFileWrapper.getInstance().loadModelFromModel(
				(Model)modelWrapper.getModel());
		assertTrue(modelWrapper.getModel() instanceof OntModel);
		assertFalse(((OntModel)modelWrapper.getModel()).isEmpty());
	}
	
	@When("I ask for property with $str and Matter $matter")
	public void AskForProperties(String str, String matter) 
			throws WesearchException {
		str = str.replace("\"", "");
		Matter m = new MatterImpl("", 
				matter, 
				"");
		properties = wesearch.getProperties(m, str);
		assertNotNull(properties);
		assertTrue(properties.iterator().hasNext());
	}
	
	@Then("I should get property $propertyName")
	public void getProperty(String propertyName) throws WesearchException {
		Property p = properties.getPropertyByName(propertyName);
		assertNotNull(p);
		assertEquals(propertyName, p.getLabel());
	}
	
	@Then("I should not get property $propertyName")
	public void dontGetProperty(String propertyName) {
		try {
			properties.getPropertyByName(propertyName);
		} catch (WesearchException e) {
			assertTrue(true);
		}
		
	}

}
