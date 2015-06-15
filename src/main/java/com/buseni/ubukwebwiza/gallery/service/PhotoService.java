package com.buseni.ubukwebwiza.gallery.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.gallery.domain.Photo;

public interface PhotoService {
	
	/**
	 * 
	 * @param weddingService
	 * @return 
	 */
	void addOrUpdate(Photo photo) ;
	
	/**
	 * 
	 * @param weddingService
	 * @return
	 */
	Photo update(Photo photo);
	/**
	 * 
	 * @param id
	 * @return
	 */
	Photo findById(Integer id);
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Photo> findAll(Pageable pageable) ;
	
	/**
	 * 
	 * @param activeFlag
	 * @param pageable
	 * @return
	 */
	Page<Photo> findByEnabled(boolean enabled, Pageable pageable);
	
	
	
	/**
	 * 
	 * @param activeFlag
	 * @param pageable
	 * @return
	 */
	Page<Photo> findPhotoGallery(Pageable pageable);
	
 	
	/**
	 * 
	 * @param category
	 * @param pageable
	 * @return
	 */
	Page<Photo> findByCategory(Integer category, Pageable pageable);
	
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);
	
	
	

	/**
	 * Return the list of home page photos
	 * @return
	 */
	List<Photo> homePagePhotos();

	/**
	 * 
	 * @param photo
	 */
	void delete(Photo photo);

}
