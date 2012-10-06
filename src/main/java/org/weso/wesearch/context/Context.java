package org.weso.wesearch.context;

import java.util.List;

/*
 * Context of the search engine
 */
public interface Context {
	
	/*
	 * Add new ontoloy to the search context 
	 */
	void addOntology(String uri);
	
	/*
	 * Obtain all ontologies from context
	 */
	List<String> ontologies();
}
