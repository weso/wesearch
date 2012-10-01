package org.weso.wesearch;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestWesearch {

	@Test
	public void test() {
		WesearchImpl ws = new WesearchImpl();
		assertEquals(ws.version(),"0.1");
	}

}
