/**
 * 
 */
package com.buseni.ubukwebwiza.vendor.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.vendor.domain.VendorWeddingService;
import com.buseni.ubukwebwiza.vendor.repository.VendorWeddingServiceRepo;
import com.buseni.ubukwebwiza.vendor.service.VendorWeddingServiceManager;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional
public class VendorWeddingServiceManagerImpl implements VendorWeddingServiceManager {

	private VendorWeddingServiceRepo vendorWeddingServiceRepo;
	
	@Autowired
	public VendorWeddingServiceManagerImpl(VendorWeddingServiceRepo vendorWeddingServiceRepo) {
		this.vendorWeddingServiceRepo =  vendorWeddingServiceRepo;
	}
	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.WeddingServiceManager#create(com.buseni.ubukwebwiza.administrator.domain.WeddingService)
	 */
	@Override
	@Transactional
	public void create(VendorWeddingService vendorWeddingService) {
		// TODO control before save
		vendorWeddingService.setCreatedAt(new Date());
		vendorWeddingService.setLastUpdate(new Date());
		vendorWeddingServiceRepo.save(vendorWeddingService);

	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.WeddingServiceManager#update(com.buseni.ubukwebwiza.administrator.domain.WeddingService)
	 */
	@Override
	@Transactional
	public VendorWeddingService update(VendorWeddingService vendorWeddingService) {
		// TODO control  before save
		vendorWeddingService.setLastUpdate(new Date());
		return vendorWeddingServiceRepo.save(vendorWeddingService);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.WeddingServiceManager#findById(java.lang.Integer)
	 */
	@Override
	public VendorWeddingService findById(Integer id) {
		if(null == id){
			return null;
		}
		return vendorWeddingServiceRepo.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.WeddingServiceManager#findAllByProvider(java.lang.Integer)
	 */
	@Override
	public List<VendorWeddingService> findAllByVendor(Integer idVendor) {
		if(null == idVendor){
			return null;
		}
		
		return vendorWeddingServiceRepo.findByVendor_id(idVendor);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.WeddingServiceManager#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<VendorWeddingService> findAll(Pageable pageable) {
		PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
		return vendorWeddingServiceRepo.findAll(pr);
	}
	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.WeddingServiceManager#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			vendorWeddingServiceRepo.delete(id);
		}
		
	}
	@Override
	public List<VendorWeddingService> findAllByWeddingService(
			Integer idWeddingService) {
		if(null == idWeddingService){
			return null;
		}
		return vendorWeddingServiceRepo.findByWeddingService_id(idWeddingService);
	}
	

}
