package com.buseni.ubukwebwiza.provider.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;
import com.buseni.ubukwebwiza.provider.repository.WeddingServiceRepo;
import com.buseni.ubukwebwiza.provider.service.WeddingServiceManager;
import com.buseni.ubukwebwiza.utils.UbUtils;

@Service("weddingServiceManager")
@Transactional(readOnly=true)
public class WeddingServiceManagerImpl implements WeddingServiceManager {

	
	private WeddingServiceRepo weddingServiceRepo;
	
	@Autowired
	public WeddingServiceManagerImpl(WeddingServiceRepo weddingServiceRepo) {
		this.weddingServiceRepo = weddingServiceRepo;
	}
	@Override
	public List<WeddingService> findAll() {
		return weddingServiceRepo.findAll();
	}

	@Override
	//@Transactional
	public Page<WeddingService> findAll(Pageable pageable) {
		PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		Page<WeddingService> allServices = weddingServiceRepo.findAll(pr);
//		allServices.getContent().forEach(d-> {
//			String urlName  = UbUtils.createUrlName(d.getLibelle(), false);
//			d.setUrlName(urlName);
//			weddingServiceRepo.save(d);
//		});
		return allServices;
	}
	@Override
	@Cacheable(value="findWeddingServices")
	public List<WeddingService> findByEnabled(boolean enabled) {		
		return weddingServiceRepo.findByEnabled(enabled);
		
	}
	@Override
	@Transactional
	public void add(WeddingService weddingService) throws BusinessException {
		//business control
		if(null == weddingService){
			throw new NullPointerException();
		}
		String urlName =  UbUtils.createUrlName(weddingService.getLibelle(), false);
		if(Objects.nonNull(weddingServiceRepo.findByUrlName(urlName)) ){
			urlName = UbUtils.createUrlName(weddingService.getLibelle(), true);
		}
		weddingService.setUrlName(urlName);
		weddingServiceRepo.save(weddingService);
		
	}
	@Override
	@Transactional
	public void delete(Integer id) {
		if(null == id){
			throw new NullPointerException();
		}
		WeddingService ws =  weddingServiceRepo.findById(id).orElse(null);
		if(ws != null){
			weddingServiceRepo.delete(ws);
		}
		
	}
	@Override
	public WeddingService findOne(Integer id) {
		if(null == id){
			throw new NullPointerException();
		}
		return weddingServiceRepo.findById(id).orElse(null);
	}

}
