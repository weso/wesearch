package org.weso.wesearch;

import org.weso.utils.NotImplementedException;
import org.weso.wesearch.context.Context;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.Query;
import org.weso.wesearch.domain.ValueSelector;

/*
 * Implementation based on Jena
 */
public class JenaWesearch implements Wesearch {

	private Context ctx;

	JenaWesearch(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public Matters getMatters() {
		/*
		 * TODO: get classes from the ontology that we can search
		 */
		throw new NotImplementedException("getMatters");
	}

	@Override
	public Properties getProperties(Matter s) {
		/*
		 * Find class corresponding to s in the ontology
		 * Return properties the we can search from that class
		 */
		throw new NotImplementedException("getProperties");
	}

	@Override
	public ValueSelector getValueSelector(Matter s, Property p) {
		/*
		 * Get value selector from a given property and subject
		 */
		throw new NotImplementedException("getValueSelector");
	}

	@Override
	public Query createQuery(Matter s, Property p, ValueSelector v) {
		/*
		 * Create a query from a given set of values
		 */
		throw new NotImplementedException("createQuery");
	}

	@Override
	public Query combineQuery(Query q, Matter s, Property p, ValueSelector v) {
		throw new NotImplementedException("combineQuery");
	}

	@Override
	public String version() {
		return "0.1";
	}

}
