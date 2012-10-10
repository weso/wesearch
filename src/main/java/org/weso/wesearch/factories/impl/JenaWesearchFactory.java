package org.weso.wesearch.factories.impl;

import java.util.List;

import org.weso.wesearch.JenaWesearch;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.context.impl.JenaContext;
import org.weso.wesearch.factories.WesearchFactory;

public class JenaWesearchFactory extends WesearchFactory {

	@Override
	public Wesearch createWesearch(List<String> ontologies) {
		return createWesearch(ontologies.toArray(new String[ontologies.size()]));
	}

	@Override
	public Wesearch createWesearch(String[] ontologies) {
		return new JenaWesearch(new JenaContext(ontologies));
	}

}
