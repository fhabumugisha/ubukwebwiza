package com.buseni.ubukwebwiza.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.core.domain.CodeDistrict;
import com.buseni.ubukwebwiza.core.domain.CodeProvince;
import com.buseni.ubukwebwiza.core.repository.CodeDistrictRepo;
import com.buseni.ubukwebwiza.core.service.CodeDistrictService;

@Service
@Transactional(readOnly=true)
public class CodeDistrictServiceImpl implements CodeDistrictService {

	
	private CodeDistrictRepo  codeDistrictRepo;
	
	public  CodeDistrictServiceImpl() {
	
	}
	@Autowired
	public  CodeDistrictServiceImpl(CodeDistrictRepo  codeDistrictRepo) {
		this.codeDistrictRepo = codeDistrictRepo;
	}
	
	@Override
	public List<CodeDistrict> findByActiveFlagGrouped(int activeFlag) {
		
		List<CodeDistrict> districts = new ArrayList<CodeDistrict>();
		CodeProvince codeProvince = new CodeProvince();
		codeProvince.setId(1);
		codeProvince.setLibelle("Kigali");
		codeProvince.setActiveFlag(1);
		CodeDistrict d1 =  new CodeDistrict();
		d1.setId(1);
		d1.setLibelle("Gasabo");
		d1.setActiveFlag(1);
		d1.setCode("GSB");
		d1.setCodeProvince(codeProvince);
		districts.add(d1);
	
		
		CodeDistrict d2 =  new CodeDistrict();
		d2.setId(2);
		d2.setLibelle("Kicukiro");
		d2.setActiveFlag(1);
		d2.setCode("GSKCKB");
		d2.setCodeProvince(codeProvince);
		districts.add(d2);
		
		
		CodeDistrict d3 =  new CodeDistrict();
		d3.setId(3);
		d3.setLibelle("Nyarugenge");
		d3.setActiveFlag(1);
		d3.setCode("NYG");
		d3.setCodeProvince(codeProvince);
		districts.add(d3);
		
		
		return districts;
		//return codeDistrictRepo.findByActiveFlagGrouped(activeFlag);
	}

}
