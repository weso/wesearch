package org.weso.wesearch.domain.impl.values;

import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Value;

public class ObjectValue extends Value<Matters> {

	private Matters value;
	
	public ObjectValue(Matters m) {
		this.value = m;
	}
	
	@Override
	public Matters getValue() {
		return value;
	}

}
