package org.weso.wesearch.domain;

import org.weso.utils.WesearchException;


/**
 * It's an interface that represents a contanier for properties. 
 * @author Ignacio Fuertes Bernardo
 *
 */
public interface Properties extends Iterable<Property>{
	
	/**
	 * This method returns a property from its name.
	 * @param propertyName The name of the property that the method has to 
	 * return
	 * @return The property sought
	 * @throws WesearchException This exception is thrown if there isn't 
	 * any property with this name
	 */
	public Property getPropertyByName(String propertyName) throws WesearchException;
	
	/**
	 * This method has to add new property to the container
	 * @param prop The new property to add
	 */
	public void addProperty(Property prop);
	

}
