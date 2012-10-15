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
	public void testJenaOntoModelWrapperWithNull() {
		JenaOntoModelWrapper modelWrapper = new JenaOntoModelWrapper(null);
		try {
			Field f = JenaOntoModelWrapper.class.getDeclaredField("loader");
			f.setAccessible(true);
			assertNull(f.get(modelWrapper));
		} catch (SecurityException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testJenaOntoModelWrapperWithoutNull() {
		JenaOntoModelWrapper modelWrapper = new JenaOntoModelWrapper(loader);
		try {
			Field f = JenaOntoModelWrapper.class.getDeclaredField("loader");
			f.setAccessible(true);
			assertNotNull(f.get(modelWrapper));
			assertEquals(loader, f.get(modelWrapper));
		} catch (SecurityException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetModel() {
		JenaOntoModelWrapper modelWrapper = new JenaOntoModelWrapper(loader);
		try {
			assertNotNull(modelWrapper.getModel());
			assertTrue(modelWrapper.getModel() instanceof OntModel);
		} catch (OntoModelException e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateJenaModel() {
		JenaOntoModelWrapper modelWrapper = new JenaOntoModelWrapper(loader);
		try {
			Method method = JenaOntoModelWrapper.class.getDeclaredMethod("createJenaModel");
			method.setAccessible(true);
			Object obj = method.invoke(modelWrapper);
			assertNotNull(obj);
			assertTrue(obj instanceof OntModel);
			assertFalse(((OntModel)obj).isEmpty());
		} catch (SecurityException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}
}
