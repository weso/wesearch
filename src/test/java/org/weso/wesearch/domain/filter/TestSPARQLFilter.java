package org.weso.wesearch.domain.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.weso.wesearch.domain.impl.filters.SPARQLFilter;

public class TestSPARQLFilter {
	
	@Test
	public void testGetClause() {
		String expected = "This is a test string";
		SPARQLFilter filter = new SPARQLFilter("This is a test string");
		String result = filter.getClause();
		assertEquals(expected, result);
		filter = new SPARQLFilter(null);
		result = filter.getClause();
		assertNull(result);
	}
	
	@Test
	public void testConstructorWithoutParameters() {
		SPARQLFilter filter = new SPARQLFilter();
		assertEquals("", filter.getClause());
	}
	
	@Test
	public void testSetString() {
		SPARQLFilter filter = new SPARQLFilter();
		assertEquals("", filter.getClause());
		filter.setString("test clause");
		assertEquals("test clause", filter.getClause());
	}

}
