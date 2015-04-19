package com.buseni.ubukwebwiza.provider.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.exceptions.ServiceLayerException;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;

public interface WeddingServiceManager {
	
	List<WeddingService> findAll();
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<WeddingService> findAll(Pageable pageable);
	
	/**
	 * 
	 * @param activeFlag
	 * @return
	 */
	List<WeddingService> findByEnabled(boolean enabled);
	/**
	 * 
	 * @param weddingService
	 */
	void add(WeddingService weddingService) throws ServiceLayerException;
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);
	/**
	 * 
	 * @param id
	 * @return
	 */
	WeddingService findOne(Integer id);

}
