package org.weso.wesearch.domain.impl.filters;

public enum Operator {
	AND ("&&"), OR ("||");
	
	private String value;
	
	private Operator(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
