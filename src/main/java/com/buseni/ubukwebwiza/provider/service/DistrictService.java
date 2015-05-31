package com.buseni.ubukwebwiza.provider.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.provider.domain.District;

public interface DistrictService {
	/**
	 * 
	 * @param activeFlag
	 * @return
	 */
	List<District> findByEnabled(boolean enabled);
	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<District> findAll(Pageable page);
	/**
	 * 
	 * @param district
	 */
	void add(District district) throws BusinessException;
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
	District findOne(Integer id);

}
