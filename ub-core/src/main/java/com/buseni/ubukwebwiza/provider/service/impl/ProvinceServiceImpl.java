package com.buseni.ubukwebwiza.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.provider.domain.Province;
import com.buseni.ubukwebwiza.provider.repository.ProvinceRepo;
import com.buseni.ubukwebwiza.provider.service.ProvinceService;

@Service
@Transactional(readOnly=true)
public class ProvinceServiceImpl implements ProvinceService{

	private ProvinceRepo provinceRepo;
	

	@Autowired
	public  ProvinceServiceImpl(ProvinceRepo provinceRepo) {
		this.provinceRepo = provinceRepo;
	}
	
	@Override
	public List<Province> findByEnabled(boolean enabled) {
		return provinceRepo.findByEnabled(enabled);
	}

	@Override
	public Page<Province> findAll(Pageable page) {
		PageRequest pr = PageRequest.of(page.getPageNumber(), page.getPageSize());
		return provinceRepo.findAll(pr);
	}

	@Override
	//@Valid
	@Transactional
	public void add(Province province) throws BusinessException {
		/*if(!province.isEnabled() ){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.province.enabled.notchecked");			
			CustomError  ce = ceb.field("enabled").buid();
			throw new BusinessException(ce);
		}*/
		provinceRepo.save(province);
		
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		if(null !=  id){			
			Province  province = provinceRepo.findById(id).orElse(null);
			if(province != null){
				provinceRepo.delete(province);
			}
		}
		
	
	}

	@Override
	public Province findOne(Integer id) {
		if(null == id){			
			//throw exception
			}
		return provinceRepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	//@Valid
	public void update(Province province) {
		provinceRepo.save(province);
		
	}
	
	

}
