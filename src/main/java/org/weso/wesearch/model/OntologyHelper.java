package org.weso.wesearch.model;

import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.impl.JenaPropertyImpl;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.PropertiesImpl;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
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
		if(res instanceof OntResource) {
			return getLabelFromOntResource(res);
		} else {
			 return getLabelFromResource(res);
		}
		
	}

	/**
	 * This method obtains the property rdfs:label from a Jena resource
	 * @param res The resource to obtain the property rdfs:label
	 * @return The property rdfs:label
	 */
	private static String getLabelFromResource(Resource res) {
		Statement labelProp = res.getProperty(RDFS.label);
		 if (labelProp != null && labelProp.getString().length() > 0) {
		     return labelProp.getString();
		 } else {
		     return (res.getURI()!=null?res.getModel().getGraph()
		                 .getPrefixMapping().shortForm(res.getURI()):"Label not available");
		 }
	}

	/**
	 * This method ontains the property rdfs:label from a jena OntResource
	 * using xml:lang attribute.
	 * @param res The resource to obtain the property rdfs:label
	 * @return The value of the property rdfs:label
	 */
	private static String getLabelFromOntResource(Resource res) {
		OntResource resource = ((OntResource)res);
		if(resource.getLabel("es") != null) {
			return resource.getLabel("es");
		} else if (resource.getLabel("en") != null) {
			return resource.getLabel("en");
		} else if (resource.getLabel(null) != null) {
			return resource.getLabel(null);
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
		if(res instanceof OntResource) {
			return getCommentFromOntResource(res);
		} else {
			return getCommentFromResource(res);
		}		
	}

	/**
	 * This method obtains the property rdfs:comment from a Jena resource
	 * @param res The resource to obtain the property rdfs:comment
	 * @return The property rdfs:comment
	 */
	private static String getCommentFromResource(Resource res) {
		Statement labelProp = res.getProperty(RDFS.comment);
		if (labelProp != null && labelProp.getString().length() > 0) {
		    return labelProp.getString();
		} else {
		    return (res.getURI()!=null?res.getModel().getGraph()
		    		.getPrefixMapping().shortForm(res.getURI()):"Comment not available");
		}
	}

	/**
	 * This method ontains the property rdfs:comment from a jena OntResource
	 * using xml:lang attribute.
	 * @param res The resource to obtain the property rdfs:comment
	 * @return The value of the property rdfs:comment
	 */
	private static String getCommentFromOntResource(Resource res) {
		OntResource ontResource = (OntResource)res;
		if(ontResource.getComment("es") != null) {
			return ontResource.getComment("es");
		} else if (ontResource.getComment("en") != null) {
			return ontResource.getComment("en");
		} else if (ontResource.getComment(null) != null) {
			return ontResource.getComment(null);
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
		/*Statement labelProp = res.getProperty(RDFS.label);
		if(labelProp != null && labelProp.getString().length() > 0) {
			return labelProp.getString();
		}
		return (res.getURI() != null)?res.getModel().getGraph().getPrefixMapping()
				.shortForm(res.getURI()):"Label not avaible";*/
		return getLabel(res);
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
		/*Statement commentProp = res.getProperty(RDFS.comment);
		if(commentProp != null && commentProp.getString().length() > 0) {
			return commentProp.getString();
		}
		return (res.getURI()!=null)?res.getModel().getGraph().getPrefixMapping()
				.shortForm(res.getURI()):"Comment not avaible";*/
		return getComment(res);
	}

	public static Properties obtainPropertiesByMatter(OntClass ontClass,
			ExtendedIterator<OntClass> superClasses) {
		Properties properties = new PropertiesImpl();
		extractPropertiesFromOntClass(properties, ontClass);
		while(superClasses.hasNext()) {
			OntClass auxOntClass = superClasses.next();
			extractPropertiesFromOntClass(properties, auxOntClass);
		}
		
		return properties;
	}

	public static void extractPropertiesFromOntClass(Properties properties,
			OntClass ontClass) {
		ExtendedIterator<OntProperty> ontProperties = ontClass.listDeclaredProperties();
		while(ontProperties.hasNext()) {
			OntProperty ontProp = ontProperties.next();
			properties.addProperty(createProperty(ontProp));
		}
	}
	
	public static Property createProperty(Resource res) {
		String uri = res.getURI();
		String label = getLabel(res);
		String description = getComment(res);
		return new JenaPropertyImpl(uri, label, description);
	}

	public static String extractPropertyRange(OntProperty ontProperty) {
		OntResource range = null;
		if(ontProperty.isDatatypeProperty()) {
			range = ontProperty.asDatatypeProperty().getRange();
			if(range != null) {
				return range.getURI();
			}
		} else if(ontProperty.isObjectProperty()) {
			range = ontProperty.asObjectProperty().getRange();
			if(range != null) {
				return range.getURI();
			}
		}
		return "Range not avaible";
	}

}
