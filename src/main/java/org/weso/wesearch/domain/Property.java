package org.weso.wesearch.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * It's an interface that represents a property of the ontologies. One property
 * depends on a given subject 
 * @author Ignacio Fuertes Bernardo
 *
 */
@XmlRootElement
public interface Property {
	
	/**
	 * This method returns the value of the property rdfs:label of the property
	 * @return The value of the property rdfs:label
	 */
	String getLabel();
	
	/**
	 * This method returns the URI of the property
	 * @return The URI of the property
	 */
	String getUri();
	
	/**
	 * This method returns the value of the property rdfs:comment of the 
	 * property
	 * @return The value of the property rdfs:comment
	 */
	String getDescription();

}
