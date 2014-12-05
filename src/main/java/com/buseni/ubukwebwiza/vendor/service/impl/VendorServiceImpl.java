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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.vendor.domain.Vendor;
import com.buseni.ubukwebwiza.vendor.predicates.VendorPredicates;
import com.buseni.ubukwebwiza.vendor.repository.VendorRepo;
import com.buseni.ubukwebwiza.vendor.service.VendorService;
import com.buseni.ubukwebwiza.vendor.utils.VendorSearch;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional(readOnly=true)
public class VendorServiceImpl implements VendorService {
	
	private VendorRepo vendorRepo;
	
	@Autowired
	public VendorServiceImpl(VendorRepo vendorRepo){
		this.vendorRepo = vendorRepo;
		
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.VendorService#create(com.buseni.ubukwebwiza.administrator.domain.Vendor)
	 */
	@Override
	@Transactional
	public void add(Vendor vendor) {
		// TODO control before save
		vendor.setCreatedAt(new Date());
		vendor.setLastUpdate(new Date());
		vendorRepo.save(vendor);

	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.VendorService#update(com.buseni.ubukwebwiza.administrator.domain.Vendor)
	 */
	@Override
	@Transactional
	public Vendor update(Vendor vendor) {
		// TODO COntrol before save
		vendor.setLastUpdate(new Date());
		return vendorRepo.save(vendor);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.VendorService#findById(java.lang.Integer)
	 */
	@Override
	public Vendor findOne(Integer id) {
		if(null == id){
			throw new NullPointerException();
		}
		
		
		return vendorRepo.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.VendorService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Vendor> findAll(Pageable pageable) {
		PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
		return vendorRepo.findAll(pr);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.VendorService#findAll(com.buseni.ubukwebwiza.vendor.utils.VendorSearch, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Vendor> search(VendorSearch vendorSearch,
			Pageable pageable) {

		if(null != vendorSearch){
			PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
			return	vendorRepo.findAll(VendorPredicates.search(vendorSearch), pr);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.VendorService#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			vendorRepo.delete(id);
		}
		
	}

	@Override
	public List<Vendor> getFeaturedVendors() {	
		Page<Vendor> page = vendorRepo.findByEnabled(Boolean.TRUE, new PageRequest(0, 3, Sort.Direction.DESC, "nbViews"));
		return page.getContent();
	}

	@Override
	public Page<Vendor> findByEnabled(boolean enabled, Pageable pageable) {
		PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
		return vendorRepo.findByEnabled(enabled, pr);
	}

	@Override
	@Transactional
	public Vendor getVendor(Integer id) {
		if(null == id){
			throw new NullPointerException();
		}
		
		Vendor vendor  = vendorRepo.findOne(id);
		if(vendor != null){
			vendor.setNbViews(vendor.getNbViews() + 1);
			vendor.setLastUpdated(new Date());
			vendorRepo.save(vendor);
		}
		return vendor;
	}



}
