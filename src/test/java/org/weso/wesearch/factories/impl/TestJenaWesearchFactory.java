package org.weso.wesearch.factories.impl;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.weso.wesearch.JenaWesearch;
import org.weso.wesearch.Wesearch;

public class TestJenaWesearchFactory {
	
	private String[] ontologies = {"http://xmlns.com/foaf/spec/index.rdf", "http://purl.org/dc/elements/1.1/"};
	private List<String> listOnto = new LinkedList<String>();
	
	@Before
	public void initialize() {
		listOnto.add("http://xmlns.com/foaf/spec/index.rdf");
		listOnto.add("http://purl.org/dc/elements/1.1/");
	}
	
	@Test
	public void testCreateWesearchList() {
		JenaWesearchFactory factory = new JenaWesearchFactory();
		Wesearch wesearch = factory.createWesearch(listOnto); 
		assertNotNull(wesearch);
		assertTrue(wesearch instanceof JenaWesearch);
	}
	
	@Test
	public void testCreateWesearchArray() {
		JenaWesearchFactory factory = new JenaWesearchFactory();
		Wesearch wesearch = factory.createWesearch(ontologies);
		assertNotNull(wesearch);
		assertTrue(wesearch instanceof JenaWesearch);
	}

}
