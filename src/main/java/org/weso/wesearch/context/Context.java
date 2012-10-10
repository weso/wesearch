package org.weso.wesearch.context;

import org.weso.wesearch.model.OntoModelWrapper;

/*
 * Context of the search engine
 */
public interface Context {
	
	OntoModelWrapper getOntologiesModel();
}
