package org.weso.wesearch.domain.impl.values;

import java.util.Date;

import org.weso.wesearch.domain.Value;

public class DateValue extends Value<Date> {

	private Date value;
	
	public DateValue(Date value) {
		this.value = value;
	}
	
	@Override
	public Date getValue() {
		return value;
	}

}
