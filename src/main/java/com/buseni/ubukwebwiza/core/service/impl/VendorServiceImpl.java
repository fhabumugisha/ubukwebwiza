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
import com.buseni.ubukwebwiza.core.domain.Photo;
import com.buseni.ubukwebwiza.core.domain.Vendor;
import com.buseni.ubukwebwiza.core.domain.WeddingService;
import com.buseni.ubukwebwiza.core.predicates.VendorPredicates;
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
		
		/*Vendor  vendor = new Vendor();
		vendor.setId(id);
		vendor.setBusinessName("Aline Manzi Designs");
		vendor.setAboutme(" Kaiyuyd  fhya ryyysdaz  dtyxygd fyidauf duyfutsatfd fyfdaufdif fyfduautfufdufdf fufufusfd");
		vendor.setAddress(" Kigali avenue de la Paix, BP 14444");
		vendor.setFbUsername("facebook.com/alinemanzidesigns");
		vendor.setTwitterUsername("twitter.com/alinemanzidesigns");
		vendor.setWebsite("www.alinemanzidesigns.com");
		vendor.setPhoneNumber("+250 00 00 00 00 00 00 ");
		 
		CodeWeddingService cws = new CodeWeddingService();
		cws.setId(1);
		cws.setLibelle("Wedding Planner");
		WeddingService ws =  new WeddingService();
		ws.setCodeWeddingService(cws);
		vendor.getWeddingServices().add(ws);
		//return vendorRepo.findOne(id);
		
		Photo photo  = new Photo();
		photo.setId(1);
		photo.setDescription(" Taking care of everything");
		photo.setPhotoName("wedding_4_content.jpg");
		vendor.getPhotos().add(photo);
		
		photo  = new Photo();
		photo.setId(2);
		photo.setDescription("Wedding rings");
		photo.setPhotoName("Wedding_rings.jpg");
		vendor.getPhotos().add(photo);
		
		photo  = new Photo();
		photo.setId(3);
		photo.setDescription("Planning");
		photo.setPhotoName("wedding-banner.png");
		vendor.getPhotos().add(photo);*/
		
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
		if(null != vendorSearch){
			vendorRepo.findAll(VendorPredicates.search(vendorSearch));
		}
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
		return vendorRepo.findAll();
	}



}
