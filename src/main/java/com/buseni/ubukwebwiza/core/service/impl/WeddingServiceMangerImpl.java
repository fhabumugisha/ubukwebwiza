/**
 * 
 */
package com.buseni.ubukwebwiza.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.core.domain.WeddingService;
import com.buseni.ubukwebwiza.core.repository.WeddingServiceRepo;
import com.buseni.ubukwebwiza.core.service.WeddingServiceManager;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional
public class WeddingServiceMangerImpl implements WeddingServiceManager {

	private WeddingServiceRepo weddingServiceRepo;
	public WeddingServiceMangerImpl() {
		
	}
	@Autowired
	public WeddingServiceMangerImpl(WeddingServiceRepo weddingServiceRepo) {
		this.weddingServiceRepo =  weddingServiceRepo;
	}
	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.WeddingServiceManager#create(com.buseni.ubukwebwiza.core.domain.WeddingService)
	 */
	@Transactional
	public void create(WeddingService weddingService) {
		// TODO control before save
		weddingServiceRepo.save(weddingService);

	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.WeddingServiceManager#update(com.buseni.ubukwebwiza.core.domain.WeddingService)
	 */
	@Transactional
	public WeddingService update(WeddingService weddingService) {
		// TODO control  before save
		return weddingServiceRepo.save(weddingService);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.WeddingServiceManager#findById(java.lang.Integer)
	 */
	public WeddingService findById(Integer id) {
		if(null == id){
			return null;
		}
		return weddingServiceRepo.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.WeddingServiceManager#findAllByProvider(java.lang.Integer)
	 */
	public List<WeddingService> findAllByProvider(Integer idProvider) {
		if(null == idProvider){
			return null;
		}
		return weddingServiceRepo.findByProvider_id(idProvider);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.WeddingServiceManager#findAll(org.springframework.data.domain.Pageable)
	 */
	public Page<WeddingService> findAll(Pageable pageable) {
		return weddingServiceRepo.findAll(pageable);
	}
	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.WeddingServiceManager#delete(java.lang.Integer)
	 */
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			weddingServiceRepo.delete(id);
		}
		
	}

}
