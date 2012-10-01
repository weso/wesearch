package org.weso.wesearch;

/*
 * Container of subjects
 */
public interface Subjects extends Iterable<Subject> {
  
	/*
	 * Returns the a subject with a given label
	 */
	Subject findSubject(String Label);
}
