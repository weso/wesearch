package org.weso.wesearch.context.impl;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.weso.utils.OntoModelException;
import org.weso.wesearch.context.Context;
import org.weso.wesearch.model.OntoModelWrapper;

import weso.mediator.config.Configuration;
import weso.mediator.core.persistence.jena.JenaModelFileWrapper;

import com.hp.hpl.jena.ontology.OntModel;

/**
 * It's an implementation of Context based onf Apache Jena
 * @author Ignacio Fuertes Bernardo
 *
 */
public class JenaContext implements Context {
	
	private static final Logger logger = Logger.getLogger(JenaContext.class);
	
	/**
	 * An instance of the wrapper that contains the model wiht ontologies
	 */
	private OntoModelWrapper modelWrapper;
	
	/**
	 * It's the contructos of the class
	 * @param modelWrapper The object that contains the model with ontologies
	 * @throws OntoModelException This exception is thrown if there is a problem
	 * saving the model.
	 */
	public JenaContext(OntoModelWrapper modelWrapper) 
			throws OntoModelException {
		this.modelWrapper = modelWrapper;
		saveModel();
	}

	@Override
	public OntoModelWrapper getOntologiesModel() {
		return modelWrapper;
	}
	
	/**
	 * This method has to pass the model to wesomed through a "logical" file
	 * using JenaModelFileWrapper (class of wesomed that allows it to load 
	 * entities from a Jena model) or a physical file in the file system.
	 * @throws OntoModelException This exception is thrown if ther is a problem
	 * saving the model in a file.
	 */
	private void saveModel() throws OntoModelException {		
		OntModel model = (OntModel)modelWrapper.getModel();	
		String datasource = Configuration.getProperty("datasource_uri");
		if(datasource.equals("virtual")) {
			passModelToWesomed(model);
		} else {
			try {		
				JenaModelFileWrapper.getInstance().loadModelFromFile(
						new File(datasource));		
			} catch (FileNotFoundException e) {		
				logger.error("Cannot save model in a local file: " + 
			e.getMessage());
				passModelToWesomed(model);
			}
		}
	}

	/**
	 * This method has to pass the model that contains the ontologies to wesomed
	 * using the class JenaModelFileWrapper supplied by wesomed
	 * @param model The model that the method has to send to wesomed
	 */
	private void passModelToWesomed(OntModel model) {
		if(model == null) 
			throw new IllegalArgumentException("Model cannot be null");
		JenaModelFileWrapper.getInstance().loadModelFromModel(model);
		logger.info("Pass an instance of the model");
	}
	

}
