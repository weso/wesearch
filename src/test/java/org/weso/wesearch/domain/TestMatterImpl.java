package org.weso.wesearch.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.impl.MatterImpl;

public class TestMatterImpl {
	
	@Test
	public void testLabel() {
		String expected = "label test";
		Matter m = new MatterImpl("label test", "http://www.weso.es/testOntology#Matter", "Description");
		assertEquals(m.getLabel(), expected);
		assertTrue(m.getLabel().equals(expected));
		assertFalse(m.getLabel().equals("other label"));
	}
	
	@Test
	public void testEqual() {
		Matter expected = new MatterImpl("Matter 1", "http://www.weso.es/testOntology#Matter", "Description");
		Matter unexpected = new MatterImpl("Matter 2", "http://www.weso.es/testOntology", "Description");
		Matter actual = new MatterImpl("Matter 1", "http://www.weso.es/testOntology#Person", "Description");
		assertTrue(actual.equals(expected));
		assertFalse(actual.equals(unexpected));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEqualWithNull() {
		Matter actual = new MatterImpl("Matter 1", "http://www.weso.es/testOntology#Person", "Description");
		assertFalse(actual.equals(null));
	}
	
	@Test
	public void testUri() {
		String expected = "http://www.weso.es/testOntology#Matter";
		String unexpected = "http://www.weso.es/testOntology#MatterTest";
		Matter actual = new MatterImpl("Matter test", "http://www.weso.es/testOntology#Matter", "Description");
		assertEquals(expected, actual.getUri());
		assertFalse(unexpected.equals(actual.getUri()));
	}
	
	@Test
	public void testDescription() {
		String expected = "Description for test";
		String unexpected1 = "http://www.weso.es/testOntology#Matter";
		String unexpected2 = "Matter test";
		Matter actual = new MatterImpl("Matter test", "http://www.weso.es/testOntology#Matter", "Description for test");
		assertEquals(expected, actual.getDescription());
		assertNotSame(unexpected1, actual.getDescription());
		assertNotSame(unexpected2, actual.getDescription());
	}
	
	@Test
	public void testToString() {
		String expected = "uriPrueba labelTest comentario";
		Matter m = new MatterImpl("labelTest", "uriPrueba", "comentario");
		assertEquals(expected, m.toString());
	}

}
