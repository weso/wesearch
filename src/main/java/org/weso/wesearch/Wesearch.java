package org.weso.wesearch;

import org.weso.utils.WesearchException;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.Query;
import org.weso.wesearch.domain.ValueSelector;

public interface Wesearch {
	
	/*
	 * Returns a container of subjects
	 */
	Matters getMatters(String stem) throws WesearchException;
	
	/*
	 * Returns a container of properties for a given subject
	 */
	Properties getProperties(Matter s, String stem) throws WesearchException;
	
	/*
	 * Returns a value selector for a given subject and property
	 */
	ValueSelector getValueSelector(Matter s, Property p, String stem);
	
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
