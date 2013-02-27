package org.weso.wesearch.domain.impl.values;

import java.util.Date;

import org.weso.wesearch.domain.Value;

/**
 * It's an implementation of the abstract class Value. This class represents
 * a date of value of a property
 * @author Ignacio Fuertes Bernardo
 *
 */
public class DateValue extends Value<Date> {

	/**
	 * The date value of a property
	 */
	private Date value;
	
	/**
	 * The constructor of the class
	 * @param value The date value
	 */
	public DateValue(Date value) {
		this.value = value;
	}
	
	@Override
	public Date getValue() {
		return value;
	}

}
