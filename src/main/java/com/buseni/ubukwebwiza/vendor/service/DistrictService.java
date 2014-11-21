package com.buseni.ubukwebwiza.vendor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.vendor.domain.District;

public interface DistrictService {
	/**
	 * 
	 * @param activeFlag
	 * @return
	 */
	List<District> findByActiveFlag(int activeFlag);
	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<District> findAll(Pageable page);

}
