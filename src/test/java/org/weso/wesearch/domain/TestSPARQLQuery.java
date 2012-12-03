package org.weso.wesearch.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.weso.utils.QueryBuilderException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.domain.impl.SPARQLQuery;
import org.weso.wesearch.domain.impl.filters.Filter;
import org.weso.wesearch.domain.impl.filters.Operator;
import org.weso.wesearch.domain.impl.filters.SPARQLFilter;
import org.weso.wesearch.domain.impl.filters.SPARQLFilters;

public class TestSPARQLQuery {
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAddClause() throws IOException, 
		NoSuchFieldException, SecurityException, 
		IllegalArgumentException, IllegalAccessException {
		SPARQLQuery query = new SPARQLQuery();
		Field field = SPARQLQuery.class.getDeclaredField("clauses");
		field.setAccessible(true);
		assertNotNull(field.get(query));
		assertTrue(((List<String>)field.get(query)).size() == 0);
		query.addClause("This is a test clause");
		assertTrue(((List<String>)field.get(query)).size() == 1);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAddClauseNull() throws IOException, 
		NoSuchFieldException, SecurityException, 
		IllegalArgumentException, IllegalAccessException {
		SPARQLQuery query = new SPARQLQuery();
		Field field = SPARQLQuery.class.getDeclaredField("clauses");
		field.setAccessible(true);
		assertNotNull(field.get(query));
		assertTrue(((List<String>)field.get(query)).size() == 0);
		query.addClause(null);
		assertTrue(((List<String>)field.get(query)).size() == 0);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAddFilters() throws IOException, 
		NoSuchFieldException, SecurityException, 
		IllegalArgumentException, IllegalAccessException, QueryBuilderException {
		SPARQLQuery query = new SPARQLQuery();
		Field field = SPARQLQuery.class.getDeclaredField("filters");
		field.setAccessible(true);
		Map<String, Filter> filters = (Map<String, Filter>)field.get(query);
		assertNull(filters.get("var"));
		query.addFilters("var", new SPARQLFilters(
				new SPARQLFilter("This is a test filter")));
		assertNotNull(filters.get("var"));
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testtAddFiltersVarNameNull() throws IOException, QueryBuilderException {
		SPARQLQuery query = new SPARQLQuery();
		query.addFilters(null, new SPARQLFilters(
				new SPARQLFilter("This is a test filter")));
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testAddFiltersFiltersNull() throws IOException, 
		QueryBuilderException {
		SPARQLQuery query = new SPARQLQuery();
		query.addFilters("var", null);
	}
	
	@Test(expected=QueryBuilderException.class)
	public void testAddFilterVarNameNull() throws IOException, QueryBuilderException {
		SPARQLQuery query = new SPARQLQuery();
		query.addFilter(null, new SPARQLFilter("This is a test filter"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAddFilterFilterNull() throws IOException, 
		QueryBuilderException, NoSuchFieldException, SecurityException, 
		IllegalArgumentException, IllegalAccessException {
		SPARQLQuery query = new SPARQLQuery();
		Field field = SPARQLQuery.class.getDeclaredField("filters");
		field.setAccessible(true);
		Map<String, Filter> filters = (Map<String, Filter>)field.get(query);
		assertFalse(filters.containsKey("var"));
		query.addFilter("var", null);
		assertTrue(filters.containsKey("var"));
		assertNull(filters.get("var"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAddFilter() throws IOException, 
		NoSuchFieldException, SecurityException, 
		IllegalArgumentException, IllegalAccessException, QueryBuilderException {
		SPARQLQuery query = new SPARQLQuery();
		Field field = SPARQLQuery.class.getDeclaredField("filters");
		field.setAccessible(true);
		Map<String, Filter> filters = (Map<String, Filter>)field.get(query);
		assertNull(filters.get("var"));
		query.addFilter("var", new SPARQLFilter("This is a test filter"));
		SPARQLFilters filter = (SPARQLFilters)filters.get("var");
		assertNotNull(filter);
		Field operator = SPARQLFilters.class.getDeclaredField("op");
		operator.setAccessible(true);
		Field filtersField = SPARQLFilters.class.getDeclaredField("filters");
		filtersField.setAccessible(true);
		assertNull(operator.get(filter));
		assertNull(filtersField.get(filter));
		query.addFilter("var", new SPARQLFilter("This is other test filter"));
		filters = (Map<String, Filter>)field.get(query);
		filter = (SPARQLFilters)filters.get("var");
		assertNotNull(operator.get(filter));
		assertNotNull(filtersField.get(filter));
		assertEquals(Operator.AND, (Operator)operator.get(filter));
	}
	
	@Test
	public void testGetNextVarName() throws IOException, WesearchException {
		SPARQLQuery query = new SPARQLQuery();
		String expected = "a";
		String result = query.getNextVarName();
		assertEquals(expected, result);
		expected = "b";
		result = query.getNextVarName();
		assertEquals(expected, result);
	}
	
	@Test(expected=WesearchException.class)
	public void testGetNextVarNameException() 
			throws NoSuchFieldException, SecurityException, IOException, 
			IllegalArgumentException, IllegalAccessException, WesearchException {
		SPARQLQuery query = new SPARQLQuery();
		Field nextVar = SPARQLQuery.class.getDeclaredField("nextVar");
		nextVar.setAccessible(true);
		nextVar.setInt(query, -5);
		query.getNextVarName();
	}
	
	@Test(expected=RuntimeException.class)
	public void testGetAuxiliarVarNameException() throws IOException {
		SPARQLQuery query = new SPARQLQuery();
		query.getAuxiliarVarName();
	}
	
	@Test
	public void testGetAuxiliarVarName() throws IOException, QueryBuilderException {
		SPARQLQuery query = new SPARQLQuery();
		String expected = "var";
		query.addFilter("var", null);
		String result = query.getAuxiliarVarName();
		assertEquals(expected, result);
	}
	
	@Test
	public void testIsPropertyForResult() throws IOException, QueryBuilderException {
		SPARQLQuery query = new SPARQLQuery();
		boolean expected = true;
		boolean result = query.isPropertyForResult();
		assertEquals(expected, result);
		query.addFilter("var", null);
		expected = false;
		result = query.isPropertyForResult();
		assertEquals(expected, result);
	}

}
