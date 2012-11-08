package org.weso.wesearch;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.weso.utils.IndexerCreator;
import org.weso.utils.NotImplementedException;
import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.context.Context;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.Query;
import org.weso.wesearch.domain.ValueSelector;
import org.weso.wesearch.domain.impl.PropertiesImpl;
import org.weso.wesearch.domain.impl.SubjectsImpl;
import org.weso.wesearch.domain.impl.ValueSelectorImpl;
import org.weso.wesearch.model.OntologyHelper;

import weso.mediator.config.Configuration;
import weso.mediator.core.business.SuggestionException;
import weso.mediator.core.domain.Suggestion;
import weso.mediator.core.domain.lucene.IndexLucene;
import weso.mediator.facade.WESOMed;
import weso.mediator.factory.FacadeFactory;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/*
 * Implementation based on Jena
 */
public class JenaWesearch implements Wesearch {
	
	private static Logger logger = Logger.getLogger(JenaWesearch.class);

	private Context ctx;
	private WESOMed<IndexLucene> wesomed;

	@SuppressWarnings("unchecked")
	public JenaWesearch(Context ctx) throws WesearchException {
		this.ctx = ctx;
		wesomed = (WESOMed<IndexLucene>) FacadeFactory.getFacade();
		initializeWesomed();
	}

	@Override
	public Matters getMatters(String stem) throws WesearchException {
		try {
			if(stem.equals("")) {
				return createMatterFromResources(
						((OntModel)ctx.getOntologiesModel().getModel()).listClasses());
			}
			List<Suggestion> classes =
					wesomed.getSuggestions(stem, Configuration.getProperty("index_dir_classes"));
			return createMatterFromResourceId(classes.iterator());
		} catch (SuggestionException e) {
			throw new WesearchException(e.getMessage());
		} catch (OntoModelException e) {
			throw new WesearchException(e.getMessage());
		}
	}
	
	private Matters createMatterFromResources(ExtendedIterator<OntClass> it) {
		Matters matters = new SubjectsImpl();
		while(it.hasNext()) {
			OntClass clazz = it.next();
			if(clazz.getURI() != null) {
				matters.addMatter(OntologyHelper.createMatter(clazz));
			}
		}
		return matters;
	}
	
	private Matters createMatterFromResourceId(Iterator<Suggestion> it) throws OntoModelException {
		Matters matters = new SubjectsImpl();
		while(it.hasNext()) {
			Suggestion clazz = it.next();
			String uriClass = clazz.getResourceId();
			if(uriClass != null) {
				matters.addMatter(OntologyHelper.createMatter(uriClass, 
						(OntModel)ctx.getOntologiesModel().getModel()));
			}
		}
		return matters;
	}

	@Override
	public Properties getProperties(Matter s, String stem) throws WesearchException {
		try {	
			Properties allProperties = obtainAllPropertiesByMatter(s);
			if(stem.equals("")) {				
				return allProperties;
			}
		
			List<Suggestion> filteredProperties = 
					wesomed.getSuggestions(stem, Configuration.getProperty("index_dir_properties"));
			return matchingProperties(allProperties, filteredProperties);
		} catch (SuggestionException e) {
			throw new WesearchException(e.getMessage());
		} catch (OntoModelException e) {
			throw new WesearchException(e.getMessage());
		}
	}
	
	private Properties matchingProperties(Properties allProperties,
			List<Suggestion> filteredProperties) {
		Properties result = new PropertiesImpl();
		Iterator<Property> it = allProperties.iterator();
		while(it.hasNext()) {
			Property p = it.next();
			if(isPropertySought(filteredProperties, p)) {
				result.addProperty(p);
			}
		}
		return result;
	}

	private boolean isPropertySought(List<Suggestion> filteredProperties, Property p) {
		for(Suggestion sug : filteredProperties) {
			if(sug.getResourceId().equals(p.getUri())) {
				return true;
			}
		}
		return false;
	}

	private Properties obtainAllPropertiesByMatter(Matter matter) throws OntoModelException {
		Properties properties = new PropertiesImpl();
		OntModel model = (OntModel)ctx.getOntologiesModel().getModel();
		OntClass ontClass = model.getOntClass(matter.getUri());
		properties = OntologyHelper.obtainPropertiesByMatter(ontClass, 
				ontClass.listSuperClasses());
		
		return properties;
	}

	@Override
	public ValueSelector getValueSelector(Matter s, Property p, String stem) 
			throws WesearchException {
		try {
			OntModel ontModel = (OntModel)ctx.getOntologiesModel().getModel();
			OntProperty ontProperty = ontModel.getOntProperty(p.getUri());
			String type = OntologyHelper.extractPropertyRange(ontProperty);
			return new ValueSelectorImpl(type);
		} catch (OntoModelException e) {
			throw new WesearchException(e.getMessage());
		}
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
	
	private void initializeWesomed() throws WesearchException {
		try {
			indexClasses();
			indexProperties();
		} catch (IOException e) {
			logger.error("Cannot read files that contains queries: " + e.getMessage());
			throw new WesearchException(e.getMessage());
		} catch (SuggestionException e) {
			throw new WesearchException(e.getMessage());
		}
	}

	private void indexProperties() throws IOException, SuggestionException {
		List<IndexLucene> indexers = IndexerCreator.createIndexerForProperties();
		
		String query = Configuration.getContentsFromProperty("query_properties");
		
		wesomed.indexEntities(
				Configuration.getProperty("index_dir_properties"), query, indexers);
	}
	
	private void indexClasses() throws IOException, SuggestionException {
		List<IndexLucene> indexers = IndexerCreator.createIndexerForClasses();
		
		String query = Configuration.getContentsFromProperty("query_classes");
		
		wesomed.indexEntities(
				Configuration.getProperty("index_dir_classes"), query, indexers);
	}

}
