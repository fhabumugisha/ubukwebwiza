package com.buseni.ubukwebwiza.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.buseni.ubukwebwiza.core.domain.Vendor;

public interface VendorRepo extends JpaRepository<Vendor, Integer> , QueryDslPredicateExecutor<Vendor>{
	
	Page<Vendor> findByBusinessName(String businessName, Pageable pageable);

}
