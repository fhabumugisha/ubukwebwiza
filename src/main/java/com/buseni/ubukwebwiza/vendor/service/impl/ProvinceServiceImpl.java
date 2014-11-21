package com.buseni.ubukwebwiza.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.vendor.domain.Province;
import com.buseni.ubukwebwiza.vendor.repository.ProvinceRepo;
import com.buseni.ubukwebwiza.vendor.service.ProvinceService;

@Service
@Transactional(readOnly=true)
public class ProvinceServiceImpl implements ProvinceService{

	private ProvinceRepo provinceRepo;
	
	
	@Autowired
	public  ProvinceServiceImpl(ProvinceRepo provinceRepo) {
		this.provinceRepo = provinceRepo;
	}
	
	@Override
	public List<Province> findByActiveFlag(int activeFlag) {
		return provinceRepo.findByActiveFlag(activeFlag);
	}

	@Override
	public Page<Province> findAll(Pageable page) {
		PageRequest pr = new PageRequest(page.getPageNumber()-1, page.getPageSize());
		return provinceRepo.findAll(pr);
	}

}
