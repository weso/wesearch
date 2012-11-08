package org.weso.wesearch.context;

import static org.junit.Assert.*;

import org.junit.Test;
import org.weso.wesearch.context.impl.DefaultContext;

public class TestDefaultContext {
	
	@Test
	public void getOntologyUri() {
		String expected = "http://www.weso.es/emptyOntology";
		DefaultContext ctx = new DefaultContext();
		assertEquals(expected, ctx.getOntologyURI());
	}
	
	@Test
	public void getOntologiesModel() {
		DefaultContext ctx = new DefaultContext();
		assertNull(ctx.getOntologiesModel());
	}

}
