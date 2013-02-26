package org.weso.wesearch.model.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.weso.utils.OntoModelException;
import org.weso.wesearch.model.OntoLoader;
import org.weso.wesearch.model.OntoModelWrapper;

import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.OWLMicroReasoner;
import com.hp.hpl.jena.reasoner.rulesys.OWLMicroReasonerFactory;
import com.hp.hpl.jena.shared.JenaException;

/**
 * This class implements the interface OntoModelWrapper and represents a 
 * wrapper for Jena ontology models. This class allows wesearch works with
 * Jena models.
 * @author Ignacio Fuertes Bernardo
 *
 */
public class JenaOntoModelWrapper implements OntoModelWrapper{
	
	private static Logger logger = Logger.getLogger(JenaOntoModelWrapper.class);
	
	/**
	 * This is the loader used by the wrapper to load the ontologies in the 
	 * model
	 */
	private OntoLoader loader;
	/**
	 * This is the model in which save all information about the ontologies
	 * that the loader load 
	 */
	private OntModel jenaModel;
	
	/**
	 * Constructor of the class that receives the loader
	 * @param loader The loader that allows the wrapper load all information
	 * about the ontologies
	 */
	public JenaOntoModelWrapper(OntoLoader loader) {
		this.loader = loader;
	}

	@Override
	public Object getModel() throws OntoModelException {
		if(jenaModel == null) {
			return jenaModel = createJenaModel();
		}
		logger.debug("Loaded Jena Model");
		return jenaModel;
	}
	
	/**
	 * This method creates the model and load all information from the loader
	 * @return The jena model that contains all information about the ontologies
	 * @throws OntoModelException This exception is thrown if there is a problem
	 * creating the model
	 */
	private OntModel createJenaModel() throws OntoModelException {
		InputStream[] streams = loader.getOntologiesSourceData();
		Reasoner reasoner = new OWLMicroReasoner(
				OWLMicroReasonerFactory.theInstance());
		OntDocumentManager dm = OntDocumentManager.getInstance();
		dm.setProcessImports(false);		
		OntModelSpec spec = new OntModelSpec( OntModelSpec.OWL_DL_MEM);
        spec.setDocumentManager(dm);
        spec.setReasoner(reasoner);
		OntModel ontModel = ModelFactory.createOntologyModel(spec, null);
		logger.debug("Loading " + streams.length + " ontologies");
		
		for(int i = 0; i < streams.length; i++) {
			try {
				InputStream in = streams[i];
				ontModel.read(in, "");			
				in.close();
			} catch (IOException e) {
				logger.error("Error closing an ontology model stream");
				throw new OntoModelException(e.getMessage());
			} catch (JenaException e) {
				logger.error("Error reading an ontology");
				throw new OntoModelException(e.getMessage());
			}
		}
		logger.debug("Loaded all ontologies");
		return ontModel;
	}
	
	@Override
	public OntoLoader getLoader() {
		return loader;
	}

}
