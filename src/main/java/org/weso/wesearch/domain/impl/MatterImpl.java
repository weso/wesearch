package org.weso.wesearch.domain.impl;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.weso.wesearch.domain.Matter;

@XmlRootElement
public class MatterImpl implements Matter {
	
	private static Logger logger = Logger.getLogger(MatterImpl.class);
	
	private String label;
	private String uri;
	private String description;
	
	public MatterImpl(String label, String uri, String description) {
		this.label = label;
		this.uri = uri;
		this.description = description;
	}
	
	public MatterImpl() {
		this.label = "";
		this.uri = "";
		this.description = "";
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

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
			throw new IllegalArgumentException("The paramenter can not be null");
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
