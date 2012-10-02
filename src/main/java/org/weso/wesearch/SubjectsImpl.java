package org.weso.wesearch;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.weso.utils.WesearchException;

public class SubjectsImpl implements Matters {
	
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
			if(m.label().equals(label)) {
				return m;
			}
		}
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
