package com.buseni.ubukwebwiza.provider.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.provider.domain.Province;

public interface ProvinceService {
	/**
	 * 
	 * @param activeFlag
	 * @return
	 */
	List<Province> findByEnabled(boolean enabled);
	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Province> findAll(Pageable page);
	/**
	 * 
	 * @param province
	 * @throws BusinessException 
	 */
	void add(Province province) throws BusinessException ;
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Province findOne(Integer id);
	/**
	 * 
	 * @param province
	 */
	void update(Province province);

}
