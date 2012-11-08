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

public class JenaOntoModelWrapper implements OntoModelWrapper{
	
	private static Logger logger = Logger.getLogger(JenaOntoModelWrapper.class);
	
	private OntoLoader loader;
	private OntModel jenaModel;
	
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
	
	/*
	 * Create Jena Ontology Model from the streams
	 */
	private OntModel createJenaModel() throws OntoModelException {
		InputStream[] streams = loader.getOntologiesSourceData();
		Reasoner reasoner = new OWLMicroReasoner(OWLMicroReasonerFactory.theInstance());
		OntDocumentManager dm = OntDocumentManager.getInstance();
		dm.setProcessImports(false);		
		OntModelSpec spec = new OntModelSpec( OntModelSpec.OWL_DL_MEM);
        spec.setDocumentManager(dm);
        spec.setReasoner(reasoner);
		OntModel ontModel = ModelFactory.createOntologyModel(spec, null);
		logger.debug("Loading " + streams.length + " ontologies");
		
		for(int i = 0; i < streams.length; i++) {
			InputStream in = streams[i];
			ontModel.read(in, "");			
			try {
				in.close();
			} catch (IOException e) {
				logger.error("Error closing an ontology model stream");
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
