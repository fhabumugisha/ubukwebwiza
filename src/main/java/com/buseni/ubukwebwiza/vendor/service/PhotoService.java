package com.buseni.ubukwebwiza.vendor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.exceptions.ServiceLayerException;
import com.buseni.ubukwebwiza.vendor.domain.Photo;

public interface PhotoService {
	
	/**
	 * 
	 * @param weddingService
	 * @return 
	 */
	void create(Photo photo) throws ServiceLayerException;
	
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
	Page<Photo> findByEnabledAndCategory(boolean enabled, Integer category, Pageable pageable);
	
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

}
