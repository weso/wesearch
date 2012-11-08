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
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

import com.hp.hpl.jena.ontology.OntModel;

public class FindNacidoEn extends Steps {
	
	private Wesearch wesearch;
	private Properties properties;
	
	@Given("an ontology is loaded with Ministros, nacido_en and representa_a")
	public void loadMinistrosOntology() throws WesearchException, OntoModelException{
		String[] ontologies = {"src/test/resources/ontoTest2.owl"};
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(ontologies));
		WesearchFactory factory = new JenaWesearchFactory();
		wesearch = factory.createWesearch(modelWrapper);
		assertTrue(modelWrapper.getModel() instanceof OntModel);
		assertFalse(((OntModel)modelWrapper.getModel()).isEmpty());
	}
	
	@When("I ask for property with $str and Matter $matter")
	public void AskForProperties(String str, String matter) throws WesearchException {
		str = str.replace("\"", "");
		Matter m = new MatterImpl(matter, 
				"http://datos.bcn.cl/ontologies/bcn-biographies#Ministro", 
				"Persona que dirige cada uno de los departamentos " +
				"ministeriales en que se divide la gobernación del Estado");
		properties = wesearch.getProperties(m, str);
		assertNotNull(properties);
		assertTrue(properties.iterator().hasNext());
	}
	
	@Then("I should get property $propertyName")
	public void getProperty(String propertyName) throws WesearchException {
		Property p = properties.getPropertyByName(propertyName);
		assertNotNull(p);
		assertEquals(propertyName, p.getName());
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
