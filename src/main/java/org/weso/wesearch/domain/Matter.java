package org.weso.wesearch.domain;

public interface Matter {

	String label();
	
	String uri();
	
	String description();
	
	boolean equals(Matter m);
}
