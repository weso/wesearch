package org.weso.wesearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestMatterImpl {
	
	@Test
	public void testLabel() {
		String expected = "label test";
		Matter m = new MatterImpl("label test");
		assertEquals(m.label(), expected);
		assertTrue(m.label().equals(expected));
		assertFalse(m.label().equals("other label"));
	}
	
	@Test
	public void testEqual() {
		Matter expected = new MatterImpl("Matter 1");
		Matter unexpected = new MatterImpl("Matter 2");
		Matter actual = new MatterImpl("Matter 1");
		assertTrue(actual.equals(expected));
		assertFalse(actual.equals(unexpected));
	}

}
