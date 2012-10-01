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

	@Override
	public boolean equals(Matter m) {
		if(m == null) {
			throw new IllegalArgumentException("The paramenter can not be null");
		}
		return this.label.equals(m.label());
	}

}
