package org.weso.wesearch.domain.impl;

import org.weso.wesearch.domain.ValueSelector;

public class ValueSelectorImpl implements ValueSelector {
	
	private String type;
	private String value;
	
	public ValueSelectorImpl(String type) {
		this.type = type;
	}
	

	@Override
	public String getType() {
		
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}
