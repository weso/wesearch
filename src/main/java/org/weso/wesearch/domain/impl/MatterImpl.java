package org.weso.wesearch.domain.impl;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.weso.wesearch.domain.Matter;

/**
 * It's an implementation of the interface Matter
 * @author Ignacio Fuertes Bernardo
 *
 */
@XmlRootElement
public class MatterImpl implements Matter {
	
	private static Logger logger = Logger.getLogger(MatterImpl.class);
	
	/**
	 * This property represents the value of the property rdfs:label of the 
	 * class that represents the object
	 */
	private String label;
	/**
	 * This property represents the URI of the class that the object represents
	 */
	private String uri;
	/**
	 * This property represents the valur of the property rdfs:comment of the 
	 * class that the object represents
	 */
	private String description;
	
	/**
	 * It's a constructor of the class that receive the values that must have 
	 * its properties
	 * @param label The value of the rdfs:label property
	 * @param uri The URI of the class that represents
	 * @param description The value of the rdfs:comment property 
	 */
	public MatterImpl(String label, String uri, String description) {
		this.label = label;
		this.uri = uri;
		this.description = description;
	}
	
	/**
	 * It's a constructor of the class. Don't receive any parameter and 
	 * initialize the variables with empty strings
	 */
	public MatterImpl() {
		this.label = "";
		this.uri = "";
		this.description = "";
	}

	/**
	 * This method sets new value for the rdfs:label property only for this 
	 * representation of the class and not for the ontology
	 * @param label The new valur of the rdfs:label property
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * This method sets new value for the URI of the class only for this 
	 * representation of the class and not for the ontology
	 * @param uri The new value of the URI
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * This method sets new value for the property rdfs:comment only for this
	 * representation of the class and not for the ontology
	 * @param description The new value of the rdfs:comment property
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public boolean equals(Matter m) {
		if(m == null) {
			logger.error("The paramenter can not be null");
			throw new IllegalArgumentException("The paramenter can not " +
					"be null");
		}
		return this.label.equals(m.getLabel());
	}
	
	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public String toString() {
		return uri + " " + label + " " + description;
	}

}
