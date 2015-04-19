package com.buseni.ubukwebwiza.provider.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.provider.domain.Photo;

public interface PhotoRepo extends JpaRepository<Photo, Integer> {

	Page<Photo> findByEnabled(boolean enabled, Pageable pageable);
	
	Page<Photo> findByCategory(Integer category, Pageable pageable);
	
	Page<Photo> findByEnabledAndCategory(boolean enabled, Integer category, Pageable pageable);
	
}
