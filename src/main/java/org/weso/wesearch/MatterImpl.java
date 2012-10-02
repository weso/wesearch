package org.weso.wesearch;

import org.apache.log4j.Logger;

public class MatterImpl implements Matter {
	
	private static Logger logger = Logger.getLogger(MatterImpl.class);
	
	private String label;
	private String uri;
	
	public MatterImpl(String label, String uri) {
		this.label = label;
		this.uri = uri;
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

}
