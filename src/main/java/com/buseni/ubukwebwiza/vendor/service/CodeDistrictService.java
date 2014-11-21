package com.buseni.ubukwebwiza.vendor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.vendor.domain.CodeDistrict;

public interface CodeDistrictService {
	/**
	 * 
	 * @param activeFlag
	 * @return
	 */
	List<CodeDistrict> findByActiveFlag(int activeFlag);
	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<CodeDistrict> findAll(Pageable page);

}
