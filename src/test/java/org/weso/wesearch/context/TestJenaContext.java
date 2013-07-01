package org.weso.wesearch.context;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.junit.Test;
import org.weso.utils.OntoModelException;
import org.weso.wesearch.context.impl.JenaContext;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

import weso.mediator.core.persistence.jena.JenaModelFileWrapper;

import com.hp.hpl.jena.ontology.OntModel;

public class TestJenaContext {
	
	@Test
	public void testGetOntologiesModel() throws OntoModelException {
		String[] files = {"src/test/resources/index.rdf"};
		OntoModelWrapper wrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(files));
		JenaContext context = new JenaContext(wrapper);
		try {
			assertTrue(context.getOntologiesModel().getModel() 
					instanceof OntModel);
		} catch (OntoModelException e) {
			assert(false);
		}
	}
	
	@Test(expected=OntoModelException.class)
	public void testGetOntologiesModelFail() throws OntoModelException {
		String[] files = {"src/test/resources/test.txt"};
		OntoModelWrapper wrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(files));
		@SuppressWarnings("unused")
		JenaContext context = new JenaContext(wrapper);
	}
	
	@Test
	public void testPassModelToWesomed() throws OntoModelException, IOException {
		modifyProperties("virtual");
		String[] files = {"src/test/resources/index.rdf"};
		OntoModelWrapper wrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(files));
		@SuppressWarnings("unused")
		JenaContext context = new JenaContext(wrapper);		
		modifyProperties("src/main/resources/model.owl");
	}
	
	@Test(expected=InvocationTargetException.class)
	public void testPassModelToWesomedNull() throws OntoModelException, 
		NoSuchMethodException, SecurityException, IllegalAccessException, 
		IllegalArgumentException, InvocationTargetException {
		String[] files = {"src/test/resources/index.rdf"};
		OntoModelWrapper wrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(files));
		JenaContext context = new JenaContext(wrapper);	
		OntModel model = null;
		Method method = context.getClass().getDeclaredMethod("passModelToWesomed", OntModel.class);
		method.setAccessible(true);
		method.invoke(context, model);
	}
	
	/*@After
	public void restoreProperties() throws IOException {
		modifyProperties("src/main/resources/model.owl");
	}*/

	@SuppressWarnings("deprecation")
	private void modifyProperties(String newValue) throws IOException, FileNotFoundException {
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("config.properties"));
		props.setProperty("datasource_uri", newValue);
		props.save(new FileOutputStream(Thread.currentThread().getContextClassLoader()
				.getResource("config.properties").getPath()), "");
	}
	
	@Test
	public void testSaveModelFail() throws OntoModelException, FileNotFoundException, IOException {
		modifyProperties("D:/error");
		String[] files = {"src/test/resources/index.rdf"};
		OntoModelWrapper wrapper = new JenaOntoModelWrapper(
				new FileOntologyLoader(files));
		@SuppressWarnings("unused")
		JenaContext context = new JenaContext(wrapper);
		assertTrue(JenaModelFileWrapper.getInstance().getModel() != null);
		modifyProperties("src/main/resources/model.owl");
	}

}
