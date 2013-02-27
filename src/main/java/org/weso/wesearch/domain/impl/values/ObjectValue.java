package org.weso.wesearch.domain.impl.values;

import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Value;

/**
 * It's an implementation of abstract class Value. Represents an object value of
 * a property. An object value is a collection of classes that can be the object
 * of a sparql property
 * @author Ignacio Fuertes Bernardo
 *
 */
public class ObjectValue extends Value<Matters> {

	/**
	 * The collection of classes that can be the object of a property
	 */
	private Matters value;
	
	/**
	 * It's the constructor of the class.
	 * @param m The collection of class that are the value of the object
	 */
	public ObjectValue(Matters m) {
		this.value = m;
	}
	
	@Override
	public Matters getValue() {
		return value;
	}

}
