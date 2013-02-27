package org.weso.wesearch.domain.impl;

import org.weso.wesearch.domain.Value;
import org.weso.wesearch.domain.ValueSelector;

/**
 * It's an implementation of the interface ValueSelector
 * @author Ignacio Fuertes Bernardo
 *
 */
public class ValueSelectorImpl implements ValueSelector {
	
	/**
	 * This variable indicates the type of the value selector
	 */
	private String type;
	/**
	 * This variable represents the object that contains the value of a property
	 */
	private Value<?> value;
	
	/**
	 * The constructor of the class, that receive the type of the value selector
	 * @param type The type of the value selector
	 */
	public ValueSelectorImpl(String type) {
		this.type = type;
	}
	

	@Override
	public String getType() {
		
		return type;
	}
	
	@Override
	public Value<?> getValue() {
		return value;
	}
	
	@Override
	public void setValue(Value<?> value) {
		this.value = value;
	}

}
