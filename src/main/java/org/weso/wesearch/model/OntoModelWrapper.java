package org.weso.wesearch.model;

import org.weso.utils.OntoModelException;

public interface OntoModelWrapper {
	
	Object getModel() throws OntoModelException;
	
	OntoLoader getLoader();

}
