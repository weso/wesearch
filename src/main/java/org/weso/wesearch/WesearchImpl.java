package org.weso.wesearch;

public class WesearchImpl implements Wesearch {

	@Override
	public Subjects getSubjects() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	@Override
	public Properties getProperties(Subject s) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	@Override
	public ValueSelector getValueSelector(Subject s, Property p) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	@Override
	public Query createQuery(Subject s, Property p, ValueSelector v) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	@Override
	public String version() {
		return "0.1";
	}

}
