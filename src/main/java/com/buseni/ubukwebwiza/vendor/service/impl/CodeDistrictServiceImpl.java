package com.buseni.ubukwebwiza.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.vendor.domain.CodeDistrict;
import com.buseni.ubukwebwiza.vendor.repository.CodeDistrictRepo;
import com.buseni.ubukwebwiza.vendor.service.CodeDistrictService;

@Service
@Transactional(readOnly=true)
public class CodeDistrictServiceImpl implements CodeDistrictService {


	private CodeDistrictRepo  codeDistrictRepo;
	
	
	@Autowired
	public  CodeDistrictServiceImpl(CodeDistrictRepo  codeDistrictRepo) {
		this.codeDistrictRepo = codeDistrictRepo;
	}
	
	@Override
	public List<CodeDistrict> findByActiveFlag(int activeFlag) {
		return codeDistrictRepo.findByActiveFlag(activeFlag);
	}

	@Override
	public Page<CodeDistrict> findAll(Pageable page) {
		PageRequest pr = new PageRequest(page.getPageNumber()-1, page.getPageSize());
		return codeDistrictRepo.findAll(pr);
	}

}
