package org.weso.wesearch.domain;

import org.junit.Test;
import org.weso.wesearch.domain.impl.PropertiesImpl;

public class TestPropertiesImpl {

	@Test(expected=IllegalArgumentException.class)
	public void testAddNullProperty() {
		Properties properties = new PropertiesImpl();
		properties.addProperty(null);
	}

}
