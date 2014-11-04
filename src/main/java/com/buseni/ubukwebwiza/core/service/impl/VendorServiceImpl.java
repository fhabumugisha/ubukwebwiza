/**
 * 
 */
package com.buseni.ubukwebwiza.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.core.domain.CodeWeddingService;
import com.buseni.ubukwebwiza.core.domain.Vendor;
import com.buseni.ubukwebwiza.core.domain.WeddingService;
import com.buseni.ubukwebwiza.core.repository.VendorRepo;
import com.buseni.ubukwebwiza.core.service.VendorService;
import com.buseni.ubukwebwiza.utils.VendorSearch;

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
	 * @see com.buseni.ubukwebwiza.core.service.VendorService#create(com.buseni.ubukwebwiza.core.domain.Vendor)
	 */
	@Transactional
	public void create(Vendor vendor) {
		// TODO control before save
		vendorRepo.save(vendor);

	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.VendorService#update(com.buseni.ubukwebwiza.core.domain.Vendor)
	 */
	@Transactional
	public Vendor update(Vendor vendor) {
		// TODO COntrol before save
		return vendorRepo.save(vendor);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.VendorService#findById(java.lang.Integer)
	 */
	public Vendor findById(Integer id) {
		if(null == id){
			return null;
		}
		return vendorRepo.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.VendorService#findAll(org.springframework.data.domain.Pageable)
	 */
	public Page<Vendor> findAll(Pageable pageable) {
		return vendorRepo.findAll(pageable);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.VendorService#findAll(com.buseni.ubukwebwiza.utils.VendorSearch, org.springframework.data.domain.Pageable)
	 */
	public Page<Vendor> search(VendorSearch vendorSearch,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.VendorService#delete(java.lang.Integer)
	 */
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			vendorRepo.delete(id);
		}
		
	}

	@Override
	public List<Vendor> getFeaturedVendors() {
		List<Vendor> vendors = new ArrayList<Vendor>();
		WeddingService ws =  new WeddingService();
		CodeWeddingService cws = new CodeWeddingService();
		Vendor vendor = new Vendor();
		
		vendor.setId(1);
		vendor.setBusinessName("Man Alain");
		vendor.setDistrict("Nyarugenge");		
		ws.setId(1);
		
		cws.setId(1);
		cws.setLibelle("Wedding Planner");
		ws.setCodeWeddingService(cws);
		vendor.getWeddingServices().add(ws);
		vendors.add(vendor);
		
		vendor = new Vendor();
		vendor.setId(2);
		vendor.setBusinessName("Kaline Alice");
		vendor.setDistrict("Gasabo");
		 ws =  new WeddingService();
		ws.setId(2);
		 cws = new CodeWeddingService();
		cws.setId(2);
		cws.setLibelle("Catering");
		ws.setCodeWeddingService(cws);
		vendor.getWeddingServices().add(ws);
		vendors.add(vendor);
		
		vendor = new Vendor();
		vendor.setId(3);
		vendor.setBusinessName("Wedding Services");
		vendor.setDistrict("Kicukiro");
		 ws =  new WeddingService();
		ws.setId(3);
		 cws = new CodeWeddingService();
		cws.setId(3);
		cws.setLibelle("Weddinng cars");
		ws.setCodeWeddingService(cws);
		vendor.getWeddingServices().add(ws);
		vendors.add(vendor);
		
		return vendors;
	}

}
