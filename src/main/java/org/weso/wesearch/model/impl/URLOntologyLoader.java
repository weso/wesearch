package org.weso.wesearch.model.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class extends the class FileOntologyHelper and allows to load ontologies
 * from URLs
 * @author Ignacio Fuertes Bernardo
 *
 */
public class URLOntologyLoader extends FileOntologyLoader {
	
	private static final Logger logger = Logger.getLogger(URLOntologyLoader.class);

	/**
	 * The constructor that receives an array that contains all full names of
	 * the ontologies to load.
	 * @param fileNames An array of string that contains the URLs of the 
	 * ontologies
	 */
	public URLOntologyLoader(String[] fileNames) {
		super(fileNames);
	}
	
	/**
	 * The constructor that receives a list that contains all full names of the
	 * ontologies to load.
	 * @param fileNames A list of string that contains the URLs of the 
	 * ontologies
	 */
	public URLOntologyLoader(List<String> fileNames) {
		super(fileNames);
	}
	
	@Override
	protected InputStream openInputStream(String filename) throws FileNotFoundException {
		logger.debug("Opening resource input stream for filename: " + filename);
        InputStream in = null;
        try {
			URL url = new URL(filename);
			in = url.openConnection().getInputStream();
		} catch (FileNotFoundException e) {
			logger.error("Ontology file not found: " + filename);
			throw e;
		} catch (IOException e) {
			logger.error("Ontology file can not be readed " + filename);
			throw new FileNotFoundException("Ontology can not be readed " + filename);
		}
        if (in == null) {
            logger.error("Ontology file not found: " + filename);
            throw new FileNotFoundException(filename);
        } else {
            return in;
        }
	}

}
