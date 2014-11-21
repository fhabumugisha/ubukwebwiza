package com.buseni.ubukwebwiza.vendor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.vendor.domain.Vendor;
import com.buseni.ubukwebwiza.vendor.utils.VendorSearch;

public interface VendorService {
	
	/**
	 * 
	 * @param weddingService
	 */
	void create(Vendor vendor);
	
	/**
	 * 
	 * @param weddingService
	 * @return
	 */
	Vendor update(Vendor vendor);
	/**
	 * 
	 * @param id
	 * @return
	 */
	Vendor findById(Integer id);
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Vendor> findAll(Pageable pageable) ;
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Vendor> findByActiveFlag(int activeFlag, Pageable pageable) ;
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Vendor> search(VendorSearch vendorSearch, Pageable pageable) ;
	
	void delete(Integer id);

	/**
	 * 
	 * @return
	 */
	List<Vendor> getFeaturedVendors();

}
