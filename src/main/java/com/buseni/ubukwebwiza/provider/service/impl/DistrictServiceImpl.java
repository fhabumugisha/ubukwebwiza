package com.buseni.ubukwebwiza.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.exceptions.CustomError;
import com.buseni.ubukwebwiza.exceptions.CustomErrorBuilder;
import com.buseni.ubukwebwiza.exceptions.ServiceLayerException;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.repository.DistrictRepo;
import com.buseni.ubukwebwiza.provider.service.DistrictService;

@Service
@Transactional(readOnly=true)
public class DistrictServiceImpl implements DistrictService {


	private DistrictRepo  districtRepo;
	
	
	@Autowired
	public  DistrictServiceImpl(DistrictRepo  districtRepo) {
		this.districtRepo = districtRepo;
	}
	
	@Override
	public List<District> findByEnabled(boolean enabled) {
		return districtRepo.findByEnabled(enabled);
	}

	@Override
	public Page<District> findAll(Pageable page) {
		PageRequest pr = new PageRequest(page.getPageNumber()-1, page.getPageSize());
		return districtRepo.findAll(pr);
	}

	@Override
	@Transactional
	public void add(District district) throws ServiceLayerException {
		//business control 
		if(district == null){
			throw new NullPointerException();
		}
		if(district.getProvince() == null || district.getProvince().getId() == null){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.district.province.null");			
			CustomError  ce = ceb.field("province").buid();
			throw new ServiceLayerException(ce);
		}
		districtRepo.save(district);
		
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			District district =  districtRepo.findOne(id);
			if(district != null){
				districtRepo.delete(district);
			}
			
		}
		
	}

	@Override
	public District findOne(Integer id) {
		if(null != id){
			return districtRepo.findOne(id);
		}
		return null;
	}

}
