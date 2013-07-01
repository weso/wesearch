package org.weso.wesearch.domain.impl;

import javax.xml.bind.annotation.XmlRootElement;

import org.weso.wesearch.domain.Property;

/**
 * It's an implementation of the interface Property.
 * @author Ignacio Fuertes Bernardo
 *
 */
@XmlRootElement
public class JenaPropertyImpl implements Property{
	
	/**
	 * This variable represents the URI of the property
	 */
	private String uri;
	/**
	 * This variable represents the value of the property rdfs:label
	 */
	private String label;
	/**
	 * This variable represents the value of the property rdfs:comment
	 */
	private String description;
	
	/**
	 * It's the constructor of the property that receives the values that must
	 * have
	 * @param uri The uri of the property
	 * @param name The value of the property rdfs:label
	 * @param description The value of the property rdfs:comment
	 */
	public JenaPropertyImpl(String uri, String name, String description) {
		this.uri = uri;
		this.label = name;
		this.description = description;
	}
	
	/**
	 * It's a constructor of the class. This constructor don't receive 
	 * parameters and initialize the properties with empty strings
	 */
	public JenaPropertyImpl(){
		this.uri = "";
		this.label = "";
		this.description = "";
	}

	@Override
	public String getUri() {
		return uri;
	}

	/**
	 * This method sets a new value for the URI of the property
	 * @param uri The new URI for the property
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * This method sets a new value for the rdfs:property only for this 
	 * representation of the property, not in the ontology
	 * @param name The new value of rdfs:label
	 */
	public void setLabel(String name) {
		this.label = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * This method sets a new valur for the property rdfs:comment only for this
	 * representation of the property and not in the ontology
	 * @param description The new value of rdfs:comment
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * This method generates an unique number for a property
	 * @return An unique identifier of the property
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	/**
	 * This method returns if this object is equals than the object that receive
	 * as a parameter
	 * @return A boolean indicates if both objects are the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JenaPropertyImpl other = (JenaPropertyImpl) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
			else 
				throw new IllegalArgumentException("Both uris are null and " +
						"cannot determinate if they are equals");
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

		

}
