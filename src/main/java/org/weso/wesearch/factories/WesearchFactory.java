package org.weso.wesearch.factories;

import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.model.OntoModelWrapper;

public abstract class WesearchFactory {
	
	public abstract Wesearch createWesearch(OntoModelWrapper modelWrapper) 
			throws WesearchException, OntoModelException;

}
