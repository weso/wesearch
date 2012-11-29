package org.weso.wesearch.domain;

public interface ValueSelector {
	
	public static final String TEXT = "textfield";
	public static final String DATE = "date";
	public static final String DATE_INTERVAL = "date_interval";
	public static final String NUMERIC = "numeric";
	public static final String NUMERIC_INTERVAL = "numeric_interval";
	public static final String OBJECT = "object";
	public static final String UNDEFINED = "undefined";
	
	/*
	 * Return type of the value selector
	 */
	public String getType();
	
	/*
	 * Return the value specifies by the user
	 */
	public Value<?> getValue();
	
	/*
	 * Set the value
	 */
	public void setValue(Value<?> value);

}
