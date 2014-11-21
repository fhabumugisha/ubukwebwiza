package com.buseni.ubukwebwiza.vendor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.vendor.domain.CodeProvince;

public interface CodeProvinceService {
	/**
	 * 
	 * @param activeFlag
	 * @return
	 */
	List<CodeProvince> findByActiveFlag(int activeFlag);
	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<CodeProvince> findAll(Pageable page);

}
