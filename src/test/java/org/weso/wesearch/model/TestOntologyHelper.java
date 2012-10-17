package org.weso.wesearch.model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.impl.MatterImpl;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class TestOntologyHelper {
	
	private OntClass ontClass = null; 
	private OntClass classWithoutLabel = null;
	private OntClass anonymousClass = null;
	
	@Before
	public void configure() {
		OntModel ont = ModelFactory.createOntologyModel();
		try {
			ont.read(new FileInputStream(new File("src/test/resources/ontoTest1.owl")), "");
			ontClass = ont.getOntClass("http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary");
			classWithoutLabel = ont.getOntClass("http://datos.bcn.cl/ontologies/bcn-biographies#ParliamentaryTest");
			anonymousClass = ont.createClass();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateMatterEquals() {
		Matter expected = new MatterImpl("Parlamentario", "http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary",
				"Una persona que es parlamentario.");
		Matter actual = OntologyHelper.createMatter(ontClass);
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testCreateMatterNotEquals() {
		Matter unexpected = new MatterImpl("Parlamentaria", "http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary",
				"Una persona que es parlamentario.");
		Matter actual = OntologyHelper.createMatter(ontClass);
		assertTrue(!unexpected.equals(actual));
	}

	@Test
	public void testGetLabelEquals() {
		String expected = "Parlamentario";
		String label = OntologyHelper.getLabel(ontClass);
		assertEquals(expected, label);
	}
	
	@Test
	public void testGetLabelNotEquals() {
		String unexpected = "Parlamentaria";
		String label = OntologyHelper.getLabel(ontClass);
		assertNotSame(unexpected, label);
	}
	
	@Test
	public void testGetLabelWithoutContent() {
		String unexpected = "";
		String label = OntologyHelper.getLabel(classWithoutLabel);
		assertNotSame(unexpected, label);
	}
	
	@Test
	public void testGetLabelAnonymousClass() {
		String expected = "Label not avaible";
		String label = OntologyHelper.getLabel(anonymousClass);
		assertEquals(expected, label);
	}
	
	@Test
	public void testGetCommentEquals() {
		String expected = "Una persona que es parlamentario.";
		String comment = OntologyHelper.getComment(ontClass);
		assertEquals(expected, comment);
	}
	
	@Test
	public void testGetCommentNotEquals() {
		String unexpected = "Una persona que es parlamentaria.";
		String comment = OntologyHelper.getComment(ontClass);
		assertNotSame(unexpected, comment);
	}
	
	@Test
	public void testGetCommentWithoutContent() {
		String unexpected = "";
		String comment = OntologyHelper.getComment(classWithoutLabel);
		assertNotSame(unexpected, comment);
	}
	
	@Test
	public void testGetCommentAnonymousClass() {
		String expected = "Comment not avaible";
		String comment = OntologyHelper.getComment(anonymousClass);
		assertEquals(expected, comment);
	}
}
