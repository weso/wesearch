package org.weso.wesearch;

import static org.junit.Assert.*;

import org.junit.Test;
import org.weso.utils.WesearchException;

public class TestWesearch {

	@Test
	public void testVersion() {
		WesearchImpl ws = new WesearchImpl();
		assertEquals(ws.version(),"0.1");
	}

	@Test
	public void testVersionFailed() {
		WesearchImpl ws = new WesearchImpl();
		Matters subjects = ws.getSubjects();
		Matter s = null;
		try {
			s = subjects.findMatter("Ministro");
			assertEquals(s.label(), "Ministro");
		} catch (WesearchException e) {
			assertNotNull(s);
		}
	}

}
