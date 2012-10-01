package org.weso.wesearch;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestWesearch {

	@Test
	public void testVersion() {
		Context ctx = new DefaultContext();
		JenaWesearch ws = new JenaWesearch(ctx);
		assertEquals(ws.version(),"0.1");
	}

	@Test
	public void testGetMatters() {
		Context ctx = new DefaultContext();
		JenaWesearch ws = new JenaWesearch(ctx);
		Matters subjects = ws.getMatters();
		Matter s = subjects.findMatter("Ministro");
		assertEquals(s.label(), "Ministro");
	}

}
