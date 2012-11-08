package org.weso.wesearch.domain;

public interface Matter {

	String getLabel();
	
	String getUri();
	
	String getDescription();
	
	boolean equals(Matter m);
}
