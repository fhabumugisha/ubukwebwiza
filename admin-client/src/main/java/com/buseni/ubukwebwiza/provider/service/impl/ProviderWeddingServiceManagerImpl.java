/**
 * 
 */
package com.buseni.ubukwebwiza.provider.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.ResourceNotFoundException;
import com.buseni.ubukwebwiza.provider.domain.ProviderWeddingService;
import com.buseni.ubukwebwiza.provider.repository.ProviderWeddingServiceRepo;
import com.buseni.ubukwebwiza.provider.service.ProviderWeddingServiceManager;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional
public class ProviderWeddingServiceManagerImpl implements ProviderWeddingServiceManager {

	private ProviderWeddingServiceRepo providerWeddingServiceRepo;
	
	@Autowired
	public ProviderWeddingServiceManagerImpl(ProviderWeddingServiceRepo providerWeddingServiceRepo) {
		this.providerWeddingServiceRepo =  providerWeddingServiceRepo;
	}
	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.WeddingServiceManager#create(com.buseni.ubukwebwiza.administrator.domain.WeddingService)
	 */
	@Override
	@Transactional
	public void create(ProviderWeddingService providerWeddingService) throws BusinessException{
		// TODO control before save
		
		if(providerWeddingService.getId() == null){
			providerWeddingService.setCreatedAt(new Date());
			providerWeddingService.setEnabled(Boolean.TRUE);
		}else{
			ProviderWeddingService vwsBdd = providerWeddingServiceRepo.findById(providerWeddingService.getId()).orElseThrow(()-> new NullPointerException(" shouldn't be null"));
			providerWeddingService.setCreatedAt(vwsBdd.getCreatedAt());			
		}
		providerWeddingService.setLastUpdate(new Date());			
		providerWeddingServiceRepo.save(providerWeddingService);
		

	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.WeddingServiceManager#update(com.buseni.ubukwebwiza.administrator.domain.WeddingService)
	 */
	@Override
	@Transactional
	public ProviderWeddingService update(ProviderWeddingService providerWeddingService) {
		// TODO control  before save
		providerWeddingService.setLastUpdate(new Date());
		return providerWeddingServiceRepo.save(providerWeddingService);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.WeddingServiceManager#findById(java.lang.Integer)
	 */
	@Override
	public ProviderWeddingService findById(Integer id) {
		if(null == id){
			return null;
		}
		ProviderWeddingService providerWeddingService = providerWeddingServiceRepo.findById(id).orElse(null);
		if(providerWeddingService == null){
			throw new ResourceNotFoundException();
		}
		return providerWeddingService;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.WeddingServiceManager#findAllByProvider(java.lang.Integer)
	 */
	@Override
	public List<ProviderWeddingService> findAllByProvider(Integer idProvider) {
		if(null == idProvider){
			return null;
		}
		
		return providerWeddingServiceRepo.findByProvider_id(idProvider);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.WeddingServiceManager#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<ProviderWeddingService> findAll(Pageable pageable) {
		PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		return providerWeddingServiceRepo.findAll(pr);
	}
	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.WeddingServiceManager#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			providerWeddingServiceRepo.deleteById(id);
		}
		
	}
	@Override
	public List<ProviderWeddingService> findAllByWeddingService(
			Integer idWeddingService) {
		if(null == idWeddingService){
			return null;
		}
		return providerWeddingServiceRepo.findByWeddingService_id(idWeddingService);
	}
	

}
