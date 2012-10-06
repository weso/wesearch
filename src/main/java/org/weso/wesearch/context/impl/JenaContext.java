package org.weso.wesearch.context.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.weso.wesearch.context.Context;

public class JenaContext implements Context {
	
	private static Logger logger = Logger.getLogger(JenaContext.class);
	
	private List<String> ontologies;
	
	public JenaContext() {
		ontologies = new LinkedList<String>();
	}
	
	@Override
	public void addOntology(String uri) {
		if(uri == null) {
			logger.error("Parameter \"uri\" can not be null");
			throw new IllegalArgumentException("Parameter \"uri\" can not be null");
		}
		ontologies.add(uri);
	}

	@Override
	public List<String> ontologies() {
		return ontologies;
	}

}
