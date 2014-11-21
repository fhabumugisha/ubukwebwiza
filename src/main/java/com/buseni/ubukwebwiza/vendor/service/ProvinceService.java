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
	List<Province> findByActiveFlag(int activeFlag);
	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Province> findAll(Pageable page);

}
