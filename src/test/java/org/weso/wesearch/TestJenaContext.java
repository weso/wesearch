package org.weso.wesearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class TestJenaContext {
	
	@Test
	public void testOntologies() {
		JenaContext ctx = new JenaContext();
		assertNotNull(ctx.ontologies());
		assertTrue(ctx.ontologies() instanceof List);
	}
	
	@Test
	public void testAddOntology() {
		JenaContext ctx = new JenaContext();
		assertEquals(0, ctx.ontologies().size());
		ctx.addOntology("ontology1");
		assertFalse(ctx.ontologies().size() == 0);
		assertEquals(1, ctx.ontologies().size());
	}

}
