package org.weso.wesearch;

public class DefaultContext implements Context {

	public final String uri = "http://www.weso.es/emptyOntology";
	
	public String getOntologyURI() {
		return uri;
	}

}
