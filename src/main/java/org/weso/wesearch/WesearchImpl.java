package org.weso.wesearch;

import org.weso.utils.NotImplementedException;

public class WesearchImpl implements Wesearch {

	@Override
	public void initialize(Context ctx) {
		throw new NotImplementedException("initialize");
	}

	@Override
	public Subjects getSubjects() {
		throw new NotImplementedException("getSubjects");
	}

	@Override
	public Properties getProperties(Subject s) {
		throw new NotImplementedException("getProperties");
	}

	@Override
	public ValueSelector getValueSelector(Subject s, Property p) {
		throw new NotImplementedException("getValueSelector");
	}

	@Override
	public Query createQuery(Subject s, Property p, ValueSelector v) {
		throw new NotImplementedException("createQuery");
	}

	@Override
	public Query combineQuery(Query q, Subject s, Property p, ValueSelector v) {
		throw new NotImplementedException("combineQuery");
	}

	@Override
	public String version() {
		return "0.1";
	}

}
