package com.buseni.ubukwebwiza.vendor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.vendor.domain.WeddingService;

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
	List<WeddingService> findByActiveFlag(int activeFlag);

}
