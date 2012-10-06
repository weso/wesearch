package org.weso.wesearch.context.impl;

import java.util.List;

import org.weso.wesearch.context.Context;

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
