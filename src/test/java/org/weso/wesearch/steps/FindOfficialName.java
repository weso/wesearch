package org.weso.wesearch.steps;

import static org.junit.Assert.*;

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
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;
import org.weso.wesearch.model.impl.URLOntologyLoader;

import com.hp.hpl.jena.ontology.OntModel;

public class FindOfficialName extends Steps {
	
	private Wesearch wesearch;
	private Properties properties;
	
	@Given("the ontology $ontology")
	public void loadGeographicalOntology(String urlOntology) 
			throws WesearchException, OntoModelException {
		String[] ontologies = new String[1];
		ontologies[0] = urlOntology;
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
				new URLOntologyLoader(ontologies));
		WesearchFactory factory = new JenaWesearchFactory();
		wesearch = factory.createWesearch(modelWrapper);
		assertTrue(modelWrapper.getModel() instanceof OntModel);
		assertFalse(((OntModel)modelWrapper.getModel()).isEmpty());
	}
	
	@When("I ask for property $stem and Matter $classUri")
	public void askForProperties(String stem, String classUri) 
			throws WesearchException {
		Matter m = new MatterImpl("", classUri, "");
		properties = wesearch.getProperties(m, stem);
		assertNotNull(properties);
		assertTrue(properties.iterator().hasNext());
	}
	
	@Then("I should get property $propertyUri")
	public void getProperty(String propertyUri) throws WesearchException {
		Property property = properties.getPropertyByName("nombre");
		assertNotNull(property);
		assertEquals(propertyUri, property.getUri());
	}

}
