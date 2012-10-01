package org.weso.wesearch;

/*
 * Container of subjects
 */
public interface Subjects extends Iterable<Subject> {
  
	/*
	 * Returns the first subject in the container
	 */
	Subject getSubject();
}
