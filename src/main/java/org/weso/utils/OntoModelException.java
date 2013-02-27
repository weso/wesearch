package org.weso.utils;

/**
 * This class defines exceptions that are thrown if there are problems while 
 * working with ontologies
 * @author Ignacio Fuertes Bernardo
 *
 */
public class OntoModelException extends Exception {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7563862346049983181L;

	/**
	 * Constructor of the exception
	 * @param msg The message of the exception
	 */
	public OntoModelException(String msg) {
		super(msg);
	}

}
