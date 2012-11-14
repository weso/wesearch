package org.weso.wesearch.domain.impl.values;

import org.weso.wesearch.domain.Value;

public class StringValue extends Value<String> {
	
	private String value;
	
	public StringValue(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {		
		return value;
	}

}
