package org.weso.wesearch.context;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.weso.utils.OntoModelException;
import org.weso.wesearch.context.impl.JenaContext;

import com.hp.hpl.jena.ontology.OntModel;

public class TestJenaContext {
	
	@Test
	public void testGetOntologiesModel() {
		List<String> ontologies = new LinkedList<String>();
		JenaContext context = new JenaContext(ontologies.toArray(new String[ontologies.size()]));
		try {
			assertTrue(context.getOntologiesModel().getModel() instanceof OntModel);
		} catch (OntoModelException e) {
			assert(false);
		}
	}

}
