package org.weso.wesearch.domain;

import org.weso.utils.WesearchException;

/**
 * This class defines a container of Matter objects
 * @author Ignacio Fuertes Bernardo
 *
 */
public interface Matters extends Iterable<Matter> {
  
	/**
	 * This method return the matter that have the label equals than the method
	 * receive as a parameter
	 * @param label The label to find a concrete Matter
	 * @return The matter that has the label
	 * @throws WesearchException This exception is thrown if there isn't a 
	 * Matter that have the label
	 */
	Matter findMatter(String label) throws WesearchException;
	
	/**
	 * This method adds a new Matter to the container
	 * @param m The new Matter to add
	 */
	void addMatter(Matter m);
	
	/**
	 * This method returns the number of elements that have the container
	 * @return The number of elements
	 */
	int size();
}
