package org.weso.wesearch;

import org.weso.utils.WesearchException;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.Query;
import org.weso.wesearch.domain.ValueSelector;

/**
 * This is the main interface of wesearch that defines the operations to 
 * implements.
 * @author Ignacio Fuertes Bernardo
 *
 */
public interface Wesearch {
	
	/**
	 * Return a collection of subjects for a given string
	 * @param stem Given string to obtain a collection of subjects
	 * @return A collection of subjects
	 * @throws WesearchException This exception is thrown if there are 
	 * some problem during the task
	 */
	Matters getMatters(String stem) throws WesearchException;
	
	/**
	 * Returns a collection of properties for a given subject and string
	 * @param s Given subject to obtains it's properties
	 * @param stem Given string to search properties
	 * @return A collection of properties according with the matter received as
	 * a parameter
	 * @throws WesearchException This exception is thrown if there are 
	 * some problem during the task
	 */
	Properties getProperties(Matter s, String stem) throws WesearchException;
	
	/**
	 * Returns the value selector for a given subject and property
	 * @param s Given subject
	 * @param p Given property to obtain it's value selector
	 * @return The value selector of the property
	 * @throws WesearchException This exception is thrown if there are 
	 * some problem during the task
	 */
	ValueSelector getValueSelector(Matter s, Property p) 
			throws WesearchException;
	
	/**
	 * Returns a query for a given subject, property and value selector
	 * @param s Given subject to form the query
	 * @param p Given property to form the query
	 * @param v Given value selector to form the query
	 * @return A query built from given subject, property and value selector
	 * @throws WesearchException This exception is thrown if there are 
	 * some problem during the task
	 */
	Query createQuery(Matter s, Property p, ValueSelector v) 
			throws WesearchException;
	
	/**
	 * Given a query, returns a new query for a given subject, property and 
	 * value
	 * @param q Given query to add new predicates
	 * @param s Given subject to add to the query
	 * @param p Given property to add to the query
	 * @param v Given value selector to add to the query
	 * @return The new query
	 * @throws WesearchException This exception is thrown if there are 
	 * some problem during the task
	 */
	Query combineQuery(Query q, Matter s, Property p, ValueSelector v) 
			throws WesearchException;

	/**
	 * Returns the version of the weserach
	 * @return The number of the version
	 */
	String version();

}
