package com.buseni.ubukwebwiza.administrator.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.buseni.ubukwebwiza.administrator.domain.Administrator;
import com.buseni.ubukwebwiza.exceptions.ServiceLayerException;

public interface AdministratorService extends UserDetailsService {
	
	/**
	 * 
	 * @param weddingService
	 */
	void create(Administrator administrator) throws ServiceLayerException;
	
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
