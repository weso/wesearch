package org.weso.wesearch.factories;

import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.model.OntoModelWrapper;

/**
 * This is an abstract class that defines a set of operations to instantiate 
 * wesearch by client applications. 
 * @author Ignacio Fuertes Bernardo
 *
 */
public abstract class WesearchFactory {
	
	/**
	 * This method create an instance of wesearch depending.
	 * @param modelWrapper The wrapper of the model that contains all 
	 * information about the ontologies.
	 * @return An instance of wesearch.
	 * @throws WesearchException This exception is thrown if there is a problem
	 * creating an instance
	 * @throws OntoModelException This exception is thrown if there is a problem
	 * loading the ontologies.
	 */
	public abstract Wesearch createWesearch(OntoModelWrapper modelWrapper) 
			throws WesearchException, OntoModelException;

}
