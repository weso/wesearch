package org.weso.wesearch.domain;

/**
 * It's an interface that represents a value selector. A value selector 
 * indicates the type that must have the value of a property of the ontology.
 * @author Ignacio Fuertes Bernardo
 *
 */
public interface ValueSelector {
	/**
	 * Indicates that a property must have a string value
	 */
	public static final String TEXT = "textfield";
	/**
	 * Indicates that a property must have a date value
	 */
	public static final String DATE = "date";
	/**
	 * Indicates that a property must have an interval date value
	 */
	public static final String DATE_INTERVAL = "date_interval";
	/**
	 * Indicates that a property must have a numeric value
	 */
	public static final String NUMERIC = "numeric";
	/**
	 * Indicates that a property must have an interval numeric value
	 */
	public static final String NUMERIC_INTERVAL = "numeric_interval";
	/**
	 * Indicates that a property must have an object value
	 */
	public static final String OBJECT = "object";
	/**
	 * Indicates that a property don't have defined range. By default, must
	 * have a string value
	 */
	public static final String UNDEFINED = "undefined";
	
	/**
	 * This method returns the type of the value selector. It's one of the types
	 * defined as a static parameters of the class
	 * @return The type of the valur selector
	 */
	public String getType();
	
	/**
	 * This method returns the value of this value selector
	 * @return The value of the value selector
	 */
	public Value<?> getValue();
	
	/**
	 * This method sets the value of the value selector from a given value
	 * @param value The new value
	 */
	public void setValue(Value<?> value);

}
