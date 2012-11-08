package org.weso.utils;

import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.TermVector;

import weso.mediator.core.domain.lucene.IndexLucene;

public class IndexerCreator {
	
	public static List<IndexLucene> createIndexerForClasses() {
		List<IndexLucene> indexers = new LinkedList<IndexLucene>();
		
		indexers.add(new IndexLucene("label", "label", Index.ANALYZED, TermVector.YES, true));
		indexers.add(new IndexLucene("comment", "comment", Index.ANALYZED, TermVector.YES, true));
		
		indexers.add(0, new IndexLucene("class", "class", Index.ANALYZED, TermVector.YES, true));
		return indexers;
	}
	
	public static List<IndexLucene> createIndexerForProperties() {
		List<IndexLucene> indexers = new LinkedList<IndexLucene>();
		
		indexers.add(new IndexLucene("label", "label", Index.ANALYZED, TermVector.YES, true));
		indexers.add(new IndexLucene("comment", "comment", Index.ANALYZED, TermVector.YES, true));
		
		indexers.add(0, new IndexLucene("prop", "prop", Index.ANALYZED, TermVector.YES, true));
		return indexers;
	}

}
