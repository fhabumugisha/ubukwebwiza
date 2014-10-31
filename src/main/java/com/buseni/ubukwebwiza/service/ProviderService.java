package com.buseni.ubukwebwiza.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.domain.Provider;
import com.buseni.ubukwebwiza.utils.ProviderSearch;

public interface ProviderService {
	
	/**
	 * 
	 * @param weddingService
	 */
	void create(Provider provider);
	
	/**
	 * 
	 * @param weddingService
	 * @return
	 */
	Provider update(Provider provider);
	/**
	 * 
	 * @param id
	 * @return
	 */
	Provider findById(Integer id);
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Provider> findAll(Pageable pageable) ;
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Provider> search(ProviderSearch providerSearch, Pageable pageable) ;
	
	void delete(Integer id);

}
