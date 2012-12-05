package org.weso.wesearch.context.impl;

import org.weso.utils.OntoModelException;
import org.weso.wesearch.context.Context;
import org.weso.wesearch.model.OntoModelWrapper;

public class JenaContext implements Context {
	
	private OntoModelWrapper modelWrapper;
	
	public JenaContext(OntoModelWrapper modelWrapper) throws OntoModelException {
		this.modelWrapper = modelWrapper;
	}

	@Override
	public OntoModelWrapper getOntologiesModel() {
		return modelWrapper;
	}
	

}
