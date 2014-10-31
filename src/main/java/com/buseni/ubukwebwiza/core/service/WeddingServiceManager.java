package com.buseni.ubukwebwiza.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.core.domain.WeddingService;

public interface WeddingServiceManager {
	/**
	 * 
	 * @param weddingService
	 */
	void create(WeddingService weddingService);
	
	/**
	 * 
	 * @param weddingService
	 * @return
	 */
	WeddingService update(WeddingService weddingService);
	/**
	 * 
	 * @param id
	 * @return
	 */
	WeddingService findById(Integer id);
	/**
	 * 
	 * @param idProvider
	 * @return
	 */
	List<WeddingService> findAllByProvider(Integer idProvider);
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<WeddingService> findAll(Pageable pageable) ;
	
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);
}
