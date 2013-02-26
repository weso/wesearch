package org.weso.wesearch.model;

import java.io.InputStream;

import org.weso.utils.OntoModelException;

/**
 * This is an interface that defines the operations that must have an ontology 
 * loader to be used by wesearch to load ontologies
 * @author Ignacio Fuertes Bernardo
 *
 */
public interface OntoLoader {
	
	/**
	 * This method obtains all input streams from the loader
	 * @return An array of input streams of all ontologies that the loader must 
	 * load
	 * @throws OntoModelException This exception is thrown if there are some 
	 * problems opening the streams. 
	 */
	InputStream[] getOntologiesSourceData() throws OntoModelException;
	
	/**
	 * This method obtains the full name (path) of all ontologies that the 
	 * loader must load.
	 * @return An array of strings containing the full names of all ontologies.
	 */
	String[] getOntologiesAsName();

}
