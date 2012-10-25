package org.weso.wesearch.domain.impl;

import org.weso.wesearch.domain.Property;

public class JenaPropertyImpl implements Property{
	
	private String uri;
	private String name;
	private String description;
	
	public JenaPropertyImpl(String uri, String name, String description) {
		this.uri = uri;
		this.name = name;
		this.description = description;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
