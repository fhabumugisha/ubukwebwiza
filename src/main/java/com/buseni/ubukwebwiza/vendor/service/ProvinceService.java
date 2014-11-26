package com.buseni.ubukwebwiza.vendor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.vendor.domain.Province;

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
	 */
	void add(Province province);
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
