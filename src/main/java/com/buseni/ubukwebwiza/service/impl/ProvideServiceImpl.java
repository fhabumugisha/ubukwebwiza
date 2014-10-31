/**
 * 
 */
package com.buseni.ubukwebwiza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.domain.Provider;
import com.buseni.ubukwebwiza.repository.ProviderRepo;
import com.buseni.ubukwebwiza.service.ProviderService;
import com.buseni.ubukwebwiza.utils.ProviderSearch;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional(readOnly=true)
public class ProvideServiceImpl implements ProviderService {
	
	private ProviderRepo providerRepo;
	
	public ProvideServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public ProvideServiceImpl(ProviderRepo providerRepo){
		this.providerRepo = providerRepo;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.service.ProviderService#create(com.buseni.ubukwebwiza.domain.Provider)
	 */
	@Transactional
	public void create(Provider provider) {
		// TODO control before save
		providerRepo.save(provider);

	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.service.ProviderService#update(com.buseni.ubukwebwiza.domain.Provider)
	 */
	@Transactional
	public Provider update(Provider provider) {
		// TODO COntrol before save
		return providerRepo.save(provider);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.service.ProviderService#findById(java.lang.Integer)
	 */
	public Provider findById(Integer id) {
		if(null == id){
			return null;
		}
		return providerRepo.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.service.ProviderService#findAll(org.springframework.data.domain.Pageable)
	 */
	public Page<Provider> findAll(Pageable pageable) {
		return providerRepo.findAll(pageable);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.service.ProviderService#findAll(com.buseni.ubukwebwiza.utils.ProviderSearch, org.springframework.data.domain.Pageable)
	 */
	public Page<Provider> search(ProviderSearch providerSearch,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.service.ProviderService#delete(java.lang.Integer)
	 */
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			providerRepo.delete(id);
		}
		
	}

}
