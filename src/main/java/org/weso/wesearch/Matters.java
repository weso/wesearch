package org.weso.wesearch;

import org.weso.utils.WesearchException;

/*
 * Container of subjects
 */
public interface Matters extends Iterable<Matter> {
  
	/*
	 * Returns the a subject with a given label
	 */
	Matter findMatter(String label) throws WesearchException;
}
