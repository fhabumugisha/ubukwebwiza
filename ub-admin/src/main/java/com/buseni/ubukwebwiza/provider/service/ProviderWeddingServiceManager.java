package com.buseni.ubukwebwiza.provider.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.provider.domain.ProviderWeddingService;

public interface ProviderWeddingServiceManager {
	/**
	 * 
	 * @param weddingService
	 */
	void create(ProviderWeddingService providerWeddingService) throws BusinessException;
	
	/**
	 * 
	 * @param weddingService
	 * @return
	 */
	ProviderWeddingService update(ProviderWeddingService providerWeddingService);
	/**
	 * 
	 * @param id
	 * @return
	 */
	ProviderWeddingService findById(Integer id);
	/**
	 * 
	 * @param idProvider
	 * @return
	 */
	List<ProviderWeddingService> findAllByProvider(Integer idProvider);
	/**
	 * 
	 * @param idWeddingService
	 * @return
	 */
	List<ProviderWeddingService> findAllByWeddingService(Integer idWeddingService);
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<ProviderWeddingService> findAll(Pageable pageable) ;
	
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);
}
