package org.weso.wesearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.weso.utils.NotImplementedException;
import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.context.Context;
import org.weso.wesearch.context.impl.DefaultContext;
import org.weso.wesearch.context.impl.JenaContext;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.impl.MatterImpl;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;


public class TestWesearch {
	
	private Context ctx = null;
	
	@Before
	public void initialize() {
		String[] files = {"src/test/resources/ontoTest1.owl"};
		ctx = new JenaContext(files);
	}

	@Test
	public void testVersion() {
		Context ctx = new DefaultContext();
		JenaWesearch ws = new JenaWesearch(ctx);
		assertEquals(ws.version(),"0.1");
	}

	@Test
	public void testGetMatters() throws WesearchException {
	 JenaWesearch ws = new JenaWesearch(ctx);
	 Matters ms = ws.getMatters("");
	 assertTrue(ms.size() == 2);
	}
	
	@Test
	public void testCreateMatterFromResource() throws SecurityException, NoSuchMethodException, 
		OntoModelException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		JenaWesearch wesearch = new JenaWesearch(ctx);
		Class[] params = {ExtendedIterator.class};
		Method method = JenaWesearch.class.getDeclaredMethod("createMatterFromResource", params);
		method.setAccessible(true);
		Object[] paramsValues = {((OntModel)ctx.getOntologiesModel().getModel()).listClasses()};
		Matters matters = (Matters)method.invoke(wesearch, paramsValues);	
		assertTrue(matters.size() == 2);
	}
	
	@Test(expected=NotImplementedException.class)
	public void testGetProperties() {
		Context ctx = new DefaultContext();
		JenaWesearch ws = new JenaWesearch(ctx);
		Matter m = new MatterImpl("Diputado", "http://datos.bcn.cl/ontologies/bcn-biographies#Diputado", 
				"Un Parlamentario que es diputado.");
		Properties p = ws.getProperties(m, "born");
	}

}
