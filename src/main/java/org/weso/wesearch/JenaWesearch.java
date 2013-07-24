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

/**
 * This class is an implementation of Wesearch based on Jena
 * @author Ignacio Fuertes Bernardo
 *
 */
public class JenaWesearch implements Wesearch {
	
	private static Logger logger = Logger.getLogger(JenaWesearch.class);
	
	/**
	 * The object that save the context of the wesarch
	 */
	private Context ctx;
	
	/**
	 * An instance of wesomed that allow text search over the ontologies
	 */
	private WESOMed<IndexLucene> wesomed;

	@SuppressWarnings("unchecked")
	public JenaWesearch(Context ctx) throws WesearchException {
		this.ctx = ctx;
		wesomed = (WESOMed<IndexLucene>) FacadeFactory.getFacade();
		initializeWesomed();
	}

	@Override
	public Matters getMatters(String stem) throws WesearchException {
		validateParams(stem);
		try {
			if(isValidStem(stem)) {
				return createMatterFromResources(
						((OntModel)ctx.getOntologiesModel().getModel())
						.listClasses());
			}
			List<Suggestion> classes =
					wesomed.getSuggestions(stem, 
							Configuration.getProperty("index_dir_classes"));
			return createMatterFromResourceId(classes.iterator());
		} catch (SuggestionException e) {
			throw new WesearchException(e.getMessage());
		} catch (OntoModelException e) {
			throw new WesearchException(e.getMessage());
		}
	}
	
	/**
	 * This method has to check if there is any object in the array that is null
	 * @param params An array of objects that have to check
	 * @throws WesearchException This exception is thrown if there is a null
	 * object in the array
	 */
	private void validateParams(Object... params) throws WesearchException {
		for(int i = 0; i < params.length; i++) {
			if(params[i] == null) {
				logger.error("There is a param in the request null");
				throw new WesearchException("There is a param in the request " +
						"null");
			}
		}
	}

	/**
	 * This method has to valid if the string is valid to obtain a suggestion
	 * @param stem The string to valid
	 * @return A boolean indicates if the string is valid
	 */
	private boolean isValidStem(String stem) {
		return stem.equals("");
	}
	
	/**
	 * This method has to create a collection of subjects from an iterator of
	 * OntClass objects
	 * @param it The iterator that contains the source objects to convert
	 * @return A collection of subjects
	 */
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
	
	/**
	 * This method has to create a collection of subjects from a list of
	 * suggestions obtained by wesomed
	 * @param it An iterator over the list of suggestions
	 * @return A collection of subjects
	 * @throws OntoModelException This exception is thrown if there are 
	 * some problem creating the subjects 
	 */
	private Matters createMatterFromResourceId(Iterator<Suggestion> it) 
			throws OntoModelException {
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
	public Properties getProperties(Matter s, String stem) 
			throws WesearchException {
		validateParams(s, stem);
		try {	
			Properties allProperties = obtainAllPropertiesByMatter(s);
			if(isValidStem(stem)) {				
				return allProperties;
			}
		
			List<Suggestion> filteredProperties = 
					wesomed.getSuggestions(stem, 
							Configuration.getProperty("index_dir_properties"));
			return matchingProperties(allProperties, filteredProperties);
		} catch (SuggestionException e) {
			throw new WesearchException(e.getMessage());
		} catch (OntoModelException e) {
			throw new WesearchException(e.getMessage());
		}
	}
	
	/**
	 * This method has to find the properties that are in both lists
	 * @param allProperties All properties of one class of the ontology
	 * @param filteredProperties All properties that are result of text search
	 * @return All properties that are in both lists
	 */
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

	/**
	 * This method returns if one property is in a list of properties
	 * @param filteredProperties List of properties which are looking for a 
	 * particular property
	 * @param p Property that method has to find in the list
	 * @return boolean that indicates if the property is in the list
	 */ 
	private boolean isPropertySought(List<Suggestion> filteredProperties, 
			Property p) {
		for(Suggestion sug : filteredProperties) {
			if(sug.getResourceId().equals(p.getUri())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method has to obtain all properties of one determinate class from 
	 * the ontologies
	 * @param matter A class of an ontology to obtain all it's properties
	 * @return A collection of properties
	 * @throws OntoModelException This exception is thrown if there are any 
	 * problem obtaining the properties
	 */
	private Properties obtainAllPropertiesByMatter(Matter matter) 
			throws OntoModelException {
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
		validateParams(s, p);
		try {
			OntModel ontModel = (OntModel)ctx.getOntologiesModel().getModel();
			OntProperty ontProperty = ontModel.getOntProperty(p.getUri());
			String type = OntologyHelper.extractPropertyRange(ontProperty);
			if(type.equals(ValueSelector.OBJECT)) {
				ValueSelector selector = new ValueSelectorImpl(type);
				Matters matters = 
						OntologyHelper.createRangeMatters(
								ontProperty.listRange());
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
		validateParams(v);
		try {
			Query query = new SPARQLQuery();
			addTypeClauseToQuery("res", query.getNextVarName(), query, s);
			String object = query.getNextVarName();
			query.addClause(SPARQLQueryBuilder.getPropertyClause("res", p,
					object));
			addFilterToQuery(v, query, object);
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

	/**
	 * This method has to add a filter clause to the query that receives as a
	 * parameter
	 * @param v The value selector to filter in the query
	 * @param query The query to adds the filter
	 * @param object The name of the variable that has to filter in the query
	 * @throws QueryBuilderException This exception is thrown if there is a 
	 * problem generating the filter clause
	 */
	private void addFilterToQuery(ValueSelector v, Query query, String object)
			throws QueryBuilderException {
		if(!v.getType().equals(ValueSelector.OBJECT)) {
			query.addFilter(object, SPARQLQueryBuilder.getFilter(
					object, v));
		} else {
			query.addFilter(object, null);
		}
	}

	/**
	 * This method adds a type filter to a given query.
	 * @param s The type that must have the variable to filter
	 * @param query Query to add the type filter
	 * @param varName Name of the variable to filter
	 * @throws OntoModelException This exception is thrown if there are some 
	 * problem during extraction of information over the ontology
	 * @throws QueryBuilderException This exception is thrown if there are some
	 * problem generating the query
	 */
	private void addTypeFilter(Matter s, Query query, String varName)
			throws OntoModelException, QueryBuilderException {
		SPARQLFilters filters = SPARQLQueryBuilder.getClassFilter(varName, 
				s, ctx.getOntologiesModel());
		query.addFilters(varName, filters);
	}
	
	/**
	 * This method has to add a type clause to the query that receives as a 
	 * parameter
	 * @param subject The name of the variable that has to be an instance
	 * of a determinate class
	 * @param object The name of the variable that has to be filtered
	 * @param query The query to add the filter
	 * @param matter The matter to filter
	 * @throws OntoModelException This exception is thrown if there is a problem
	 * generating the filter clause
	 * @throws QueryBuilderException This exception is thrown if there is a 
	 * problem generating the filter clause
	 */
	private void addTypeClauseToQuery(String subject, String object, 
			Query query, Matter matter) throws OntoModelException, 
			QueryBuilderException {
		query.addClause(SPARQLQueryBuilder.getTypeClause(subject, object));
		
		addTypeFilter(matter, query, object);
	}

	@Override
	public Query combineQuery(Query q, Matter s, Property p, ValueSelector v) 
			throws WesearchException {
		validateParams(q, s, p, v);
		try {
			String subject = "";
			String object = q.getNextVarName();
			if(q.isPropertyForResult()) {
				subject = "res";
			} else {
				//Add type clausules to query
				subject = q.obtainAuxiliarVarName();
				addTypeClauseToQuery(subject, object, q, 
						s);
				object = q.getNextVarName();
			}
			
			//Add property clause to query
			q.addClause(SPARQLQueryBuilder.getPropertyClause(subject, 
					p, object));
			addFilterToQuery(v, q, object);
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
	
	/**
	 * This method has to initialized wesomed
	 * @throws WesearchException This exception is thrown if there are 
	 * some problem reading the file that contains queries to extract 
	 * information to index
	 */
	private void initializeWesomed() throws WesearchException {
		try {
			indexClasses();
			indexProperties();
		} catch (IOException e) {
			logger.error("Cannot read files that contains queries: " + 
					e.getMessage());
			throw new WesearchException(e.getMessage());
		} catch (SuggestionException e) {
			throw new WesearchException(e.getMessage());
		}
	}

	/**
	 * This method has to index all properties of the ontologies in wesomed 
	 * to allow text search over them.
	 * @throws IOException This exception is thrown if there are some problems 
	 * reading the file that contains the query to extract properties.
	 * @throws SuggestionException This exception is thrown if there are some 
	 * problems indexing properties in wesomed
	 */
	private void indexProperties() throws IOException, SuggestionException {
		List<IndexLucene> indexers = 
				IndexerCreator.createIndexerForProperties();
		
		String query = 
				Configuration.getContentsFromProperty("query_properties");
		
		wesomed.indexEntities(
				Configuration.getProperty("index_dir_properties"), query, 
					indexers);
	}
	
	/**
	 * This method has to index all classes of the ontologies in wesomed to 
	 * allow text search over them.
	 * @throws IOException This exception is thrown if there are some problems 
	 * reading the file that contains the query to extract classes.
	 * @throws SuggestionException This exception is throw if there are some 
	 * problems indexing classes in wesomed.
	 */
	private void indexClasses() throws IOException, SuggestionException {
		List<IndexLucene> indexers = IndexerCreator.createIndexerForClasses();
		
		String query = Configuration.getContentsFromProperty("query_classes");
		
		wesomed.indexEntities(
				Configuration.getProperty("index_dir_classes"), query, 
				indexers);
	}

}
