package org.weso.wesearch.domain.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.weso.utils.WesearchException;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;

public class PropertiesImpl implements Properties {
	
	private static Logger logger = Logger.getLogger(PropertiesImpl.class);
	
	Set<Property> properties;
	
	public PropertiesImpl() {
		properties = new HashSet<Property>();
	}

	@Override
	public Property getPropertyByName(String propertyName) throws WesearchException {
		Iterator<Property> it = iterator();
		
		while(it.hasNext()) {
			Property prop = it.next();
			
			if(prop.getName().equalsIgnoreCase(propertyName) 
					|| prop.getName().contains(propertyName)) {
				return prop;
			}
		}
		logger.error("There isn't any property with name " + propertyName);
		throw new WesearchException("There isn't any property with name " + propertyName);
		
	}

	@Override
	public Iterator<Property> iterator() {
		return properties.iterator();
	}

	@Override
	public void addProperty(Property prop) {
		if(prop == null) {
			throw new IllegalArgumentException("The parameter can not be null");
		}
		properties.add(prop);
	}

}
