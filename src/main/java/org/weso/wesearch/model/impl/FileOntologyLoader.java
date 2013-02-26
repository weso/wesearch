package org.weso.wesearch.model.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.weso.utils.OntoModelException;
import org.weso.wesearch.model.OntoLoader;

/**
 * This class implements the interface OntoLoader and allows to wesearch
 * load ontologies from files
 * @author Ignacio Fuertes Bernardo
 *
 */
public class FileOntologyLoader implements OntoLoader {
	
	private static final Logger logger = Logger.getLogger(
			FileOntologyLoader.class);
	
	/**
	 * An array of string that contains the full name (path) of all ontologies 
	 * that have to load. 
	 */
	private String[] fileNames;
	/**
	 * An array that contains the input streams of all ontologies that have to
	 * load.
	 */
	private InputStream[] sourceData;
	
	/**
	 * The constructor of the class that receives an array of string with 
	 * full names (path) of ontolgies
	 * @param fileNames The array that contains all full names (path)of the 
	 * ontologies to load
	 */
	public FileOntologyLoader(String [] fileNames) {
		this.fileNames = fileNames;
	}
	
	/**
	 * The constructor of the class that receives a list that contain 
	 * the full name (path) of the ontologies to load.
	 * @param fileNames The list that contain the names of all ontologies that
	 * the loader must load
	 */
	public FileOntologyLoader(List<String> fileNames) {
		this.fileNames = fileNames.toArray(new String[fileNames.size()]);
	}
	
	/**
	 * This method has to open a input stream from a filename
	 * @param filename The name of the file that have to obtain its stream.
	 * @return An input stream of the file
	 * @throws FileNotFoundException This exception is thrown if there is a 
	 * problem opening the stream.
	 */
	protected InputStream openInputStream(String filename) 
			throws FileNotFoundException {
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
			logger.error("There isn't any ontology specified by his path " +
					"or URL");
			throw new OntoModelException("There isn't any ontology specified " +
					"by his path or URL");
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
