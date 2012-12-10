package org.weso.wesearch;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.weso.utils.IndexerCreator;
import org.weso.utils.OntoModelException;
import org.weso.utils.QueryBuilderException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.context.Context;
import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.Query;
import org.weso.wesearch.domain.SPARQLQueryBuilder;
import org.weso.wesearch.domain.ValueSelector;
import org.weso.wesearch.domain.impl.PropertiesImpl;
import org.weso.wesearch.domain.impl.SPARQLQuery;
import org.weso.wesearch.domain.impl.SubjectsImpl;
import org.weso.wesearch.domain.impl.ValueSelectorImpl;
import org.weso.wesearch.domain.impl.filters.SPARQLFilters;
import org.weso.wesearch.domain.impl.values.ObjectValue;
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
	public ValueSelector getValueSelector(Matter s, Property p) 
			throws WesearchException {
		if(p == null) {
			logger.error("Property cannot be null");
			throw new WesearchException("Property cannot be null");
		}
		try {
			OntModel ontModel = (OntModel)ctx.getOntologiesModel().getModel();
			OntProperty ontProperty = ontModel.getOntProperty(p.getUri());
			String type = OntologyHelper.extractPropertyRange(ontProperty);
			if(type.equals(ValueSelector.OBJECT)) {
				ValueSelector selector = new ValueSelectorImpl(type);
				Matters matters = 
						OntologyHelper.createRangeMatters(ontProperty.listRange());
				selector.setValue(new ObjectValue(matters));
				return selector;
			}
			return new ValueSelectorImpl(type);
		} catch (OntoModelException e) {
			throw new WesearchException(e.getMessage());
		}
	}
	
	@Override
	public Query createQuery(Matter s, Property p, ValueSelector v) 
			throws WesearchException {
		try {
			Query query = new SPARQLQuery();
			String object = query.getNextVarName();
			query.addClause(SPARQLQueryBuilder.getTypeClause("res", 
					object));
			addTypeFilter(s, query, object);
			object = query.getNextVarName();
			query.addClause(SPARQLQueryBuilder.getPropertyClause("res", p,
					object));
			if(!v.getType().equals(ValueSelector.OBJECT)) {
				query.addFilter(object, SPARQLQueryBuilder.getFilter(
						object, v));
			} else {
				query.addFilter(object, null);
			}
			return query;
		}catch(IOException e) {
			logger.error("Cannot read sparql queries variables");
			throw new WesearchException("Cannot read sparql queries variables");
		}catch(QueryBuilderException e) {
			throw new WesearchException(e.getMessage());
		} catch (OntoModelException e) {
			throw new WesearchException(e.getMessage());
		}
	}

	private void addTypeFilter(Matter s, Query query, String varName)
			throws OntoModelException, QueryBuilderException {
		SPARQLFilters filters = SPARQLQueryBuilder.getClassFilter(varName, 
				s, ctx.getOntologiesModel());
		query.addFilters(varName, filters);
	}

	@Override
	public Query combineQuery(Query q, Matter s, Property p, ValueSelector v) 
			throws WesearchException {
		try {
			String subject = "";
			String object = q.getNextVarName();
			if(q.isPropertyForResult()) {
				subject = "res";
			} else {
				//Add type clausules to query
				subject = q.obtainAuxiliarVarName();
				q.addClause(SPARQLQueryBuilder.getTypeClause(subject, object));
				addTypeFilter(s, q, object);
				object = q.getNextVarName();
			}
			
			//Add property clause to query
			q.addClause(SPARQLQueryBuilder.getPropertyClause(subject, 
					p, object));
			if(!v.getType().equals(ValueSelector.OBJECT)) {
				q.addFilter(object, SPARQLQueryBuilder.getFilter(
						object, v));
			} else {
				q.addFilter(object, null);
			}
		} catch (QueryBuilderException e) {
			throw new WesearchException(e.getMessage());
		} catch (OntoModelException e) {
			throw new WesearchException(e.getMessage());
		}
		return q;
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
