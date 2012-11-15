package org.weso.wesearch.domain;


import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.weso.utils.WesearchException;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.SubjectsImpl;

public class TestSubjectsImpl {
	
	private Matters matters;
	
	@Before
	public void initialize() {
		matters = new SubjectsImpl();
		Matter m = new MatterImpl("Matter test 1", "http://www.weso.es/testOntology#Person", "Descripcion for test");
		matters.addMatter(m);
		m = new MatterImpl("Matter test 2", "http://www.weso.es/testOntology#Thing", "Descripcion for test");
		matters.addMatter(m);
		m = new MatterImpl("Matter test 3", "http://www.weso.es/testOntology#Matter", "Descripcion for test");
		matters.addMatter(m);
		m = new MatterImpl("Matter test 4", "http://www.weso.es/testOntology#Place", "Descripcion for test");
		matters.addMatter(m);
	}
	
	@Test
	public void testIterator() {
		Iterator<Matter> iterator = matters.iterator();
		assertNotNull(iterator);
	}
	
	@Test
	public void testSize() {
		assertEquals(4, matters.size());
		Matter m = new MatterImpl("Label test", "http://www.weso.es/testOntology#Matter", "Descripcion for test");
		matters.addMatter(m);
		assertEquals(5, matters.size());
		Iterator<Matter> iterator = matters.iterator();
		iterator.next();
		iterator.remove();
		assertEquals(4, matters.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddMatter() {
		Matter m = new MatterImpl("Label 1", "http://www.weso.es/testOntology#Matter", "Descripcion for test");
		matters.addMatter(m);
		assertEquals(5, matters.size());
		m = new MatterImpl("Label 2", "http://www.weso.es/testOntology#Matter", "Descripcion for test");
		matters.addMatter(m);
		assertEquals(6, matters.size());
		matters.addMatter(null);
	}
	
	@Test
	public void testFindMatterExpected() throws WesearchException {
		Matter expected = new MatterImpl("Matter test 3", "http://www.weso.es/testOntology#Matter", "Descripcion for test");
		Matter actual = null;
		actual = matters.findMatter("Matter test 3");
		assertTrue(actual.equals(expected));		
	}
	
	@Test
	public void testFindMatterIncorrectUnexpected() throws WesearchException {
		Matter unexpected = new MatterImpl("Matter test", "http://www.weso.es/testOntology#MatterTest", "Descripcion for test");
		Matter actual = null;
		actual = matters.findMatter("Matter test 1");
		assertFalse(actual.equals(unexpected));
	}
	
	@Test(expected=WesearchException.class)
	public void testFindMatterNonExistent() throws WesearchException {
		matters.findMatter("Matter test 5");
	}

}
