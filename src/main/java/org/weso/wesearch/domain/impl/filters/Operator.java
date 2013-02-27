package org.weso.wesearch.domain.impl.filters;

/**
 * It's an enumeration that defines the possible operators to use in a query
 * @author Ignacio Fuertes Bernardo
 *
 */
public enum Operator {

	AND ("&&"), OR ("||");
	
	/**
	 * The string value to use in a query
	 */
	private String value;
	
	/**
	 * It's the constructor
	 * @param value The string value of the operator
	 */
	private Operator(String value) {
		this.value = value;
	}
	
	/**
	 * This method returns  the string valur of the operator
	 * @return The string valur
	 */
	public String value() {
		return value;
	}
}
