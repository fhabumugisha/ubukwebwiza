package com.buseni.ubukwebwiza.provider.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import com.buseni.ubukwebwiza.provider.domain.Provider;

@Repository
public class FullTextSearch {

	
	@PersistenceContext
	private EntityManager em;
	 public List<Provider> fullTextSearch(String searchTerm){
System.out.println("In full text search ...");
		 FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(em);
		 QueryBuilder tweetQb = fullTextEm.getSearchFactory().buildQueryBuilder().forEntity(Provider.class).get();
		 Query fullTextQuery = tweetQb.keyword().onFields("businessName","aboutme").matching(searchTerm).createQuery();
		 List<Provider> results = fullTextEm.createFullTextQuery(fullTextQuery, Provider.class).getResultList();
		
		 return  results;
	 }
	 
	 public void buildIndex() {
		 FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
		 try {
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	
}
