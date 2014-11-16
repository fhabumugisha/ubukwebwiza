package com.buseni.ubukwebwiza.vendor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.vendor.domain.WeddingService;

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
	List<WeddingService> findAllByVendor(Integer idProvider);
	
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
