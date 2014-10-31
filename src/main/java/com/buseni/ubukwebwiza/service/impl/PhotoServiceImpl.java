/**
 * 
 */
package com.buseni.ubukwebwiza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.domain.Photo;
import com.buseni.ubukwebwiza.repository.PhotoRepo;
import com.buseni.ubukwebwiza.service.PhotoService;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional(readOnly=true)
public class PhotoServiceImpl implements PhotoService {

	private PhotoRepo photoRepo;
	
	public PhotoServiceImpl() {
		
	}
	
	@Autowired
	public PhotoServiceImpl(PhotoRepo photoRepo){
		this.photoRepo = photoRepo;
	}
	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.service.PhotoService#create(com.buseni.ubukwebwiza.domain.Photo)
	 */
	@Transactional
	public void create(Photo photo) {
		// TODO control before save
		photoRepo.save(photo);

	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.service.PhotoService#update(com.buseni.ubukwebwiza.domain.Photo)
	 */
	@Transactional
	public Photo update(Photo photo) {
		// TODO control before save
		return photoRepo.save(photo);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.service.PhotoService#findById(java.lang.Integer)
	 */
	public Photo findById(Integer id) {
		if(null == id){
			return null;
		}
		return photoRepo.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.service.PhotoService#findAllByProvider(java.lang.Integer)
	 */
	public List<Photo> findAllByProvider(Integer idProvider) {
		return photoRepo.findAllByProvider(idProvider);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.service.PhotoService#findAll(org.springframework.data.domain.Pageable)
	 */
	public Page<Photo> findAll(Pageable pageable) {
		return photoRepo.findAll(pageable);
	}

	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.service.PhotoService#delete(java.lang.Integer)
	 */
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			photoRepo.delete(id);
		}
		
	}

}
