package org.weso.wesearch.context;

import static org.junit.Assert.assertNotNull;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.weso.wesearch.context.impl.JenaContext;

import com.hp.hpl.jena.ontology.OntModel;

public class TestJenaContext {
	
	@Test
	public void testGetOntologiesModel() {
		List<String> ontologies = new LinkedList<String>();
		JenaContext context = new JenaContext(ontologies.toArray(new String[ontologies.size()]));
		assertNotNull(context);
		assertNotNull(context.getOntologiesModel());
		assert(context.getOntologiesModel() instanceof OntModel);
	}

}
