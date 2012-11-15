package org.weso.wesearch.domain;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.weso.utils.SPARQLQueryBuilderException;
import org.weso.wesearch.domain.impl.JenaPropertyImpl;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.ValueSelectorImpl;
import org.weso.wesearch.domain.impl.values.DateValue;
import org.weso.wesearch.domain.impl.values.NumericValue;
import org.weso.wesearch.domain.impl.values.StringValue;

import com.hp.hpl.jena.vocabulary.RDF;
import com.ibm.icu.util.GregorianCalendar;

public class TestSPARQLQueryBuilder {
	
	@Test
	public void testGetTypeCluase() throws SPARQLQueryBuilderException {
		Matter m = new MatterImpl("Label test", "http://purl.weso.org/test#Class", 
				"This is a test comment");
		String expected = "?sol <" + RDF.type + "> " + 
				"<http://purl.weso.org/test#Class>";
		String result = SPARQLQueryBuilder.getTypeClause("sol", m);
		assertEquals(expected, result);
	}
	
	@Test(expected=SPARQLQueryBuilderException.class) 
	public void testGetTypeClauseMatterNull() throws SPARQLQueryBuilderException {
		Matter m = null;
		SPARQLQueryBuilder.getTypeClause("sol", m);
	}
	
	@Test(expected=SPARQLQueryBuilderException.class)
	public void testGetTypeClauseNameNull() throws SPARQLQueryBuilderException {
		Matter m = new MatterImpl("Label test", "http://purl.weso.org/test#Class", 
				"This is a test comment");
		SPARQLQueryBuilder.getTypeClause(null, m);
	}
	
	@Test(expected=SPARQLQueryBuilderException.class)
	public void testGetTypeClauseBothParamsNull() throws SPARQLQueryBuilderException {
		SPARQLQueryBuilder.getTypeClause(null, null);
	}
	
	@Test
	public void testGetPropertyClause() throws SPARQLQueryBuilderException {
		String expected = "?p <http://purl.weso.org/test#Property> ?t";
		Property p = new JenaPropertyImpl("http://purl.weso.org/test#Property", 
				"Property test", "This is a test property");
		String result = SPARQLQueryBuilder.getPropertyClause("p", p, "t");
		assertEquals(expected, result);
	}
	
	@Test(expected=SPARQLQueryBuilderException.class)
	public void testGetPropertyClauseNameNull() throws SPARQLQueryBuilderException {
		Property p = new JenaPropertyImpl("http://purl.weso.org/test#Property",
				"Property test", "This is a test property");
		SPARQLQueryBuilder.getPropertyClause(null, p, "t");
	}
	
	@Test(expected=SPARQLQueryBuilderException.class)
	public void testGetPropertyClausePropertyNull() throws SPARQLQueryBuilderException {
		SPARQLQueryBuilder.getPropertyClause("p", null, "t");
	}
	
	@Test(expected=SPARQLQueryBuilderException.class)
	public void testGetPropertyClauseObjNameNull() throws SPARQLQueryBuilderException {
		Property p = new JenaPropertyImpl("http://purl.weso.org/test#Property",
				"Property test", "This is a test property");
		SPARQLQueryBuilder.getPropertyClause("p", p, null);
	}
	
	@Test(expected=SPARQLQueryBuilderException.class)
	public void testGetPropertyClauseAllParamsNull() throws SPARQLQueryBuilderException {
		SPARQLQueryBuilder.getPropertyClause(null, null, null);
	}
	
	@Test
	public void testGetFilterClauseTextValueSelector() 
			throws SPARQLQueryBuilderException {
		String expected = "FILTER(regex(?x, \"test\", \"i\"))";
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.TEXT);
		selector.setValue(new StringValue("test"));
		String result = SPARQLQueryBuilder.getFilterClause("x", selector);
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetFilterClauseNumericValueSelector() 
			throws SPARQLQueryBuilderException {
		String expected = "FILTER(xsd:decimal(?x) = xsd:decimal('5.36'))";
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.NUMERIC);
		selector.setValue(new NumericValue(5.36));
		String result = SPARQLQueryBuilder.getFilterClause("x", selector);
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetFilterClauseDateValueSelector() 
			throws SPARQLQueryBuilderException {
		Date now = new GregorianCalendar().getTime();
		String expected = "FILTER(xsd:date(?x) = xsd:date('" + now + "'))";
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.DATE);
		selector.setValue(new DateValue(now));
		String result = SPARQLQueryBuilder.getFilterClause("x", selector);
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetFilterClauseUndefinedValueSelector() 
			throws SPARQLQueryBuilderException {
		String expected = "FILTER()";
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.UNDEFINED);
		selector.setValue(new StringValue("test"));
		String result = SPARQLQueryBuilder.getFilterClause("x", selector);
		assertEquals(expected, result);
	}
	
	@Test(expected=SPARQLQueryBuilderException.class)
	public void testGetFilterClauseNameNull() 
			throws SPARQLQueryBuilderException {
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.UNDEFINED);
		selector.setValue(new StringValue("test"));
		SPARQLQueryBuilder.getFilterClause(null, selector);
	}
	
	@Test(expected=SPARQLQueryBuilderException.class)
	public void testGetFilterClauseValueSelectorNull() 
			throws SPARQLQueryBuilderException {
		SPARQLQueryBuilder.getFilterClause("x", null);
	}
	
	@Test(expected=SPARQLQueryBuilderException.class)
	public void testGetFilterClauseValueNull() 
			throws SPARQLQueryBuilderException {
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.UNDEFINED);
		selector.setValue(null);
		SPARQLQueryBuilder.getFilterClause("x", selector);
	}
	
}
