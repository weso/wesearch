package org.weso.wesearch.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.lang.reflect.Field;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.weso.utils.OntoModelException;
import org.weso.utils.QueryBuilderException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.Query;
import org.weso.wesearch.domain.SPARQLQueryBuilder;
import org.weso.wesearch.domain.ValueSelector;
import org.weso.wesearch.domain.impl.JenaPropertyImpl;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.SPARQLQuery;
import org.weso.wesearch.domain.impl.ValueSelectorImpl;
import org.weso.wesearch.domain.impl.values.StringValue;
import org.weso.wesearch.factories.WesearchFactory;
import org.weso.wesearch.factories.impl.JenaWesearchFactory;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

public class CombineQuery extends Steps {
	
	private Matter matter;
	private Property property;
	private ValueSelector selector;
	private Query query;
	private Wesearch wesearch;
	private Query resultQuery;
	
	@Given("a matter $m, property $p, value selector $selector, value $value and clauses query $clause1 and $clause2")
	public void loadQueryAndParameters(String m, String p, String selector, String value, 
			String clause1, String clause2) throws 
			IOException, WesearchException, OntoModelException, 
			QueryBuilderException, 
			NoSuchFieldException, SecurityException, 
			IllegalArgumentException, IllegalAccessException {
		Field nextVar = SPARQLQuery.class.getDeclaredField("nextVar");
		nextVar.setAccessible(true);
		Matter aux = new MatterImpl("Ministro", 
				"http://datos.bcn.cl/ontologies/bcn-biographies#Ministro", 
				"Persona que dirige cada uno de los departamentos ministeriales " +
				"en que se divide la gobernación del Estado");
		String[] ontologies = {"src/test/resources/ontoTest2.owl"};
		WesearchFactory factory = new JenaWesearchFactory();
    	OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
    			new FileOntologyLoader(ontologies));
    	wesearch = factory.createWesearch(modelWrapper);
		matter = new MatterImpl("País", m, "Define un país");
		property = new JenaPropertyImpl(p, "Nombre", 
				"Define el nombre de un país");
		this.selector = new ValueSelectorImpl(selector);
		this.selector.setValue(new StringValue(value));
		query = new SPARQLQuery();
		query.addClause(clause1);
		query.addClause(clause2);
		query.addFilters("a", SPARQLQueryBuilder.getClassFilter("a", aux, modelWrapper));
		query.addFilter("b", null);
		nextVar.setInt(query, 1);
	}
	
	@When("I ask for a combined query")
	public void askForCombinedQuery() throws WesearchException {
		resultQuery = wesearch.combineQuery(query, matter, property, selector);
		assertNotNull(resultQuery);
	}
	
	@Then("I get combined query $query")
	public void getCombinedQuery(String query) {
		assertEquals(query, resultQuery.getQuery());
	}
	
	

}
