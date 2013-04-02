package org.weso.wesearch.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.weso.wesearch.domain.impl.JenaPropertyImpl;

public class TestJenaPropertyImpl {
	
	private JenaPropertyImpl property;
	
	@Before
	public void setUp() {
		property = new JenaPropertyImpl(
				"http://www.weso.es/ontologyTest/uriTest", "nameTest", 
				"test description");
	}
	
	@Test
	public void testGetUri() {
		String expected = "http://www.weso.es/ontologyTest/uriTest";
		assertEquals(expected, property.getUri());
	}
	
	@Test
	public void testGetName() {
		String expected = "nameTest";
		assertEquals(expected, property.getLabel());
	}
	
	@Test
	public void testGetDescription() {
		String expected = "test description";
		assertEquals(expected, property.getDescription());
	}
	
	@Test
	public void testSetUri() {
		String expected = "http://www.weso.es/ontologyTest/newUri";
		property.setUri("http://www.weso.es/ontologyTest/newUri");
		assertEquals(expected, property.getUri());
		assertTrue(!expected.equals("http://www.weso.es/ontologyTest/uriTest"));
	}
	
	@Test
	public void testSetDescription() {
		String expected = "new test description";
		property.setDescription("new test description");
		assertEquals(expected, property.getDescription());
		assertTrue(!expected.equals("test description"));
	}
	
	@Test
	public void testSetName() {
		String expected = "new name";
		property.setLabel("new name");
		assertEquals(expected, property.getLabel());
		assertTrue(!expected.equals("nameTest"));
	}
	
	@Test
	public void testEqualsSameObject() {
		assertTrue(property.equals(property));
	}
	
	@Test
	public void testEqualsWithNull() {
		assertFalse(property.equals(null));
	}
	
	@Test
	public void testEqualsDifferentClass() {
		String str = "hola";
		assertFalse(property.equals(str));
	}
	
	@Test
	public void testEquals() {
		JenaPropertyImpl prop = new JenaPropertyImpl("http://www.weso.es/ontologyTest/uriTest", "name test", 
				"other test description");
		assertTrue(property.equals(prop));
	}
	
	@Test
	public void testEqualsWithUriNull () {
		property.setUri(null);
		JenaPropertyImpl prop = new JenaPropertyImpl("http://www.weso.es/ontologyTest/uriTest", "name test", 
				"other test description");
		assertFalse(property.equals(prop));
		property.setUri("http://www.weso.es/ontologyTest/uriTest");
		prop.setUri(null);
		assertFalse(property.equals(prop));
	}
	
	@Test
	public void testHashCodeWithUriNull() {
		int expected = 31;
		JenaPropertyImpl prop = new JenaPropertyImpl(null, "name test", 
				"other test description");
		assertEquals(expected, prop.hashCode());
	}
	
	@Test
	public void testHashCode() {
		JenaPropertyImpl prop = new JenaPropertyImpl(
				"http://www.weso.es/ontologyTest/uriTest", "name test", 
				"other test description");
		assertEquals(property.hashCode(), prop.hashCode());
	}
	
	@Test
	public void testContructorWithoutParameters() {
		JenaPropertyImpl prop = new JenaPropertyImpl();
		assertEquals("", prop.getDescription());
		assertEquals("", prop.getLabel());
		assertEquals("", prop.getUri());
	}

}
