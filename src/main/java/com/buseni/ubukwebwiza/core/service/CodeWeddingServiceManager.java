package com.buseni.ubukwebwiza.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.core.domain.CodeTypeWeddingService;

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
