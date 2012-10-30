package org.weso.wesearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.factories.WesearchFactory;
import org.weso.wesearch.factories.impl.JenaWesearchFactory;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

import weso.mediator.core.domain.Suggestion;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;


public class TestWesearch {
	
	private String[] files = {"src/test/resources/ontoTest1.owl"};
	private List<Suggestion> suggestions = null;
	
	@Before
	public void initialize() throws OntoModelException {
		suggestions = new LinkedList<Suggestion>();
		suggestions.add(new Suggestion("http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary", 
				(float)0.8));
		suggestions.add(new Suggestion("http://datos.bcn.cl/ontologies/bcn-biographies#ParliamentaryTest" ,
				(float)0.7));
	}

	@Test
	public void testVersion() throws WesearchException, OntoModelException { 
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(new FileOntologyLoader(files));
		Wesearch ws = factory.createWesearch(modelWrapper);
		assertEquals(ws.version(),"0.1");
	}

	@Test
	public void testGetMattersWithLabel() throws WesearchException, OntoModelException {
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(new FileOntologyLoader(files));
		String expectedLabel = "Parlamentario";
		String expectedComment = "Una persona que es parlamentario.";
		String expectedUri = "http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary";
		Wesearch ws = factory.createWesearch(modelWrapper);
		Matters ms = ws.getMatters("Parlamentario");
		assertEquals(1, ms.size());
		Iterator<Matter> it = ms.iterator();
		while(it.hasNext()) {
			 Matter m = it.next();
			 assertEquals(expectedLabel, m.label());
			 assertEquals(expectedComment, m.description());
			 assertEquals(expectedUri, m.uri());
		}
	}
	
	@Test
	public void testGetMattersWithoutLable() throws WesearchException, OntoModelException {
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(new FileOntologyLoader(files));
		Wesearch ws = factory.createWesearch(modelWrapper);
		 Matters ms = ws.getMatters("");
		 assertTrue(ms.size() > 0);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testCreateMatterFromResourceId() throws SecurityException, NoSuchMethodException, 
		OntoModelException, IllegalArgumentException, IllegalAccessException, 
		InvocationTargetException, WesearchException {
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(new FileOntologyLoader(files));
		JenaWesearch wesearch = (JenaWesearch) factory.createWesearch(modelWrapper);
		Class[] params = {Iterator.class};
		Method method = JenaWesearch.class.getDeclaredMethod("createMatterFromResourceId", params);
		method.setAccessible(true);
		Object[] paramsValues = {suggestions.iterator()};
		Matters matters = (Matters)method.invoke(wesearch, paramsValues);	
		assertTrue(matters.size() == 2);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
    public void testCreateMatterFromResource() throws SecurityException, NoSuchMethodException, 
	    OntoModelException, IllegalArgumentException, IllegalAccessException, 
	    InvocationTargetException, WesearchException {
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(new FileOntologyLoader(files));
		String expectedLabel = "Parlamentario";
		String expectedComment = "Una persona que es parlamentario.";
		String expectedUri = "http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary";
	    JenaWesearch wesearch = (JenaWesearch) factory.createWesearch(modelWrapper);
	    Class[] params = {ExtendedIterator.class};
	    Method method = JenaWesearch.class.getDeclaredMethod("createMatterFromResources", params);
	    method.setAccessible(true);
	    Object[] paramsValues = {((OntModel)modelWrapper.getModel()).listClasses()};
	    Matters matters = (Matters)method.invoke(wesearch, paramsValues);       
	    Matter m = matters.findMatter("Parlamentario");
	    assertEquals(expectedComment, m.description());
	    assertEquals(expectedLabel, m.label());
	    assertEquals(expectedUri, m.uri());
    }
	
	@Test
	public void testGetPropertiesWithoutProperties() throws WesearchException, OntoModelException {
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(new FileOntologyLoader(files));
		Wesearch ws = factory.createWesearch(modelWrapper);
		Matter m = new MatterImpl("Parlamentario", 
				"http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary", 
				"Una persona que es parlamentario.");
		Properties p = ws.getProperties(m, "born");
		assertFalse(p.iterator().hasNext());
	}
	
	@Test
	public void testGetPropertiesWithProperties() throws OntoModelException, WesearchException {
		WesearchFactory factory = new JenaWesearchFactory();
		String[] fileNames = {"src/test/resources/ontoTest3.owl"};
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(fileNames));
		Wesearch ws = factory.createWesearch(modelWrapper);
		Matter m = new MatterImpl("Parlamentario", 
				"http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary", 
				"Una persona que es parlamentario.");
		Properties properties = ws.getProperties(m, "nacido");
		assertNotNull(properties.getPropertyByName("Ha nacido"));
	}

}
