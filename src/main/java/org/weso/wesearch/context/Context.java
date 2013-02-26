package org.weso.wesearch.context;

import org.weso.wesearch.model.OntoModelWrapper;

/**
 * It's an interface that defines the basic operations that must have the 
 * context of wesearch.
 * @author Ignacio Fuertes Bernardo
 *
 */
public interface Context {
	
	/**
	 * This method has to return the wrapper of the model that contains the
	 * information of the ontologies.
	 * @return The wapper model object.
	 */
	OntoModelWrapper getOntologiesModel();
}
