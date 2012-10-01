package org.weso.wesearch;

public interface Wesearch {
	
	/*
	 * Returns a container of subjects
	 */
	Subjects getSubjects();
	
	/*
	 * Returns a container of properties for a given subject
	 */
	Properties getProperties(Subject s);
	
	/*
	 * Returns a value selector for a given subject and property
	 */
	ValueSelector getValueSelector(Subject s, Property p);
	
	/*
	 * Returns a query for a given subject, property and value selector
	 */
	Query createQuery(Subject s, Property p, ValueSelector v);
	
	/*
	 * Returns the version
	 */
	String version();

}
