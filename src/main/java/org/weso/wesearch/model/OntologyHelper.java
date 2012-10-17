package org.weso.wesearch.model;

import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.impl.MatterImpl;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDFS;

public class OntologyHelper {
	
	public static Matter createMatter(Resource resource) {
		String uri = resource.getURI();
		String label = getLabel(resource);
		String comment = getComment(resource);
		return new MatterImpl(label, uri, comment);
	}
	
	/**
	 * This method obtains the property rdfs:label from a Jena resource
	 * @param resource The resource to obtain the property rdfs:label
	 * @return The property rdfs:label
	 */
	public static String getLabel(Resource resource) {
		Statement labelProp = resource.getProperty(RDFS.label);
		if(labelProp != null && labelProp.getString().length() > 0) {
			return labelProp.getString();
		}
		return (resource.getURI() != null)?resource.getModel().getGraph().getPrefixMapping()
				.shortForm(resource.getURI()):"Label not avaible";
	}
	
	/**
	 * This method obtains the property rdfs:comment from a Jena resource
	 * @param resource The resource to obtain the property rdfs:comment
	 * @return The property rdfs:comment
	 */
	public static String getComment(Resource resource) {
		Statement commentProp = resource.getProperty(RDFS.comment);
		if(commentProp != null) {
			return commentProp.getString();
		}
		return (resource.getURI()!=null)?resource.getModel().getGraph().getPrefixMapping()
				.shortForm(resource.getURI()):"Comment not avaible";
	}

}
