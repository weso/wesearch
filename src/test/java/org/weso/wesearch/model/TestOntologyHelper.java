package org.weso.wesearch.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.PropertiesImpl;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class TestOntologyHelper {
	
	private String className = "http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary"; 
	private String classNameWithoutLabel = "http://datos.bcn.cl/ontologies/bcn-biographies#ParliamentaryTest";
	private String anonymousClassName = "";
	private OntModel ont = null;
	private OntClass ontClass = null;
	private OntClass classWithoutLabel = null;
	private OntClass anonymousClass = null;
	
	@Before
	public void configure() throws FileNotFoundException {
		ont = ModelFactory.createOntologyModel();
		ont.read(new FileInputStream(new File("src/test/resources/ontoTest1.owl")), "");
		ontClass = ont.getOntClass("http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary");
        classWithoutLabel = ont.getOntClass("http://datos.bcn.cl/ontologies/bcn-biographies#ParliamentaryTest");
        anonymousClass = ont.createClass();
	}
	
	@Test
	public void testCreateMatterFromResourceIdEquals() {
		Matter expected = new MatterImpl("Parlamentario", "http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary",
				"Una persona que es parlamentario.");
		Matter actual = OntologyHelper.createMatter(className, ont);
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testCreateMatterFromResourceIdNotEquals() {
		Matter unexpected = new MatterImpl("Parlamentaria", "http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary",
				"Una persona que es parlamentario.");
		Matter actual = OntologyHelper.createMatter(className, ont);
		assertTrue(!unexpected.equals(actual));
	}

	@Test
	public void testGetLabelFromResourceIdEquals() {
		String expected = "Parlamentario";
		String label = OntologyHelper.getLabel(className, ont);
		assertEquals(expected, label);
	}
	
	@Test
	public void testGetLabelFromResourceIdNotEquals() {
		String unexpected = "Parlamentaria";
		String label = OntologyHelper.getLabel(className, ont);
		assertNotSame(unexpected, label);
	}
	
	@Test
	public void testGetLabelFromResourceIdWithoutContent() {
		String unexpected = "";
		String label = OntologyHelper.getLabel(classNameWithoutLabel, ont);
		assertNotSame(unexpected, label);
	}
	
	@Test
	public void testGetLabelFromResourceIdAnonymousClass() {
		String expected = "Label not avaible";
		String label = OntologyHelper.getLabel(anonymousClassName, ont);
		assertEquals(expected, label);
	}
	
	@Test
	public void testGetCommentFromResourceIdEquals() {
		String expected = "Una persona que es parlamentario.";
		String comment = OntologyHelper.getComment(className, ont);
		assertEquals(expected, comment);
	}
	
	@Test
	public void testGetCommentFromResourceIdNotEquals() {
		String unexpected = "Una persona que es parlamentaria.";
		String comment = OntologyHelper.getComment(className, ont);
		assertNotSame(unexpected, comment);
	}
	
	@Test
	public void testGetCommentFromResourceIdWithoutContent() {
		String unexpected = "";
		String comment = OntologyHelper.getComment(classNameWithoutLabel, ont);
		assertNotSame(unexpected, comment);
	}
	
	@Test
	public void testGetCommentFromResourceIdAnonymousClass() {
		String expected = "Comment not avaible";
		String comment = OntologyHelper.getComment(anonymousClassName, ont);
		assertEquals(expected, comment);
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
            String expected = "Label not available";
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
            String expected = "Comment not available";
            String comment = OntologyHelper.getComment(anonymousClass);
            assertEquals(expected, comment);
    }
    
    @Test
    public void testExtractPropertiesFromOntClassWithoutProperties() {
    	Properties properties = new PropertiesImpl();
    	OntologyHelper.extractPropertiesFromOntClass(properties, 
    			classWithoutLabel);
    	assertFalse(properties.iterator().hasNext());
    }
    
    @Test
    public void testExtractPropertiesFromOntClassWithProperties() {
    	Properties properties = new PropertiesImpl();
    	OntologyHelper.extractPropertiesFromOntClass(properties, 
    			ontClass);
    	assertTrue(properties.iterator().hasNext());
    }
    
    @Test
    public void testObtainPropertiesByMatter() {
    	String expected = "http://datos.bcn.cl/ontologies/bcn-biographies#hasBorn";
    	Properties properties = OntologyHelper.obtainPropertiesByMatter(ontClass, 
    			ontClass.listSuperClasses());
    	assertTrue(properties.iterator().hasNext());
    	assertEquals(expected, properties.iterator().next().getUri());
    }
    
    @Test
    public void testCreateProperty() {
    	String uriExpected = "http://datos.bcn.cl/ontologies/bcn-biographies#hasBorn";
    	String labelExpected = "Ha nacido";
    	String commentExpected = "relaciona a una persona con los datos de su nacimiento";
    	Property prop = OntologyHelper.createProperty(
    			ont.getProperty("http://datos.bcn.cl/ontologies/bcn-biographies#hasBorn"));
    	assertEquals(uriExpected, prop.getUri());
    	assertEquals(labelExpected, prop.getName());
    	assertEquals(commentExpected, prop.getDescription());
    }
}
