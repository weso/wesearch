package org.weso.wesearch.domain;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.weso.utils.OntoModelException;
import org.weso.utils.QueryBuilderException;
import org.weso.wesearch.domain.impl.JenaPropertyImpl;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.ValueSelectorImpl;
import org.weso.wesearch.domain.impl.filters.Filters;
import org.weso.wesearch.domain.impl.values.DateValue;
import org.weso.wesearch.domain.impl.values.NumericValue;
import org.weso.wesearch.domain.impl.values.StringValue;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

import com.hp.hpl.jena.vocabulary.RDF;
import com.ibm.icu.util.GregorianCalendar;

public class TestSPARQLQueryBuilder {
	
	private OntoModelWrapper model = null;
	private Matter matter = null;
	
	@Before
	public void initialize() {
		String[] files = {"src/test/resources/ontoTest2.owl"};
		model = new JenaOntoModelWrapper(new FileOntologyLoader(files));
		matter = new MatterImpl("Person", 
				"http://xmlns.com/foaf/0.1/Person", 
				"A person.");
	}
	
	@Test
	public void testGetTypeCluase() throws QueryBuilderException {
		String expected = "?sol <" + RDF.type + "> " + 
				"?class";
		String result = SPARQLQueryBuilder.getTypeClause("sol", "class");
		assertEquals(expected, result);
	}
	
	@Test(expected=QueryBuilderException.class) 
	public void testGetTypeClauseMatterNull() throws QueryBuilderException {
		SPARQLQueryBuilder.getTypeClause("sol", null);
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetTypeClauseNameNull() throws QueryBuilderException {
		SPARQLQueryBuilder.getTypeClause(null, "class");
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetTypeClauseBothParamsNull() throws QueryBuilderException {
		SPARQLQueryBuilder.getTypeClause(null, null);
	}
	
	@Test
	public void testGetPropertyClause() throws QueryBuilderException {
		String expected = "?p <http://purl.weso.org/test#Property> ?t";
		Property p = new JenaPropertyImpl("http://purl.weso.org/test#Property", 
				"Property test", "This is a test property");
		String result = SPARQLQueryBuilder.getPropertyClause("p", p, "t");
		assertEquals(expected, result);
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetPropertyClauseNameNull() throws QueryBuilderException {
		Property p = new JenaPropertyImpl("http://purl.weso.org/test#Property",
				"Property test", "This is a test property");
		SPARQLQueryBuilder.getPropertyClause(null, p, "t");
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetPropertyClausePropertyNull() throws QueryBuilderException {
		SPARQLQueryBuilder.getPropertyClause("p", null, "t");
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetPropertyClauseObjNameNull() throws QueryBuilderException {
		Property p = new JenaPropertyImpl("http://purl.weso.org/test#Property",
				"Property test", "This is a test property");
		SPARQLQueryBuilder.getPropertyClause("p", p, null);
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetPropertyClauseAllParamsNull() throws QueryBuilderException {
		SPARQLQueryBuilder.getPropertyClause(null, null, null);
	}
	
	@Test
	public void testGetFilterTextValueSelector() 
			throws QueryBuilderException {
		String expected = "regex(?x, \"test\", \"i\")";
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.TEXT);
		selector.setValue(new StringValue("test"));
		String result = SPARQLQueryBuilder.getFilter("x", selector).getClause();
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetFilterClauseNumericValueSelector() 
			throws QueryBuilderException {
		String expected = "xsd:decimal(?x) = xsd:decimal('5.36')";
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.NUMERIC);
		selector.setValue(new NumericValue(5.36));
		String result = SPARQLQueryBuilder.getFilter("x", selector).getClause();
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetFilterClauseDateValueSelector() 
			throws QueryBuilderException {
		Date now = new GregorianCalendar().getTime();
		String expected = "xsd:date(?x) = xsd:date('" + now + "')";
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.DATE);
		selector.setValue(new DateValue(now));
		String result = SPARQLQueryBuilder.getFilter("x", selector).getClause();
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetFilterClauseUndefinedValueSelector() 
			throws QueryBuilderException {
		String expected = "regex(?x, \"test\", \"i\")";
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.UNDEFINED);
		selector.setValue(new StringValue("test"));
		String result = SPARQLQueryBuilder.getFilter("x", selector).getClause();
		assertEquals(expected, result);
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetFilterClauseNameNull() 
			throws QueryBuilderException {
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.UNDEFINED);
		selector.setValue(new StringValue("test"));
		SPARQLQueryBuilder.getFilter(null, selector);
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetFilterClauseValueSelectorNull() 
			throws QueryBuilderException {
		SPARQLQueryBuilder.getFilter("x", null);
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetFilterClauseValueNull() 
			throws QueryBuilderException {
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.UNDEFINED);
		selector.setValue(null);
		SPARQLQueryBuilder.getFilter("x", selector);
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetFilterClauseValueSelectorObject() 
			throws QueryBuilderException {
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.OBJECT);
		selector.setValue(new StringValue("test"));
		SPARQLQueryBuilder.getFilter("a", selector);
	}
	
	@Test
	public void testGetClassFilter() 
			throws OntoModelException, QueryBuilderException {
		String expected = "?class = <http://xmlns.com/foaf/0.1/Person> || ?class = <http://www.w3.org/2002/07/owl#Nothing> ";
		Filters filters = SPARQLQueryBuilder.getClassFilter("class", matter, model);
		String result = filters.toString();
		assertEquals(expected, result);
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetClassFilterVarNameNull() throws QueryBuilderException, OntoModelException {
		SPARQLQueryBuilder.getClassFilter(null, matter, model);
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetClassFilterMatterNull() 
			throws QueryBuilderException, OntoModelException {	
		SPARQLQueryBuilder.getClassFilter("subject", null, model);
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testGetClassFilterModelNull() 
			throws QueryBuilderException, OntoModelException {
		SPARQLQueryBuilder.getClassFilter("subject", matter, null);
	}
	
}
