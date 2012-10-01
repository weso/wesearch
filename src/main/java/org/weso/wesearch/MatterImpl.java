package org.weso.wesearch;

public class MatterImpl implements Matter {
	
	private String label;
	
	public MatterImpl(String label) {
		this.label = label;
	}

	@Override
	public String label() {
		return label;
	}

}
