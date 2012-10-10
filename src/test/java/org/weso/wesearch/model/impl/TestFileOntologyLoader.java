package org.weso.wesearch.model.impl;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;

public class TestFileOntologyLoader {
	
	@Test
	public void testOpenInputStream() {
		String fileName = "src/test/resources/testFalse.txt";
		String[] fileNames = {"src/test/resources/test.txt"};
		FileOntologyLoader loader = new FileOntologyLoader(fileNames);
		try {
			InputStream stream = loader.openInputStream(fileNames[0]);
			assertNotNull(stream);
		} catch (FileNotFoundException e) {			
			assert(false);
		}
		try {
			InputStream stream = loader.openInputStream(fileName);
			assertNull(stream);
		} catch (FileNotFoundException e) {
			assert(true);
		}
	}

}
