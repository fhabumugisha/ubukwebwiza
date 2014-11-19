package com.buseni.ubukwebwiza.vendor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.vendor.domain.CodeTypeWeddingService;

public interface CodeWeddingServiceManager {
	
	List<CodeTypeWeddingService> findAll();
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<CodeTypeWeddingService> findAll(Pageable pageable);
	
	/**
	 * 
	 * @param activeFlag
	 * @return
	 */
	List<CodeTypeWeddingService> findByActiveFlag(int activeFlag);

}