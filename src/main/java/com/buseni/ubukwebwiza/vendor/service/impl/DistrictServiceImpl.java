package com.buseni.ubukwebwiza.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.vendor.domain.District;
import com.buseni.ubukwebwiza.vendor.repository.DistrictRepo;
import com.buseni.ubukwebwiza.vendor.service.DistrictService;

@Service
@Transactional(readOnly=true)
public class DistrictServiceImpl implements DistrictService {


	private DistrictRepo  districtRepo;
	
	
	@Autowired
	public  DistrictServiceImpl(DistrictRepo  districtRepo) {
		this.districtRepo = districtRepo;
	}
	
	@Override
	public List<District> findByActiveFlag(int activeFlag) {
		return districtRepo.findByActiveFlag(activeFlag);
	}

	@Override
	public Page<District> findAll(Pageable page) {
		PageRequest pr = new PageRequest(page.getPageNumber()-1, page.getPageSize());
		return districtRepo.findAll(pr);
	}

}
