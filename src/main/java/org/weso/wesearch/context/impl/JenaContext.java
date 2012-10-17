package org.weso.wesearch.context.impl;

import org.weso.wesearch.context.Context;
import org.weso.wesearch.model.OntoLoader;
import org.weso.wesearch.model.OntoModelWrapper;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

public class JenaContext implements Context {
	
	private OntoLoader ontoLoader;
	private OntoModelWrapper modelWrapper;
	
	public JenaContext(String[] fileNames) {
		ontoLoader = new FileOntologyLoader(fileNames);
		modelWrapper = new JenaOntoModelWrapper(ontoLoader);
	}

	@Override
	public OntoModelWrapper getOntologiesModel() {
		return modelWrapper;
	}
	

}
