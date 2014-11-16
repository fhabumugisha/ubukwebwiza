package com.buseni.ubukwebwiza.vendor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.vendor.domain.Photo;

public interface PhotoService {
	
	/**
	 * 
	 * @param weddingService
	 */
	void create(Photo photo);
	
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
	 * @param idProvider
	 * @return
	 */
	List<Photo> findAllByVendor(Integer idProvider);
	
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
	Page<Photo> findByActiveFlag(int activeFlag, Pageable pageable);
	
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);

}
