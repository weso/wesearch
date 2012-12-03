package org.weso.wesearch.domain.filter;

import static org.junit.Assert.assertEquals;
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
	public void testContructorThreeParameters() 
			throws IllegalArgumentException, IllegalAccessException, 
			NoSuchFieldException, SecurityException {
		SPARQLFilters aux = new SPARQLFilters(new SPARQLFilter("aaaaaa"));
		SPARQLFilters filters = new SPARQLFilters(
				new SPARQLFilter("This is a test filter"), Operator.AND, aux);
		assertNotNull(field.get(filters));
		field = SPARQLFilters.class.getDeclaredField("op");
		field.setAccessible(true);
		assertNotNull(field.get(filters));
		assertEquals(Operator.AND, field.get(filters));
		field = SPARQLFilters.class.getDeclaredField("filters");
		field.setAccessible(true);
		assertNotNull(field.get(filters));
	}
	
	@Test
	public void testAddFilter() 
			throws NoSuchFieldException, SecurityException, 
			IllegalArgumentException, IllegalAccessException {
		SPARQLFilters filters = new SPARQLFilters(new SPARQLFilter("This is a test filter"));
		field = SPARQLFilters.class.getDeclaredField("op");
		field.setAccessible(true);
		assertNull(field.get(filters));
		field = SPARQLFilters.class.getDeclaredField("filters");
		field.setAccessible(true);
		assertNull(field.get(filters));
		filters.addFilter(new SPARQLFilter("aaaaaa"), Operator.AND);		
		assertNotNull(field.get(filters));
		field = SPARQLFilters.class.getDeclaredField("op");
		field.setAccessible(true);
		assertNotNull(field.get(filters));
		assertEquals(Operator.AND, field.get(filters));
	}
	
	@Test
	public void testAddFilterNutNull() 
			throws NoSuchFieldException, SecurityException, 
			IllegalArgumentException, IllegalAccessException {
		SPARQLFilters aux = new SPARQLFilters(new SPARQLFilter("aaaaaa"));
		SPARQLFilters filters = new SPARQLFilters(
				new SPARQLFilter("This is a test filter"), Operator.AND, aux);
		field = SPARQLFilters.class.getDeclaredField("op");
		field.setAccessible(true);
		assertNull(field.get(aux));
		field = SPARQLFilters.class.getDeclaredField("filters");
		field.setAccessible(true);
		assertNull(field.get(aux));
		filters.addFilter(new SPARQLFilter("bbbbbb"), Operator.OR);
		assertNotNull(field.get(aux));
		field = SPARQLFilters.class.getDeclaredField("op");
		field.setAccessible(true);
		assertNotNull(field.get(aux));
		assertEquals(Operator.OR, field.get(aux));
	}

}
