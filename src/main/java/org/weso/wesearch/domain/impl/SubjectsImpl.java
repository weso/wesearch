package org.weso.wesearch.domain.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.weso.utils.WesearchException;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;

public class SubjectsImpl implements Matters {
	
	private static Logger logger = Logger.getLogger(SubjectsImpl.class);
	
	private List<Matter> matters;
	
	public SubjectsImpl() {
		matters = new LinkedList<Matter>();
	}

	@Override
	public Iterator<Matter> iterator() {
		return matters.iterator();
	}

	@Override
	public Matter findMatter(String label) 
			throws WesearchException {
		for(Matter m : matters) {
			if(m.getLabel().equals(label)) {
				return m;
			}
		}
		logger.error("There isn't any matter with label " + label);
		throw new WesearchException("There isn't any matter with label " + label);
	}

	@Override
	public void addMatter(Matter m) {
		if(m == null) {
			throw new IllegalArgumentException("The parameter can not be null");
		}
		matters.add(m);
	}

	@Override
	public int size() {
		return matters.size();
	}

}
