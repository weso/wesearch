package org.weso.wesearch.model;

import java.io.InputStream;

import org.weso.utils.OntoModelException;

public interface OntoLoader {
	
	/*
	 * Obtains all input stream from the loader
	 */
	InputStream[] getOntologiesSourceData() throws OntoModelException;
	
	/*
	 * Obtains all names of ontologies
	 */
	String[] getOntologiesAsName();

}
