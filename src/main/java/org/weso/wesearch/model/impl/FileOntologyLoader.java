package org.weso.wesearch.model.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.weso.utils.OntoModelException;
import org.weso.wesearch.model.OntoLoader;

public class FileOntologyLoader implements OntoLoader {
	
	private static final Logger logger = Logger.getLogger(FileOntologyLoader.class);
	
	private String[] fileNames;
	private InputStream[] sourceData;
	
	public FileOntologyLoader(String [] fileNames) {
		this.fileNames = fileNames;
	}
	
	/*
	 * Open the input stream from a file
	 */
	protected InputStream openInputStream(String filename) throws FileNotFoundException {
		logger.debug("Opening resource input stream for filename: " + filename);
        InputStream in = null;
        try {
        	File file = new File(filename);
        	in = new FileInputStream(file);
        } catch(FileNotFoundException e) {
        	logger.error("Ontology file not found: " + filename);
        	throw e;
        }
        return in;
	}

	@Override
	public InputStream[] getOntologiesSourceData() throws OntoModelException {
		if(fileNames == null) {
			logger.error("There isn't any ontology specified by his path or URL");
			throw new OntoModelException("There isn't any ontology specified by his path or URL");
		}
		InputStream[] result = new InputStream[this.fileNames.length];
		for(int i = 0; i < result.length; i++) {
			try {
				result[i] = openInputStream(fileNames[i]);
			} catch (FileNotFoundException e) {
				throw new OntoModelException(e.getMessage());
			}
		}
		this.sourceData = result;
		return this.sourceData;
	}

	@Override
	public String[] getOntologiesAsName() {
		return this.fileNames;
	}

}
