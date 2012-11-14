package org.weso.wesearch.domain.impl;

import org.weso.wesearch.domain.Value;
import org.weso.wesearch.domain.ValueSelector;

public class ValueSelectorImpl implements ValueSelector {
	
	private String type;
	private Value<?> value;
	
	public ValueSelectorImpl(String type) {
		this.type = type;
	}
	

	@Override
	public String getType() {
		
		return type;
	}
	
	public Value<?> getValue() {
		return value;
	}
	
	public void setValue(Value<?> value) {
		this.value = value;
	}

}
