package org.weso.wesearch.domain;

import org.weso.utils.WesearchException;

/*
 * Container of subjects
 */
public interface Matters extends Iterable<Matter> {
  
	/*
	 * Returns the a subject with a given label
	 */
	Matter findMatter(String label) throws WesearchException;
	
	/*
	 * Adds a matter to the list
	 */
	void addMatter(Matter m);
	
	/*
	 * Returns the number of elements
	 */
	int size();
}
