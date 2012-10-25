package org.weso.wesearch.domain;

import org.weso.utils.WesearchException;


/*
 * Container of properties
 * A property depends on a given subject
 */
public interface Properties extends Iterable<Property>{
	
	/*
	 * Obtain a property from name
	 */
	public Property getPropertyByName(String propertyName) throws WesearchException;
	
	/*
	 * Add new Property
	 */
	public void addProperty(Property prop);
	

}
