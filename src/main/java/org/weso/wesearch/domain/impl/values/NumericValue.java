package org.weso.wesearch.domain.impl.values;

import org.weso.wesearch.domain.Value;

public class NumericValue extends Value<Double> {

	private Double value;
	
	public NumericValue(Double value) {
		this.value = value;
	}
	
	@Override
	public Double getValue() {
		return value;
	}

}
