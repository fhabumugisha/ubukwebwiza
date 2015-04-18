/**
 * 
 */
package com.buseni.ubukwebwiza.vendor.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.administrator.enums.EnumPhotoCategory;
import com.buseni.ubukwebwiza.vendor.domain.Photo;
import com.buseni.ubukwebwiza.vendor.repository.PhotoRepo;
import com.buseni.ubukwebwiza.vendor.service.PhotoService;

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
	 * @see com.buseni.ubukwebwiza.administrator.service.PhotoService#create(com.buseni.ubukwebwiza.administrator.domain.Photo)
	 */
	@Override
	@Transactional
	public void create(Photo photo) {
		// control before save
		if (photo == null) {
			throw new NullPointerException();
		}
		if (photo.getId() == null) {
			photo.setCreatedAt(new Date());
		} else {
			Photo photoBdd = photoRepo.findOne(photo.getId());
			photo.setFilename(photoBdd.getFilename());
			photo.setCreatedAt(photoBdd.getCreatedAt());
			if (photo.getContent() == null) {
				photo.setContent(photoBdd.getContent());
			}
		}
		photo.setLastUpdate(new Date());

		photoRepo.save(photo);

	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.PhotoService#update(com.buseni.ubukwebwiza.administrator.domain.Photo)
	 */
	@Override
	@Transactional
	public Photo update(Photo photo) {
		// TODO control before save
		photo.setLastUpdate(new Date());
		return photoRepo.save(photo);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.PhotoService#findById(java.lang.Integer)
	 */
	@Override
	public Photo findById(Integer id) {
		if(null == id){
			return null;
		}
		return photoRepo.findOne(id);
	}

	

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.PhotoService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Photo> findAll(Pageable pageable) {
		if( pageable == null){
			throw new NullPointerException();
		}
		PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
		return photoRepo.findAll(pr);
	}
	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.PhotoService#findByActiveFlag(int, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Photo> findByEnabled(boolean enabled, Pageable pageable) {
		if( pageable == null){
			throw new NullPointerException();
		}
		PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
		return photoRepo.findByEnabled(enabled, pr);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.PhotoService#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			photoRepo.delete(id);
		}
		
	}

	@Override
	public Page<Photo> findByEnabledAndCategory(boolean enabled,
			Integer category, Pageable pageable) {
		if(category == null || pageable == null){
			throw new NullPointerException();
		}
		PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
		return photoRepo.findByEnabledAndCategory(enabled, category, pr);
	
	}
	
	@Override
	public List<Photo>  homePagePhotos(){
		Page<Photo>  photosPage = photoRepo.findByEnabledAndCategory(Boolean.TRUE,EnumPhotoCategory.HOME_PAGE.getId(), new PageRequest(0, 5, Sort.Direction.DESC, "lastUpdate"));
		if(photosPage != null){
			return  photosPage.getContent();
		}
		return new ArrayList<>();
	}

}
