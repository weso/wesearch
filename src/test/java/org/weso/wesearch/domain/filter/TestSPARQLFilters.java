package org.weso.wesearch.domain.filter;

import org.junit.Test;
import org.weso.wesearch.domain.impl.filters.SPARQLFilters;

public class TestSPARQLFilters {
	
	@Test
	public void testConstructorOneParameter() {
		SPARQLFilters filters = new SPARQLFilters(null);
	}

}
