package com.buseni.ubukwebwiza.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.core.domain.CodeWeddingService;

public interface CodeWeddingServiceManager {
	
	List<CodeWeddingService> findAll();
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<CodeWeddingService> findAll(Pageable pageable);
	
	/**
	 * 
	 * @param activeFlag
	 * @return
	 */
	List<CodeWeddingService> findByActiveFlag(int activeFlag);

}
