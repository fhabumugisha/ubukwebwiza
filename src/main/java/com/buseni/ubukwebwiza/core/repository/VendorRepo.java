package com.buseni.ubukwebwiza.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.core.domain.Vendor;

public interface VendorRepo extends JpaRepository<Vendor, Integer> {
	
	Page<Vendor> findByBusinessName(String businessName, Pageable pageable);

}
