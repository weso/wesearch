package org.weso.wesearch.domain.filter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.weso.wesearch.domain.impl.filters.Filter;
import org.weso.wesearch.domain.impl.filters.Operator;
import org.weso.wesearch.domain.impl.filters.SPARQLFilter;
import org.weso.wesearch.domain.impl.filters.SPARQLFilters;

public class TestSPARQLFilters {
	
	private Field field;
	
	@Before
	public void setUp() throws NoSuchFieldException, SecurityException {
		field = SPARQLFilters.class.getDeclaredField("filter");
		field.setAccessible(true);
	}
	
	@Test
	public void testConstructorOneParameter() 
			throws NoSuchFieldException, SecurityException, 
			IllegalArgumentException, IllegalAccessException {
		SPARQLFilters filters = new SPARQLFilters(new SPARQLFilter("This is a test filter"));
		Filter result = (Filter)field.get(filters);
		assertNotNull(result);
	}
	
	@Test
	public void testConstructorOneParameterNull() 
			throws IllegalArgumentException, IllegalAccessException {
		SPARQLFilters filter = new SPARQLFilters(null);
		assertNull(field.get(filter));
	}
	
	@Test
	public void testContructorThreeParameters() {
		SPARQLFilters aux = new SPARQLFilters(new SPARQLFilter("aaaaaa"));
		SPARQLFilters filters = new SPARQLFilters(
				new SPARQLFilter("This is a test filter"), Operator.AND, aux);
	}

}
