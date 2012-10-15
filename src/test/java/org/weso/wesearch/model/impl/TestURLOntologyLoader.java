package org.weso.wesearch.model.impl;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class TestURLOntologyLoader {
	
	private String[] uris = {"http://xmlns.com/foaf/spec/index.rdf", "http://purl.org/dc/elements/1.1/"}; 
	
	@Before
	public void configure() {
		System.setProperty("http.proxyHost", "proxy.uniovi.es");
		System.setProperty("http.proxyPort", "8888");
	}
	
	@Test
	public void testOpenInputStream() throws FileNotFoundException {
		URLOntologyLoader loader = new URLOntologyLoader(uris);
		InputStream in = loader.openInputStream(uris[0]);
		assertNotNull(in);
	}
	

}