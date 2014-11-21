package com.buseni.ubukwebwiza.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.vendor.domain.CodeProvince;
import com.buseni.ubukwebwiza.vendor.repository.CodeProvinceRepo;
import com.buseni.ubukwebwiza.vendor.service.CodeProvinceService;

@Service
@Transactional(readOnly=true)
public class CodeProvinceServiceImpl implements CodeProvinceService{

	private CodeProvinceRepo codeProvinceRepo;
	
	
	@Autowired
	public  CodeProvinceServiceImpl(CodeProvinceRepo codeProvinceRepo) {
		this.codeProvinceRepo = codeProvinceRepo;
	}
	
	@Override
	public List<CodeProvince> findByActiveFlag(int activeFlag) {
		return codeProvinceRepo.findByActiveFlag(activeFlag);
	}

	@Override
	public Page<CodeProvince> findAll(Pageable page) {
		PageRequest pr = new PageRequest(page.getPageNumber()-1, page.getPageSize());
		return codeProvinceRepo.findAll(pr);
	}

}
