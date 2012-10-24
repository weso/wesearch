package org.weso.wesearch.model.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

public class URLOntologyLoader extends FileOntologyLoader {
	
	private static final Logger logger = Logger.getLogger(URLOntologyLoader.class);

	public URLOntologyLoader(String[] fileNames) {
		super(fileNames);
	}
	
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
