package org.weso.wesearch.context;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.weso.utils.OntoModelException;
import org.weso.wesearch.context.impl.JenaContext;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

import com.hp.hpl.jena.ontology.OntModel;

public class TestJenaContext {
	
	@Test
	public void testGetOntologiesModel() throws OntoModelException {
		String[] files = {"src/test/resources/index.rdf"};
		OntoModelWrapper wrapper = new JenaOntoModelWrapper(new FileOntologyLoader(files));
		JenaContext context = new JenaContext(wrapper);
		try {
			assertTrue(context.getOntologiesModel().getModel() instanceof OntModel);
		} catch (OntoModelException e) {
			assert(false);
		}
	}

}
