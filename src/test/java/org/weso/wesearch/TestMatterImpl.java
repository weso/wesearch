package org.weso.wesearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestMatterImpl {
	
	@Test
	public void testLabel() {
		String expected = "label test";
		Matter m = new MatterImpl("label test", "http://www.weso.es/testOntology#Matter");
		assertEquals(m.label(), expected);
		assertTrue(m.label().equals(expected));
		assertFalse(m.label().equals("other label"));
	}
	
	@Test
	public void testEqual() {
		Matter expected = new MatterImpl("Matter 1", "http://www.weso.es/testOntology#Matter");
		Matter unexpected = new MatterImpl("Matter 2", "http://www.weso.es/testOntology");
		Matter actual = new MatterImpl("Matter 1", "http://www.weso.es/testOntology#Person");
		assertTrue(actual.equals(expected));
		assertFalse(actual.equals(unexpected));
	}
	
	@Test
	public void testUri() {
		String expected = "http://www.weso.es/testOntology#Matter";
		String unexpected = "http://www.weso.es/testOntology#MatterTest";
		Matter actual = new MatterImpl("Matter test", "http://www.weso.es/testOntology#Matter");
		assertEquals(expected, actual.uri());
		assertFalse(unexpected.equals(actual.uri()));
	}

}
