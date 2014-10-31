package com.buseni.ubukwebwiza.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.domain.Administrator;

public interface AdministratorService {
	
	/**
	 * 
	 * @param weddingService
	 */
	void create(Administrator administrator);
	
	/**
	 * 
	 * @param weddingService
	 * @return
	 */
	Administrator update(Administrator administrator);
	/**
	 * 
	 * @param id
	 * @return
	 */
	Administrator findById(Integer id);
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Administrator> findAll(Pageable pageable) ;
	
	void delete(Integer id);

}
