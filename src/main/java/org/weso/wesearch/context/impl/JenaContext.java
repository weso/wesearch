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

public class JenaContext implements Context {
	
	private static final Logger logger = Logger.getLogger(JenaContext.class);
	
	private OntoModelWrapper modelWrapper;
	
	public JenaContext(OntoModelWrapper modelWrapper) throws OntoModelException {
		this.modelWrapper = modelWrapper;
		saveModel();
	}

	@Override
	public OntoModelWrapper getOntologiesModel() {
		return modelWrapper;
	}
	
	
	private void saveModel() throws OntoModelException {		
		OntModel model = (OntModel)modelWrapper.getModel();	
		String datasource = Configuration.getProperty("datasource_uri");
		if(datasource.equals("virtual")) {
			JenaModelFileWrapper.getInstance().loadModelFromModel(model);
			logger.info("Pass an instance of the model");
		} else {
			try {		
				JenaModelFileWrapper.getInstance().loadModelFromFile(new File(datasource));		
			} catch (FileNotFoundException e) {		
				logger.error("Cannot save model in a local file: " + e.getMessage());
				logger.info("Pass an instance of the model");
				JenaModelFileWrapper.getInstance().loadModelFromModel(model);
			}
		}				
	}
	

}
