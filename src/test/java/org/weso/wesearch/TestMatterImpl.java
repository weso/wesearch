package org.weso.wesearch;

import static org.junit.Assert.*;

import org.junit.Test;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.impl.MatterImpl;

public class TestMatterImpl {
	
	@Test
	public void testLabel() {
		String expected = "label test";
		Matter m = new MatterImpl("label test", "http://www.weso.es/testOntology#Matter", "Description");
		assertEquals(m.label(), expected);
		assertTrue(m.label().equals(expected));
		assertFalse(m.label().equals("other label"));
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
		assertEquals(expected, actual.uri());
		assertFalse(unexpected.equals(actual.uri()));
	}
	
	@Test
	public void testDescription() {
		String expected = "Description for test";
		String unexpected1 = "http://www.weso.es/testOntology#Matter";
		String unexpected2 = "Matter test";
		Matter actual = new MatterImpl("Matter test", "http://www.weso.es/testOntology#Matter", "Description for test");
		assertEquals(expected, actual.description());
		assertNotSame(unexpected1, actual.description());
		assertNotSame(unexpected2, actual.description());
	}

}
