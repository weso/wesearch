package org.weso.wesearch.model.impl;

import static org.junit.Assert.*;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.weso.utils.OntoModelException;

public class TestFileOntologyLoader {
	
	private String[] fileNames = {"src/test/resources/test.txt", "src/test/resources/test2.txt"};
	
	@Test(expected=FileNotFoundException.class)
	public void testOpenInputStream() throws FileNotFoundException {
		String fileName = "src/test/resources/testFalse.txt";
		FileOntologyLoader loader = new FileOntologyLoader(fileNames);
		try {
			InputStream stream = loader.openInputStream(fileNames[0]);
			assertNotNull(stream);
		} catch (FileNotFoundException e) {			
			assert(false);
		}
		loader.openInputStream(fileName);
	}
	
	@Test
	public void testGetOntologiesSourceData() throws OntoModelException, IOException {
		FileOntologyLoader loader = new FileOntologyLoader(fileNames);
		InputStream [] streams = loader.getOntologiesSourceData();
		assertTrue(streams.length == 2);
	}
	
	@Test
	public void testGetOntologiesAsNames() {
		String []expected = {"src/test/resources/test.txt", "src/test/resources/test2.txt"};
		FileOntologyLoader loader = new FileOntologyLoader(fileNames);
		String[] result = loader.getOntologiesAsName();
		for(int i = 0; i < result.length; i++) {
			assertEquals(expected[i], result[i]);
		}
	}
	
	@Test
	public void testGetOntologiesAsNamesWitoutFileNames() {
		String[] filenames = null;
		FileOntologyLoader loader = new FileOntologyLoader(filenames);
		assertNull(loader.getOntologiesAsName());
	}
	
	@Test(expected=OntoModelException.class)
	public void testGetOntologiesSourceDataWithoutFileNames() throws OntoModelException {
		String[] filenames = null;
		FileOntologyLoader loader = new FileOntologyLoader(filenames);
		loader.getOntologiesSourceData();
	}

}
