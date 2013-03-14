package org.weso.wesearch.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.weso.utils.OntoModelException;
import org.weso.wesearch.model.OntoModelWrapper;

import com.hp.hpl.jena.ontology.OntModel;

public class TestJenaOntoModelWrapper {
	
	private String[] uris = {"http://xmlns.com/foaf/spec/index.rdf"};
	private URLOntologyLoader loader = null;
	
	@Before
	public void initialize() {
		System.setProperty("http.proxyHost", "proxy.uniovi.es");
		System.setProperty("http.proxyPort", "8888");
		loader = new URLOntologyLoader(uris);
	}

	@Test
	public void testJenaOntoModelWrapperWithNull() throws SecurityException, 
	NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		JenaOntoModelWrapper modelWrapper = new JenaOntoModelWrapper(null);
		Field f = JenaOntoModelWrapper.class.getDeclaredField("loader");
		f.setAccessible(true);
		assertNull(f.get(modelWrapper));
	}
	
	@Test
	public void testJenaOntoModelWrapperWithoutNull() throws SecurityException, 
	NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		JenaOntoModelWrapper modelWrapper = new JenaOntoModelWrapper(loader);
		Field f = JenaOntoModelWrapper.class.getDeclaredField("loader");
		f.setAccessible(true);
		assertNotNull(f.get(modelWrapper));
		assertEquals(loader, f.get(modelWrapper));
	}
	
	@Test
	public void testGetModel() throws OntoModelException {
		JenaOntoModelWrapper modelWrapper = new JenaOntoModelWrapper(loader);
		assertNotNull(modelWrapper.getModel());
		assertTrue(modelWrapper.getModel() instanceof OntModel);
	}
	
	@Test
	public void testCreateJenaModel() throws SecurityException, 
	NoSuchMethodException, IllegalArgumentException, IllegalAccessException, 
		InvocationTargetException {
		JenaOntoModelWrapper modelWrapper = new JenaOntoModelWrapper(loader);
		Method method = JenaOntoModelWrapper.class.getDeclaredMethod(
				"createJenaModel");
		method.setAccessible(true);
		Object obj = method.invoke(modelWrapper);
		assertNotNull(obj);
		assertTrue(obj instanceof OntModel);
		assertFalse(((OntModel)obj).isEmpty());
	}
	
	@Test
	public void testGetLoaderNull() {
		OntoModelWrapper wrapper = new JenaOntoModelWrapper(null);
		assertNull(wrapper.getLoader());
	}
	
	@Test
	public void testGetLoaderNotNull() {
		OntoModelWrapper wrapper = new JenaOntoModelWrapper(loader);
		assertNotNull(wrapper.getLoader());
		assertTrue(wrapper.getLoader() instanceof URLOntologyLoader);
	}
}
