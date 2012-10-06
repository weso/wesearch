package org.weso.wesearch;


import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.weso.utils.WesearchException;

public class TestSubjectsImpl {
	
	private Matters matters;
	
	@Before
	public void initialize() {
		matters = new SubjectsImpl();
	}
	
	@Test
	public void testIterator() {
		Iterator<Matter> iterator = matters.iterator();
		assertNotNull(iterator);
	}
	
	@Test
	public void testSize() {
		assertEquals(0, matters.size());
		Matter m = new MatterImpl("Label test");
		matters.addMatter(m);
		assertEquals(1, matters.size());
		Iterator<Matter> iterator = matters.iterator();
		iterator.next();
		iterator.remove();
		assertEquals(0, matters.size());
	}
	
	@Test
	public void testAddMatter() {
		Matter m = new MatterImpl("Label 1");
		matters.addMatter(m);
		assertEquals(1, matters.size());
		m = new MatterImpl("Label 2");
		matters.addMatter(m);
		assertEquals(2, matters.size());
		try {
			matters.addMatter(null);
			fail("This test must throw an exception");
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testFindMatter() {
		Matter expected = new MatterImpl("Matter test 3");
		Matter unexpected = new MatterImpl("Matter test");
		Matter m = new MatterImpl("Matter test 1");
		matters.addMatter(m);
		m = new MatterImpl("Matter test 2");
		matters.addMatter(m);
		m = new MatterImpl("Matter test 3");
		matters.addMatter(m);
		m = new MatterImpl("Matter test 4");
		matters.addMatter(m);
		Matter actual = null;
		try {
			actual = matters.findMatter("Matter test 3");
			assertTrue(actual.equals(expected));
		} catch (WesearchException e) {
			assertTrue(false);
		}
		try {
			actual = matters.findMatter("Matter test 1");
			assertFalse(actual.equals(unexpected));
		} catch (WesearchException e) {
			assertTrue(false);
		}
		try {
			actual = matters.findMatter("Matter test 5");
			fail("This test must throw an exception");
		} catch (WesearchException e) {
			assertTrue(true);
		}
	}

}