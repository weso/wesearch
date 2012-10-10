package org.weso.wesearch.factories;

import java.util.List;

import org.weso.wesearch.Wesearch;

public abstract class WesearchFactory {
	
	public abstract Wesearch createWesearch(List<String> ontologies);
	
	public abstract Wesearch createWesearch(String[] ontologies);

}
