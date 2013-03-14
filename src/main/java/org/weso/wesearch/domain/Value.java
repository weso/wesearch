package org.weso.wesearch.domain;

/**
 * This is an abstract class that represents the value that a variable can have
 * in a query. 
 * @author Ignacio Fuertes Bernardo
 *
 * @param <T> It's a template that indicates the type of the value
 */
public abstract class Value<T> {
	
	/**
	 * This method returns the value that represents the object
	 * @return The value that contains the object
	 */
	public abstract T getValue();

}
