package org.weso.wesearch;

/*
 * Container of subjects
 */
public interface Matters extends Iterable<Matter> {
  
	/*
	 * Returns the a subject with a given label
	 */
	Matter findMatter(String Label);
}
