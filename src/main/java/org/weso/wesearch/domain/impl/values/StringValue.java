package org.weso.wesearch.domain.impl.values;

import org.weso.wesearch.domain.Value;

/**
 * It's an implementation of the abstract class Value. Represents a text value
 * of a sparql property
 * @author Ignacio Fuertes Bernardo
 *
 */
public class StringValue extends Value<String> {
	
	/**
	 * This variable contains the text value of a sparql property
	 */
	private String value;
	
	/**
	 * It's the constructor of the class
	 * @param value The text value
	 */
	public StringValue(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {		
		return value;
	}

}
