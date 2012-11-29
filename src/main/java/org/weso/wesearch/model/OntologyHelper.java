package org.weso.wesearch.model;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.weso.utils.OntoModelException;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.ValueSelector;
import org.weso.wesearch.domain.impl.JenaPropertyImpl;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.PropertiesImpl;
import org.weso.wesearch.domain.impl.SubjectsImpl;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDFS;

public class OntologyHelper {
	
	private static Logger logger = Logger.getLogger(OntologyHelper.class);
	
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
			return "Label not available";
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

	@SuppressWarnings("rawtypes")
	public static String extractPropertyRange(OntProperty ontProperty) {
		if(ontProperty != null) {
			ExtendedIterator ranges = null;
			if(ontProperty.isDatatypeProperty()) {
				ranges = ontProperty.asDatatypeProperty().listRange();
				if(ranges != null) {
					return extractValueSelectorFromList(ranges);
				}
			} else if(ontProperty.isObjectProperty()) {
				return ValueSelector.OBJECT;
			}
		}		
		return ValueSelector.UNDEFINED;
	}
	
	@SuppressWarnings("rawtypes")
	private static String extractValueSelectorFromList(
			ExtendedIterator it) {
		String result = null;
		while(it.hasNext()) {
			OntResource ont = (OntResource)it.next();
			result = extractValueSelector(ont);
			if(result != null && !result.equals(ValueSelector.UNDEFINED)) {
				return result;
			}
		}
		return (result==null)?ValueSelector.UNDEFINED:result;
	}

	private static String extractValueSelector(OntResource range) {
		if(range == null) {
			return ValueSelector.UNDEFINED;
		}
		String uriRange = range.getURI();
		if(uriRange.equals("http://www.w3.org/2001/XMLSchema#date") || 
				uriRange.equals("http://www.w3.org/2001/XMLSchema#dateTime") || 
				uriRange.equals("http://www.w3.org/2001/XMLSchema#time")) {
			return ValueSelector.DATE;
		} else if (uriRange.equals("http://www.w3.org/2001/XMLSchema#decimal") || 
				uriRange.equals("http://www.w3.org/2001/XMLSchema#float") || 
				uriRange.equals("http://www.w3.org/2001/XMLSchema#double") || 
				uriRange.equals("http://www.w3.org/2001/XMLSchema#long") ||
				uriRange.equals("http://www.w3.org/2001/XMLSchema#integer") || 
				uriRange.equals("http://www.w3.org/2001/XMLSchema#int")) {
			return ValueSelector.NUMERIC;
		} else if (uriRange.equals("http://www.w3.org/2001/XMLSchema#string") || 
				uriRange.equals("http://www.w3.org/2000/01/rdf-schema#Literal")) {
			return ValueSelector.TEXT;
		} 
		return ValueSelector.UNDEFINED;
	}

	public static Matters createRangeMatters(
			ExtendedIterator<? extends OntResource> listRange) 
					throws OntoModelException {
		if(listRange == null) {
			logger.error("The list of ranges cannot be null");
			throw new OntoModelException("The list of ranges cannot be null");
		}
		Matters matters = new SubjectsImpl();
		while(listRange.hasNext()) {
			OntResource res = listRange.next();
			Matter m = new MatterImpl(getLabel(res), res.getURI(), 
					getComment(res));
			matters.addMatter(m);
		}
		return matters;
	}
	
	public static List<String> extractSubclasses(Matter matter, OntModel model) {
		List<String> result = new LinkedList<String>();
		OntClass ontClass = model.getOntClass(matter.getUri());
		if(ontClass != null) {
			ExtendedIterator<OntClass> it = ontClass.listSubClasses();
			while(it.hasNext()) {
				OntClass aux = it.next();
				result.add(aux.getURI());
			}
		}
		return result;
	}

}
