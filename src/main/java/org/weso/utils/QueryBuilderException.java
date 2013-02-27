package org.weso.utils;

/**
 * This class represents exceptions that are thrown if there are problems while
 * building queries
 * @author Ignacio Fuertes Bernardo
 *
 */
public class QueryBuilderException extends Exception {
	
	
	private static final long serialVersionUID = -5974559436998413816L;

	/**
	 * Constructor of the exception
	 * @param msg The message of the exception
	 */
	public QueryBuilderException(String msg) {
		super(msg);
	}

}
