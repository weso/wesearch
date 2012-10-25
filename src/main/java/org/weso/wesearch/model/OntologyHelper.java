package org.weso.wesearch.model;

import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.impl.MatterImpl;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDFS;

public class OntologyHelper {
	
	/**
	 * This method create a matter from a uri and a jena model
	 * @param resource The uri of the resource
	 * @param model The model where is the resource
	 * @return The matter
	 */
	public static Matter createMatter(String resource, OntModel model) {
		String label = getLabel(resource, model);
		String comment = getComment(resource, model);
		return new MatterImpl(label, resource, comment);
	}
	
	/**
	 * This method create a matter from a jena resource
	 * @param res The resource to create the matter
	 * @return The matter
	 */
	public static Matter createMatter(Resource res) {
		String uri = res.getURI();
		String label = getLabel(res);
		String comment = getComment(res);
		
		return new MatterImpl(label, uri, comment);
	}
	
	/**
	 * This method obtains the property rdfs:label from a Jena resource
	 * @param res The resource to obtain the property rdfs:label
	 * @return The property rdfs:label
	 */
	public static String getLabel(Resource res) {
		Statement labelProp = res.getProperty(RDFS.label);
		if (labelProp != null && labelProp.getString().length() > 0) {
            return labelProp.getString();
        } else {
            return (res.getURI()!=null?res.getModel().getGraph()
            		.getPrefixMapping().shortForm(res.getURI()):"Label not available");
        }
	}
	
	/**
	 * This method obtains the property rdfs:comment from a Jena resource
	 * @param res The resource to obtain the property rdfs:comment
	 * @return The property rdfs:comment
	 */
	public static String getComment(Resource res) {
		Statement labelProp = res.getProperty(RDFS.comment);
		if (labelProp != null && labelProp.getString().length() > 0) {
            return labelProp.getString();
        } else {
            return (res.getURI()!=null?res.getModel().getGraph()
            		.getPrefixMapping().shortForm(res.getURI()):"Comment not available");
        }
	}
	
	/**
	 * This method obtains the property rdfs:label from a Jena resource
	 * @param resource The resource to obtain the property rdfs:label
	 * @param model The ontology model to obtain the property value
	 * @return The property rdfs:label
	 */
	public static String getLabel(String resource, OntModel model) {
		OntClass res = model.getOntClass(resource);
		if(res == null) {
			return "Label not avaible";
		}
		Statement labelProp = res.getProperty(RDFS.label);
		if(labelProp != null && labelProp.getString().length() > 0) {
			return labelProp.getString();
		}
		return (res.getURI() != null)?res.getModel().getGraph().getPrefixMapping()
				.shortForm(res.getURI()):"Label not avaible";
	}
	
	/**
	 * This method obtains the property rdfs:comment from a Jena resource
	 * @param resource The resource to obtain the property rdfs:comment
	 * @param model The ontology model to obtain the value
	 * @return The property rdfs:comment
	 */
	public static String getComment(String resource, OntModel model) {
		OntClass res = model.getOntClass(resource);
		if(res == null) {
			return "Comment not avaible";
		}
		Statement commentProp = res.getProperty(RDFS.comment);
		if(commentProp != null && commentProp.getString().length() > 0) {
			return commentProp.getString();
		}
		return (res.getURI()!=null)?res.getModel().getGraph().getPrefixMapping()
				.shortForm(res.getURI()):"Comment not avaible";
	}

	public static Property createPropertyByDomain(OntProperty property, String uri) {
		OntResource domain = property.getDomain();
		
		return null;
	}

}
