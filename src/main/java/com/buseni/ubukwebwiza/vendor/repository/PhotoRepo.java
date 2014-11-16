package com.buseni.ubukwebwiza.vendor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.vendor.domain.Photo;

public interface PhotoRepo extends JpaRepository<Photo, Integer> {

	List<Photo> findByVendor_id(Integer idVendor);
	
	Page<Photo> findByActiveFlag(int activeFlag, Pageable pageable);
}
