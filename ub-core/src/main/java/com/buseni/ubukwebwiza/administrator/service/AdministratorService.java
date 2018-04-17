package com.buseni.ubukwebwiza.administrator.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.administrator.domain.Administrator;
import com.buseni.ubukwebwiza.administrator.domain.AdministratorDTO;
import com.buseni.ubukwebwiza.exceptions.BusinessException;

public interface AdministratorService {

	/**
	 * 
	 * @param weddingService
	 */
	void create(Administrator administrator) throws BusinessException;
	/**
	 * 
	 * @param administratorDTO
	 * @return 
	 * @throws BusinessException
	 */
	Administrator create(AdministratorDTO administratorDTO) throws BusinessException;

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

	Administrator findByEmail(String email);
	/**
	 * Update the administrator
	 * @param administrator
	 * @return
	 * @throws BusinessException 
	 */
	Administrator update(AdministratorDTO administrator) throws BusinessException;

	

}
