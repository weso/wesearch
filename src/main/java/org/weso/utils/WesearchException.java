package org.weso.utils;

/**
 * This class represents all exceptions that are thrown by wesearch to client
 * applications
 * @author Ignacio Fuertes Bernardo
 *
 */
public class WesearchException extends Exception {
	
	private static final long serialVersionUID = 8621629881784122856L;

	/**
	 * The constructor of the exception
	 * @param msg The message of the exception
	 */
	public WesearchException(String msg) {
		super(msg);
	}
}
