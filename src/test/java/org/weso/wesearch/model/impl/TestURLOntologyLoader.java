package org.weso.wesearch.model.impl;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestURLOntologyLoader {
	
	private String[] uris = {"http://xmlns.com/foaf/spec/index.rdf", 
			"http://purl.org/dc/elements/1.1/", 
			"http://www.weso.es/failTest"}; 
	
	private List<String> listUris = new LinkedList<String>();
	
	@Before
	public void configure() {
		listUris.add("http://xmlns.com/foaf/spec/index.rdf");
		listUris.add("http://purl.org/dc/elements/1.1/");
		listUris.add("http://www.weso.es/failTest");
	}
	
	@Test
	public void testOpenInputStream() throws IOException {
		URLOntologyLoader loader = new URLOntologyLoader(uris);
		InputStream in = loader.openInputStream(uris[0]);
		assertNotNull(in);
		in.close();
	}
	
	@Test(expected=FileNotFoundException.class)
	public void testOpenInputStreamFail() throws IOException {
		URLOntologyLoader loader = new URLOntologyLoader(listUris);
		loader.openInputStream(uris[2]);
	}
	

}
