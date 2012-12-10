package org.weso.wesearch.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface Matter {

	String getLabel();
	
	String getUri();
	
	String getDescription();
	
	boolean equals(Matter m);
}
