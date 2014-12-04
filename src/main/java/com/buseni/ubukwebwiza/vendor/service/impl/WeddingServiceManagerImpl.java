package com.buseni.ubukwebwiza.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.exceptions.ServiceLayerException;
import com.buseni.ubukwebwiza.vendor.domain.WeddingService;
import com.buseni.ubukwebwiza.vendor.repository.WeddingServiceRepo;
import com.buseni.ubukwebwiza.vendor.service.WeddingServiceManager;

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
	public Page<WeddingService> findAll(Pageable pageable) {
		PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
		return weddingServiceRepo.findAll(pr);
	}
	@Override
	public List<WeddingService> findByEnabled(boolean enabled) {
		
		return weddingServiceRepo.findByEnabled(enabled);
		
	}
	@Override
	@Transactional
	public void add(WeddingService weddingService) throws ServiceLayerException {
		//business control
		if(null == weddingService){
			throw new NullPointerException();
		}
		weddingServiceRepo.save(weddingService);
		
	}
	@Override
	@Transactional
	public void delete(Integer id) {
		if(null == id){
			throw new NullPointerException();
		}
		WeddingService ws =  weddingServiceRepo.findOne(id);
		if(ws != null){
			weddingServiceRepo.delete(ws);
		}
		
	}
	@Override
	public WeddingService findOne(Integer id) {
		if(null == id){
			throw new NullPointerException();
		}
		return weddingServiceRepo.findOne(id);
	}

}
