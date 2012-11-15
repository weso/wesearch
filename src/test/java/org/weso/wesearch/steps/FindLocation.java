package org.weso.wesearch.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.ValueSelector;
import org.weso.wesearch.domain.impl.JenaPropertyImpl;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.factories.WesearchFactory;
import org.weso.wesearch.factories.impl.JenaWesearchFactory;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

public class FindLocation extends Steps {

	private Wesearch wesearch;
	private ValueSelector valueSelector;
	
	@Given("an ontology is loaded with Minitros and nacido_en")
	public void loadMinistrosOntology() throws WesearchException, OntoModelException {
		String[] ontologies = {"src/test/resources/ontoTest2.owl"};
    	WesearchFactory factory = new JenaWesearchFactory();
    	OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(new FileOntologyLoader(ontologies));
    	wesearch = factory.createWesearch(modelWrapper);
	}
	
	@When("I ask for value selector of $p, $m and $str")
	public void askForValueSelector(String p, String m, String str) throws WesearchException {
		String uri = "http://datos.bcn.cl/ontologies/bcn-biographies#" + p;
		Property prop = new JenaPropertyImpl(uri, "nacido_en", 
				"Indica donde ha nacido un ministro");
		String matterUri = "http://datos.bcn.cl/ontologies/bcn-biographies#" + m;
		Matter matter = new MatterImpl("Ministro", matterUri, 
				"Persona que dirige cada uno de los departamentos " +
				"ministeriales en que se divide la gobernación del Estado");
		valueSelector = wesearch.getValueSelector(matter, prop);
		assertNotNull(valueSelector);
	}
	
	@Then("I should get value selector $valueSelectorType")
	public void getValueSelector(String valueSelectorType) {
		assertEquals(valueSelectorType, valueSelector.getType());
	}
}
