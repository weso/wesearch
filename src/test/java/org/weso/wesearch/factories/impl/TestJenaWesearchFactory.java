package org.weso.wesearch.factories.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.JenaWesearch;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

public class TestJenaWesearchFactory {
	
	private String[] ontologies = {"src/test/resources/ontoTest1.owl", "src/test/resources/index.rdf"};
	private List<String> listOnto = new LinkedList<String>();
	private OntoModelWrapper wrapper;
	
	@Before
	public void initialize() {
		listOnto.add("src/test/resources/ontoTest1.owl");
		listOnto.add("src/test/resources/index.rdf");
	}
	
	@Test
	public void testCreateWesearchList() throws WesearchException, OntoModelException {
		wrapper = new JenaOntoModelWrapper(new FileOntologyLoader(listOnto));
		JenaWesearchFactory factory = new JenaWesearchFactory();
		Wesearch wesearch = factory.createWesearch(wrapper); 
		assertNotNull(wesearch);
		assertTrue(wesearch instanceof JenaWesearch);
	}
	
	@Test
	public void testCreateWesearchArray() throws WesearchException, OntoModelException {
		wrapper = new JenaOntoModelWrapper(new FileOntologyLoader(ontologies));
		JenaWesearchFactory factory = new JenaWesearchFactory();
		Wesearch wesearch = factory.createWesearch(wrapper);
		assertNotNull(wesearch);
		assertTrue(wesearch instanceof JenaWesearch);
	}

}
