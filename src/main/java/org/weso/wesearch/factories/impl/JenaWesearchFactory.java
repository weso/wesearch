package org.weso.wesearch.factories.impl;

import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.JenaWesearch;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.context.impl.JenaContext;
import org.weso.wesearch.factories.WesearchFactory;
import org.weso.wesearch.model.OntoModelWrapper;

/**
 * It's an implementation of WesearchFactory that allows create an instance of
 * JenaWesearchFactory. An instance of wesearch that use Apache Jena to works
 * with ontologies.
 * @author Ignacio Fuertes Bernardo
 *
 */
public class JenaWesearchFactory extends WesearchFactory {

	@Override
	public Wesearch createWesearch(OntoModelWrapper modelWrapper) 
			throws WesearchException, OntoModelException {
		return new JenaWesearch(new JenaContext(modelWrapper));
	}

}
