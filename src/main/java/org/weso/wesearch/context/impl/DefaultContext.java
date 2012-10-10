package org.weso.wesearch.context.impl;

import org.weso.wesearch.context.Context;
import org.weso.wesearch.model.OntoModelWrapper;

public class DefaultContext implements Context {

	public final String uri = "http://www.weso.es/emptyOntology";
	
	public String getOntologyURI() {
		return uri;
	}

	@Override
	public OntoModelWrapper getOntologiesModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
