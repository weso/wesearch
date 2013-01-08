package org.weso.wesearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
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
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.Query;
import org.weso.wesearch.domain.ValueSelector;
import org.weso.wesearch.domain.impl.JenaPropertyImpl;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.ValueSelectorImpl;
import org.weso.wesearch.domain.impl.values.StringValue;
import org.weso.wesearch.factories.WesearchFactory;
import org.weso.wesearch.factories.impl.JenaWesearchFactory;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

import weso.mediator.core.domain.Suggestion;
import weso.mediator.core.persistence.jena.JenaModelFileWrapper;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;


public class TestJenaWesearch {
	
	private String[] files = {"src/test/resources/ontoTest1.owl"};
	private List<Suggestion> suggestions = null;
	
	@Before
	public void initialize() throws OntoModelException, FileNotFoundException {
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
		JenaModelFileWrapper.getInstance().loadModelFromModel((Model)modelWrapper.getModel());
		String expectedLabel = "Parlamentario";
		String expectedComment = "Una persona que es parlamentario.";
		String expectedUri = "http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary";
		Wesearch ws = factory.createWesearch(modelWrapper);
		Matters ms = ws.getMatters("Parlamentario");
		assertEquals(1, ms.size());
		Iterator<Matter> it = ms.iterator();
		while(it.hasNext()) {
			 Matter m = it.next();
			 assertEquals(expectedLabel, m.getLabel());
			 assertEquals(expectedComment, m.getDescription());
			 assertEquals(expectedUri, m.getUri());
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
	    assertEquals(expectedComment, m.getDescription());
	    assertEquals(expectedLabel, m.getLabel());
	    assertEquals(expectedUri, m.getUri());
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
		JenaModelFileWrapper.getInstance().loadModelFromModel((Model)modelWrapper.getModel());
		Wesearch ws = factory.createWesearch(modelWrapper);
		Matter m = new MatterImpl("Parlamentario", 
				"http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary", 
				"Una persona que es parlamentario.");
		Properties properties = ws.getProperties(m, "nacido");
		assertNotNull(properties.getPropertyByName("Ha nacido"));
	}
	
	@Test
	public void testGetValueSelectorWithObjectSelector() 
			throws WesearchException, OntoModelException {
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(files));
		Wesearch ws = factory.createWesearch(modelWrapper);
		Matter matter = new MatterImpl("Parlamentario", 
				"http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary", 
				"Una persona que es parlamentario.");
		Property p = new JenaPropertyImpl("http://datos.bcn.cl/ontologies/bcn-biographies#hasBorn",
				"Ha nacido", 
				"relaciona a una persona con los datos de su nacimiento");
		ValueSelector selector = ws.getValueSelector(matter, p);
		assertEquals(ValueSelector.OBJECT, selector.getType());
		assertNotNull(selector.getValue());
		Matters matters = (Matters)selector.getValue().getValue();
		Iterator<Matter> it = matters.iterator();
		boolean containsBirth = false;
		while(it.hasNext()) {
			Matter m = it.next();
			if(m.getUri().equals("http://purl.org/vocab/bio/0.1/Birth")) {
				containsBirth = true;
			}
		}
		assertTrue(containsBirth);
	}
	
	@Test
	public void testGetValueSelectorWithStringSelector() 
			throws WesearchException, OntoModelException {
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(files));
		Wesearch ws = factory.createWesearch(modelWrapper);
		Matter matter = new MatterImpl("Parlamentario", 
				"http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary", 
				"Una persona que es parlamentario.");
		Property p = new JenaPropertyImpl("http://datos.bcn.cl/ontologies/bcn-biographies#name",
				"Nombre", 
				"Indica el nombre de un parlamentario");
		ValueSelector selector = ws.getValueSelector(matter, p);
		assertEquals(ValueSelector.TEXT, selector.getType());
	}
	
	@Test
	public void testGetValueSelectorWithNumericSelector() 
			throws WesearchException, OntoModelException {
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(files));
		Wesearch ws = factory.createWesearch(modelWrapper);
		Matter matter = new MatterImpl("Parlamentario", 
				"http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary", 
				"Una persona que es parlamentario.");
		Property p = new JenaPropertyImpl("http://datos.bcn.cl/ontologies/bcn-biographies#identifier",
				"Identificador", 
				"Indica el identificador de un parlamentario");
		ValueSelector selector = ws.getValueSelector(matter, p);
		assertEquals(ValueSelector.NUMERIC, selector.getType());
	}
	
	@Test
	public void testGetValueSelectorWithDateSelector() 
			throws WesearchException, OntoModelException {
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(files));
		Wesearch ws = factory.createWesearch(modelWrapper);
		Matter matter = new MatterImpl("Parlamentario", 
				"http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary", 
				"Una persona que es parlamentario.");
		Property p = new JenaPropertyImpl("http://datos.bcn.cl/ontologies/bcn-biographies#hasDead",
				"Fecha de defunción", 
				"Indica la fecha de defunción de un parlamentario");
		ValueSelector selector = ws.getValueSelector(matter, p);
		assertEquals(ValueSelector.DATE, selector.getType());
	}
	
	@Test(expected=WesearchException.class)
	public void testGetValueSelectorWithPropertyNull() 
			throws WesearchException, OntoModelException {
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(files));
		Wesearch ws = factory.createWesearch(modelWrapper);
		Matter matter = new MatterImpl("Parlamentario", 
				"http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary", 
				"Una persona que es parlamentario.");
		Property p = null;
		ws.getValueSelector(matter, p);
	}
	
	@Test
	public void testCreateQuery() 
			throws WesearchException, OntoModelException {
		String expected = "SELECT DISTINCT ?res WHERE { ?res " +
				"<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> " +
				"?a . " +
				"?res <http://purl.weso.org/test#Property> ?b . " +
				"FILTER( regex(?b, \"Chile\", \"i\") ) .FILTER( ?a = <http://purl.weso.org/test#Class>  ) .}";
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(files));
		Wesearch ws = factory.createWesearch(modelWrapper);
		Matter m = new MatterImpl("label test", "http://purl.weso.org/test#Class", 
				"This is a test class");
		Property p = new JenaPropertyImpl("http://purl.weso.org/test#Property", 
				"Test property", "This is a test property");
		ValueSelector selector = new ValueSelectorImpl(ValueSelector.TEXT);
		selector.setValue(new StringValue("Chile"));
		Query q = ws.createQuery(m, p, selector);
		assertNotNull(q);
		assertEquals(expected, q.obtainQuery());
	}
	
	@Test(expected=WesearchException.class)
	public void testCreateQueryWithParamsNull() 
			throws WesearchException, OntoModelException {
		WesearchFactory factory = new JenaWesearchFactory();
		OntoModelWrapper modelWrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(files));
		Wesearch ws = factory.createWesearch(modelWrapper);
		ws.createQuery(null, null, null);
	}

}
