/**
 * 
 */
package com.buseni.ubukwebwiza.gallery.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.exceptions.ResourceNotFoundException;
import com.buseni.ubukwebwiza.gallery.beans.PhotoDetails;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.gallery.repository.GalleryPredicates;
import com.buseni.ubukwebwiza.gallery.repository.PhotoRepo;
import com.buseni.ubukwebwiza.gallery.service.PhotoService;
import com.buseni.ubukwebwiza.utils.AmazonS3Util;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional(readOnly=true)
public class PhotoServiceImpl implements PhotoService {

	public  static final Logger LOGGER = LoggerFactory.getLogger(PhotoServiceImpl.class);


	private PhotoRepo photoRepo;
	@Autowired
	private AmazonS3Util amazonS3Util;
	@Autowired
	public PhotoServiceImpl( PhotoRepo photoRepo) {
		this.photoRepo = photoRepo;
		
	}
	

	
	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.PhotoService#create(com.buseni.ubukwebwiza.administrator.domain.Photo)
	 */
	@Override
	@Transactional
	public Photo addOrUpdate(Photo photo) {
		// control before save
		if (photo == null) {
			throw new NullPointerException();
		}
		if (photo.getId() == null) {
			photo.setCreatedAt(new Date());
			photo.setLastUpdate(new Date());
			photo.setEnabled(Boolean.TRUE);
			photoRepo.save(photo);
			return photo;
		} else {
			Photo photoBdd = photoRepo.findById(photo.getId()).orElseThrow(()-> new NullPointerException(" shouldn't be null"));
			photoBdd.setLastUpdate( new Date());
			photoBdd.setEnabled(photo.isEnabled());
			photoBdd.setDescription(photo.getDescription());
			if (photo.getFilename() != null) {
				// picture changed, delete the old one
				if(photoBdd.getFilename()!= null){
					amazonS3Util.deleteFile(photoBdd.getFilename());				
				}
				photoBdd.setFilename(photo.getFilename());
			}	
			photoRepo.save(photoBdd);
			return photoBdd;
		}
	

		

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
			throw new NullPointerException();
		}
		Photo photo  = photoRepo.findById(id).orElse(null);
		if(photo == null){
			throw new ResourceNotFoundException();
		}
		return photo;
	}

	

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.PhotoService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Photo> findAll(Pageable pageable) {
		if( pageable == null){
			throw new NullPointerException();
		}
		PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		return photoRepo.findAll(pr);
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.PhotoService#findByActiveFlag(int, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Photo> findByEnabled(boolean enabled, Pageable pageable) {
		if( pageable == null ){
			throw new NullPointerException();
		}
		PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
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
			photoRepo.deleteById(id);
		}
		
	}

	@Override
	public Page<PhotoDetails> findPhotoGallery( Pageable pageable) {
		if( pageable == null){
			throw new NullPointerException();
		}
		
		
		PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		
		Page<PhotoDetails> photoDetailsPage = photoRepo.findPhotoGallery(pr);
		
		return photoDetailsPage;
	
	}
	
	@Override
	public List<Photo>  homePagePhotos(){		
		Page<Photo>  photosPage = photoRepo.findAll(GalleryPredicates.homePagePhotos(), PageRequest.of(0, 5, Sort.Direction.DESC, "lastUpdate"));
		if(photosPage != null){
			return  photosPage.getContent();
		}
		return new ArrayList<>();
	}

	@Override
	public Page<Photo> findByCategory(Integer category, Pageable pageable) {
		if( category == null || pageable ==  null){
			throw new NullPointerException();
		}
		PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "lastUpdate");
		return photoRepo.findByCategory(category, pr);
	}

	

	@Transactional
	@Override
	public void delete(Photo photo) {
		if(photo != null){
			photoRepo.delete(photo);
		}
		
	}



}
