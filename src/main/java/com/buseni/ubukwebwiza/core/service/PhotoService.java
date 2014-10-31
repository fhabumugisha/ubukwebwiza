package com.buseni.ubukwebwiza.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.core.domain.Photo;

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
	List<Photo> findAllByProvider(Integer idProvider);
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Photo> findAll(Pageable pageable) ;
	
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);

}
