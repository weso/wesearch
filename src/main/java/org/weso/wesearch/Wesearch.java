package org.weso.wesearch;

public interface Wesearch {
	
	/*
	 * Returns a container of subjects
	 */
	Matters getMatters();
	
	/*
	 * Returns a container of properties for a given subject
	 */
	Properties getProperties(Matter s);
	
	/*
	 * Returns a value selector for a given subject and property
	 */
	ValueSelector getValueSelector(Matter s, Property p);
	
	/*
	 * Returns a query for a given subject, property and value selector
	 */
	Query createQuery(Matter s, Property p, ValueSelector v);
	
	/*
	 * Given a query, returns a new query for a given subject, property and value 
	 */
	Query combineQuery(Query q, Matter s, Property p, ValueSelector v);

	/*
	 * Returns the version
	 */
	String version();

}
