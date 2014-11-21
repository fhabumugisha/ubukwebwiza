package com.buseni.ubukwebwiza.vendor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.vendor.domain.VendorWeddingService;

public interface VendorWeddingServiceManager {
	/**
	 * 
	 * @param weddingService
	 */
	void create(VendorWeddingService vendorWeddingService);
	
	/**
	 * 
	 * @param weddingService
	 * @return
	 */
	VendorWeddingService update(VendorWeddingService vendorWeddingService);
	/**
	 * 
	 * @param id
	 * @return
	 */
	VendorWeddingService findById(Integer id);
	/**
	 * 
	 * @param idVendor
	 * @return
	 */
	List<VendorWeddingService> findAllByVendor(Integer idVendor);
	/**
	 * 
	 * @param idWeddingService
	 * @return
	 */
	List<VendorWeddingService> findAllByWeddingService(Integer idWeddingService);
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<VendorWeddingService> findAll(Pageable pageable) ;
	
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);
}
