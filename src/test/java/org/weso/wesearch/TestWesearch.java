package org.weso.wesearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.weso.utils.NotImplementedException;
import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.context.Context;
import org.weso.wesearch.context.impl.JenaContext;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

import weso.mediator.core.domain.Suggestion;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;


public class TestWesearch {
	
	private Context ctx = null;
	private List<Suggestion> suggestions;
	
	@Before
	public void initialize() throws OntoModelException {
		String[] files = {"src/test/resources/ontoTest1.owl"};
		OntoModelWrapper wrapper = new JenaOntoModelWrapper(new FileOntologyLoader(files));
		ctx = new JenaContext(wrapper);
		suggestions = new LinkedList<Suggestion>();
		suggestions.add(new Suggestion("http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary", 
				(float)0.8));
		suggestions.add(new Suggestion("http://datos.bcn.cl/ontologies/bcn-biographies#ParliamentaryTest" ,
				(float)0.7));
	}

	@Test
	public void testVersion() throws WesearchException {
		JenaWesearch ws = new JenaWesearch(ctx);
		assertEquals(ws.version(),"0.1");
	}

	@Test
	public void testGetMattersWithLabel() throws WesearchException {
		String expectedLabel = "Parlamentario";
		String expectedComment = "Una persona que es parlamentario.";
		String expectedUri = "http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary";
		JenaWesearch ws = new JenaWesearch(ctx);
		Matters ms = ws.getMatters("Parlamentario");
		assertTrue(ms.size() == 1);
		Iterator<Matter> it = ms.iterator();
		while(it.hasNext()) {
			 Matter m = it.next();
			 assertEquals(expectedLabel, m.label());
			 assertEquals(expectedComment, m.description());
			 assertEquals(expectedUri, m.uri());
		}
	}
	
	@Test
	public void testGetMattersWithoutLable() throws WesearchException {
		JenaWesearch ws = new JenaWesearch(ctx);
		 Matters ms = ws.getMatters("");
		 assertTrue(ms.size() > 0);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testCreateMatterFromResourceId() throws SecurityException, NoSuchMethodException, 
		OntoModelException, IllegalArgumentException, IllegalAccessException, 
		InvocationTargetException, WesearchException {
		JenaWesearch wesearch = new JenaWesearch(ctx);
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
		String expectedLabel = "Parlamentario";
		String expectedComment = "Una persona que es parlamentario.";
		String expectedUri = "http://datos.bcn.cl/ontologies/bcn-biographies#Parliamentary";
	    JenaWesearch wesearch = new JenaWesearch(ctx);
	    Class[] params = {ExtendedIterator.class};
	    Method method = JenaWesearch.class.getDeclaredMethod("createMatterFromResources", params);
	    method.setAccessible(true);
	    Object[] paramsValues = {((OntModel)ctx.getOntologiesModel().getModel()).listClasses()};
	    Matters matters = (Matters)method.invoke(wesearch, paramsValues);       
	    Matter m = matters.findMatter("Parlamentario");
	    assertEquals(expectedComment, m.description());
	    assertEquals(expectedLabel, m.label());
	    assertEquals(expectedUri, m.uri());
    }
	
	@Test(expected=NotImplementedException.class)
	public void testGetProperties() throws WesearchException {
		JenaWesearch ws = new JenaWesearch(ctx);
		Matter m = new MatterImpl("Diputado", "http://datos.bcn.cl/ontologies/bcn-biographies#Diputado", 
				"Un Parlamentario que es diputado.");
		Properties p = ws.getProperties(m, "born");
	}

}
