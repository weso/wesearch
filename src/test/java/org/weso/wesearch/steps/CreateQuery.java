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
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.Query;
import org.weso.wesearch.domain.ValueSelector;
import org.weso.wesearch.domain.impl.JenaPropertyImpl;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.ValueSelectorImpl;
import org.weso.wesearch.domain.impl.values.StringValue;
import org.weso.wesearch.factories.WesearchFactory;
import org.weso.wesearch.factories.impl.JenaWesearchFactory;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

public class CreateQuery extends Steps {
	
	private Wesearch wesearch;
	private Matter matter;
	private Property property;
	private ValueSelector valueSelector;
	private Query query;
	
	@Given("a matter $m, property $p and value selector $selector with value $value")
	public void loadQueryParameters(String m, String p, String selector, String value) 
			throws WesearchException, OntoModelException {
		String[] ontologies = {"src/test/resources/ontoTest2.owl"};
    	WesearchFactory factory = new JenaWesearchFactory();
    	OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(new FileOntologyLoader(ontologies));
    	wesearch = factory.createWesearch(modelWrapper);
		String uri = "http://datos.bcn.cl/ontologies/bcn-biographies#" + p;
		property = new JenaPropertyImpl(uri, "nacido_en", 
				"Indica donde ha nacido un ministro");
		String matterUri = "http://datos.bcn.cl/ontologies/bcn-biographies#" + m;
		matter = new MatterImpl("Ministro", matterUri, 
				"Persona que dirige cada uno de los departamentos " +
				"ministeriales en que se divide la gobernación del Estado");
		valueSelector = new ValueSelectorImpl(selector);
		valueSelector.setValue(new StringValue(value));
		assertNotNull(wesearch);
		assertNotNull(matter);
		assertNotNull(property);
		assertNotNull(valueSelector);
	}
	
	@When("I ask for a query")
	public void askQuery() throws WesearchException {
		query = wesearch.createQuery(matter, property, valueSelector);
		assertNotNull(query);
	}
	
	@Then("I get query $sparqlQuery")
	public void getQuery(String sparqlQuery) {
		String q = query.obtainQuery();
		System.out.println(q);
		assertTrue(q.equals(sparqlQuery));
	}

}
