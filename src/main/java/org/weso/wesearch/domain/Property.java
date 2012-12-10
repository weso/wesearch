package org.weso.wesearch.domain;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * Represents a property
 * For example: birthPlace
 */
@XmlRootElement
public interface Property {
	
	String getName();
	
	String getUri();
	
	String getDescription();

}
