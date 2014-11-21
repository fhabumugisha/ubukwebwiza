package com.buseni.ubukwebwiza.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		return weddingServiceRepo.findAll(pageable);
	}
	@Override
	public List<WeddingService> findByActiveFlag(int activeFlag) {
		
		return weddingServiceRepo.findByActiveFlag(activeFlag);
		
	}

}
