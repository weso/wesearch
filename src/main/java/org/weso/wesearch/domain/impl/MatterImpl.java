package org.weso.wesearch.domain.impl;

import org.apache.log4j.Logger;
import org.weso.wesearch.domain.Matter;

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

	@Override
	public String label() {
		return label;
	}

	@Override
	public boolean equals(Matter m) {
		if(m == null) {
			logger.error("The paramenter can not be null");
			throw new IllegalArgumentException("The paramenter can not be null");
		}
		return this.label.equals(m.label());
	}
	
	@Override
	public String uri() {
		return uri;
	}

	@Override
	public String description() {
		return this.description;
	}

}
