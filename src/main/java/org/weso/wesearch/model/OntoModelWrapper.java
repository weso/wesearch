package org.weso.wesearch.model;

import org.weso.utils.OntoModelException;

/**
 * Is an interface that defines the operations that an ontology wrapper must 
 * have to be used by wesearch.
 * @author Ignacio Fuertes Bernardo	
 *
 */
public interface OntoModelWrapper {
	
	/**
	 * This method has to return the original model.
	 * @return The original model that contains the information that wesearch 
	 * need to work
	 * @throws OntoModelException This exception is thrown if there is some 
	 * problem returning the model.
	 */
	Object getModel() throws OntoModelException;
	
	/**
	 * This method has to return the ontology loader
	 * @return The ontology loader of wesearch
	 */
	OntoLoader getLoader();

}
