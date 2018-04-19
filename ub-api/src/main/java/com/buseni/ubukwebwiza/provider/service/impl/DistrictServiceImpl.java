package com.buseni.ubukwebwiza.provider.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.CustomError;
import com.buseni.ubukwebwiza.exceptions.CustomErrorBuilder;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.repository.DistrictRepo;
import com.buseni.ubukwebwiza.provider.service.DistrictService;
import com.buseni.ubukwebwiza.utils.UbUtils;

@Service
@Transactional(readOnly=true)
public class DistrictServiceImpl implements DistrictService {


	private DistrictRepo  districtRepo;
	
	
	@Autowired
	public  DistrictServiceImpl(DistrictRepo  districtRepo) {
		this.districtRepo = districtRepo;
	}
	
	@Override
	@Cacheable(value="findDistricts")
	public List<District> findByEnabled(boolean enabled) {
		return districtRepo.findByEnabled(enabled);
	}

	@Override
//	@Transactional
	public Page<District> findAll(Pageable page) {
		PageRequest pr = PageRequest.of(page.getPageNumber(), page.getPageSize());
		
		Page<District> allDistricts =  districtRepo.findAll(pr);
//		allDistricts.getContent().forEach(d-> {
//			String urlName  = UbUtils.createUrlName(d.getLibelle(), false);
//			d.setUrlName(urlName);;
//			districtRepo.save(d);
//		});
		
		return allDistricts;
	}

	@Override
	@Transactional
	public void add(District district) throws BusinessException {
		//business control 
		if(district == null){
			throw new NullPointerException();
		}
		if(district.getProvince() == null || district.getProvince().getId() == null){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.district.province.null");			
			CustomError  ce = ceb.field("province").buid();
			throw new BusinessException(ce);
		}
		String urlName =  UbUtils.createUrlName(district.getLibelle(), false);
		if(CollectionUtils.isNotEmpty(districtRepo.findByUrlName(urlName))){
			urlName = UbUtils.createUrlName(district.getLibelle(), true);
		}
		district.setUrlName(urlName);
		districtRepo.save(district);
		
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			District district =  districtRepo.findById(id).orElse(null);
			if(district != null){
				districtRepo.delete(district);
			}
			
		}
		
	}

	@Override
	public District findOne(Integer id) {
		if(null != id){
			return districtRepo.findById(id).orElse(null);
		}
		return null;
	}

}
