package org.weso.wesearch.domain;

public interface Matter {

	String label();
	
	String uri();
	
	boolean equals(Matter m);
}
