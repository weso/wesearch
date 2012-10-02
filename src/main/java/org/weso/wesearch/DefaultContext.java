package org.weso.wesearch;

import java.util.List;

public class DefaultContext implements Context {

	public final String uri = "http://www.weso.es/emptyOntology";
	
	public String getOntologyURI() {
		return uri;
	}

	@Override
	public void addOntology(String uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> ontologies() {
		// TODO Auto-generated method stub
		return null;
	}

}
