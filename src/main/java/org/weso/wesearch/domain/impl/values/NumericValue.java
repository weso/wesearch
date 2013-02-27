package org.weso.wesearch.domain.impl.values;

import org.weso.wesearch.domain.Value;

/**
 * It's an implementation of the abstract class Value. Represents a numeric 
 * value of a property
 * @author Ignacio Fuertes Bernardo
 *
 */
public class NumericValue extends Value<Double> {

	/**
	 * Contains the numeric value of a property
	 */
	private Double value;
	
	/**
	 * It's a contructor of the class
	 * @param value The numeric value
	 */
	public NumericValue(Double value) {
		this.value = value;
	}
	
	@Override
	public Double getValue() {
		return value;
	}

}
