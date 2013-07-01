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
	
	@Given("The ontology $onto")
	public void loadMinistrosOntology(String onto) throws WesearchException, 
		OntoModelException {
		String[] ontologies = {onto};
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(ontologies));
		wesearch = factory.createWesearch(modelWrapper);
	}
	
	@When("I ask for value selector of $m and $p")
	public void askForValueSelector(String p, String m) 
			throws WesearchException {
		p = p.replace("\"", "");
		m = m.replace("\"", "");
		Property prop = new JenaPropertyImpl(p, "", 
				"");
		Matter matter = new MatterImpl("", m, 
				"");
		valueSelector = wesearch.getValueSelector(matter, prop);
		assertNotNull(valueSelector);		
	}
	
	@Then("I should get value selector $valueSelectorType")
	public void getValueSelector(String valueSelectorType) {
		assertEquals(valueSelectorType, valueSelector.getType());
		
	}

}
