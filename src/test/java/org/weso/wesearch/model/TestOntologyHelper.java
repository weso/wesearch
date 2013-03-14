package org.weso.wesearch.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.weso.utils.OntoModelException;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.ValueSelector;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.PropertiesImpl;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class TestOntologyHelper {
	
	private String className = 
			"http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary"; 
	private String classNameWithoutLabel = 
			"http://datos.bcn.cl/ontologies/bcn-biographies#ParliamentaryTest";
	private String anonymousClassName = "";
	private OntModel ont = null;
	private OntClass ontClass = null;
	private OntClass classWithoutLabel = null;
	private OntClass anonymousClass = null;
	private OntClass ontClassEnglish = null;
	private OntClass ontClassWithoutTag = null;
	
	@Before
	public void configure() throws FileNotFoundException {
		ont = ModelFactory.createOntologyModel();
		ont.read(new FileInputStream(
				new File("src/test/resources/ontoTest1.owl")), "");
		ontClass = ont.getOntClass(
				"http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary");
        classWithoutLabel = ont.getOntClass("http://datos.bcn.cl/ontologies/" +
        		"bcn-biographies#ParliamentaryTest");
        anonymousClass = ont.createClass();
        ontClassEnglish = ont.getOntClass(
        		"http://datos.bcn.cl/ontologies/bcn-biographies#Deputy");
        ontClassWithoutTag = ont.getOntClass(
        		"http://datos.bcn.cl/ontologies/bcn-biographies#Embassy");
	}
	
	@Test
	public void testCreateMatterFromResourceIdEquals() {
		Matter expected = new MatterImpl("Parlamentario", 
				"http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary",
				"Una persona que es parlamentario.");
		Matter actual = OntologyHelper.createMatter(className, ont);
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testCreateMatterFromResourceIdNotEquals() {
		Matter unexpected = new MatterImpl("Parlamentaria", 
				"http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary",
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
	public void testGetLabelIdAnonymousClass() {
		String expected = "Label not available";
		String label = OntologyHelper.getLabel(anonymousClassName, ont);
		assertEquals(expected, label);
	}
	
	@Test
	public void testGetLabelFromResourceAnonymousClass() 
			throws NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException {
		String expected = "Label not available";
		Method method = OntologyHelper.class.getDeclaredMethod(
				"getLabelFromResource",
				Resource.class);
		method.setAccessible(true);
		String result = (String)method.invoke(OntologyHelper.class, 
				anonymousClass);
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetLabelFromOntResourceEnglishTag() 
			throws NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException {
		String expected = "Deputy";
		Method method = OntologyHelper.class.getDeclaredMethod(
				"getLabelFromOntResource",
				Resource.class);
		method.setAccessible(true);
		String result = (String)method.invoke(OntologyHelper.class, 
				ontClassEnglish);
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetLabelFromOntResourceWithoutLanguageTag() 
			throws NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException {
		String expected = "Embassy";
		Method method = OntologyHelper.class.getDeclaredMethod(
				"getLabelFromOntResource",
				Resource.class);
		method.setAccessible(true);
		String result = (String)method.invoke(OntologyHelper.class, 
				ontClassWithoutTag);
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetCommentFromResourceAnonymousClass() 
			throws NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException {
		String expected = "Comment not available";
		Method method = OntologyHelper.class.getDeclaredMethod(
				"getCommentFromResource", Resource.class);
		method.setAccessible(true);
		String result = (String)method.invoke(OntologyHelper.class, 
				anonymousClass);
		assertEquals(expected, result);
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
	public void testGetCommentFromOntResourceEnglishTag() 
			throws NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException {
		String expected = "A person who is deputy";
		Method method = OntologyHelper.class.getDeclaredMethod(
				"getCommentFromOntResource", Resource.class);
		method.setAccessible(true);
		String result = (String)method.invoke(OntologyHelper.class, 
				ontClassEnglish);
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetCommentFromOntResourceWithoutLanguageTag() 
			throws NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException {
		String expected = "the official residence or offices of an ambassador.";
		Method method = OntologyHelper.class.getDeclaredMethod(
				"getCommentFromOntResource", Resource.class);
		method.setAccessible(true);
		String result = (String)method.invoke(OntologyHelper.class, 
				ontClassWithoutTag);
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetCommentFromResourceIdAnonymousClass() {
		String expected = "Comment not avaible";
		String comment = OntologyHelper.getComment(anonymousClassName, ont);
		assertEquals(expected, comment);
	}
	
	@Test
    public void testCreateMatterEquals() {
            Matter expected = new MatterImpl("Parlamentario", 
            		"http://datos.bcn.cl/ontologies/bcn-biographies#" +
            		"Parliamentary",
                            "Una persona que es parlamentario.");
            Matter actual = OntologyHelper.createMatter(ontClass);
            assertTrue(expected.equals(actual));
    }
    
    @Test
    public void testCreateMatterNotEquals() {
            Matter unexpected = new MatterImpl("Parlamentaria", 
            		"http://datos.bcn.cl/ontologies/bcn-biographies#" +
            		"Parliamentary",
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
    	String expected = 
    			"http://datos.bcn.cl/ontologies/bcn-biographies#hasBorn";
    	Properties properties = OntologyHelper.obtainPropertiesByMatter(
    			ontClass, ontClass.listSuperClasses());
    	assertTrue(properties.iterator().hasNext());
    	assertEquals(expected, properties.iterator().next().getUri());
    }
    
    @Test
    public void testCreateProperty() {
    	String uriExpected = 
    			"http://datos.bcn.cl/ontologies/bcn-biographies#hasBorn";
    	String labelExpected = "Ha nacido";
    	String commentExpected = 
    			"relaciona a una persona con los datos de su nacimiento";
    	Property prop = OntologyHelper.createProperty(
    			ont.getProperty("http://datos.bcn.cl/ontologies/" +
    					"bcn-biographies#hasBorn"));
    	assertEquals(uriExpected, prop.getUri());
    	assertEquals(labelExpected, prop.getName());
    	assertEquals(commentExpected, prop.getDescription());
    }
    
    @Test
    public void testExtractPropertyRangeObjectType() {
    	String expected = ValueSelector.OBJECT;
    	OntProperty p = ont.getOntProperty(
    			"http://datos.bcn.cl/ontologies/bcn-biographies#hasBorn");
    	String result = OntologyHelper.extractPropertyRange(p);
    	assertEquals(expected, result);
    }
    
    @Test
    public void testExtractPropertyRangeStringType() {
    	String expected = ValueSelector.TEXT;
    	OntProperty p = ont.getOntProperty(
    			"http://datos.bcn.cl/ontologies/bcn-biographies#name");
    	String result = OntologyHelper.extractPropertyRange(p);
    	assertEquals(expected, result);
    }
    
    @Test
    public void testExtractPropertyRangeNumericType() {
    	String expected = ValueSelector.NUMERIC;
    	OntProperty p = ont.getOntProperty(
    			"http://datos.bcn.cl/ontologies/bcn-biographies#identifier");
    	String result = OntologyHelper.extractPropertyRange(p);
    	assertEquals(expected, result);
    }
    
    @Test
    public void testExtractPropertyRangeDateType() {
    	String expected = ValueSelector.DATE;
    	OntProperty p = ont.getOntProperty(
    			"http://datos.bcn.cl/ontologies/bcn-biographies#hasDead");
    	String result = OntologyHelper.extractPropertyRange(p);
    	assertEquals(expected, result);
    }
    
    @Test
    public void testExtractPropertyNull() {
    	String expected = ValueSelector.UNDEFINED;
    	String result = OntologyHelper.extractPropertyRange(null);
    	assertEquals(expected, result);
    }
    
    @Test
    public void testExtractValueSelectorFromListEmpty() 
    		throws NoSuchMethodException, SecurityException, 
    		IllegalAccessException, IllegalArgumentException, 
    		InvocationTargetException {
    	Method method = OntologyHelper.class.getDeclaredMethod(
    			"extractValueSelectorFromList",
    			ExtendedIterator.class);
    	method.setAccessible(true);
    	String expected = ValueSelector.UNDEFINED;
    	OntProperty p = ont.getOntProperty(
    			"http://datos.bcn.cl/ontologies/bcn-biographies#propertyTest");
    	String result = (String)method.invoke(OntologyHelper.class, 
    			p.listRange());
    	assertEquals(expected, result);
    }
    
    @Test
    public void testExtractValueSelectorNull() 
    		throws NoSuchMethodException, SecurityException, 
    		IllegalAccessException, IllegalArgumentException, 
    		InvocationTargetException {
    	Method method = OntologyHelper.class.getDeclaredMethod(
    			"extractValueSelector", 
    			OntResource.class);
    	method.setAccessible(true);
    	String expected = ValueSelector.UNDEFINED;
    	OntResource res = null;
    	String result = (String)method.invoke(OntologyHelper.class, res);
    	assertEquals(expected, result);
    }
    
    @Test
    public void testExtractValueSelectorWithoutUri() 
    		throws NoSuchMethodException, SecurityException, 
    		IllegalAccessException, IllegalArgumentException, 
    		InvocationTargetException {
    	Method method = OntologyHelper.class.getDeclaredMethod(
    			"extractValueSelector", 
    			OntResource.class);
    	method.setAccessible(true);
    	String expected = ValueSelector.UNDEFINED;
    	String result = (String)method.invoke(OntologyHelper.class, ontClass);
    	assertEquals(expected, result);
    }
    
    @Test(expected=OntoModelException.class)
    public void testCreateRangeMattersWithParamNull() 
    		throws OntoModelException {
    	ExtendedIterator<? extends OntResource> it = null;
    	OntologyHelper.createRangeMatters(it);
    }
    
    @Test
    public void testCreateRangeMatters() throws OntoModelException {
    	ExtendedIterator<? extends OntResource> it = 
    			ont.getOntProperty(
					"http://datos.bcn.cl/ontologies/bcn-biographies#hasBorn")
					.listRange();
    	Matters result = OntologyHelper.createRangeMatters(it);
    	assertNotNull(result);
    	assertTrue(result.size() > 0);
    }
}
