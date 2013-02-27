package org.weso.wesearch.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is an abstraction of a class of an ontology that contains all
 * necessary information about it
 * @author Ignacio Fuertes Bernardo
 *
 */
@XmlRootElement
public interface Matter {

	/**
	 * This method must return the value of the property rdfs:label of the 
	 * class
	 * @return The value of the property rdfs:label
	 */
	String getLabel();
	
	/**
	 * This method must return the URI of the class
	 * @return The URI of the class
	 */
	String getUri();
	
	/**
	 * This method must return the value of the property rdfs:comment of the 
	 * class
	 * @return The value of the property rdfs:comment
	 */
	String getDescription();
	
	/**
	 * This method indicates if the actual object is equal than the object that
	 * receive as a parameter
	 * @param m The matter that have to compare with itself
	 * @return True if both objects are equals
	 */
	boolean equals(Matter m);
}
