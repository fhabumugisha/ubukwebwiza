package com.buseni.ubukwebwiza.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.vendor.domain.VendorWeddingService;

public interface VendorWeddingServiceRepo extends JpaRepository<VendorWeddingService, Integer> {
	
	List<VendorWeddingService> findByVendor_id(Integer idVendor);

	List<VendorWeddingService> findByWeddingService_id(Integer idWeddingService);

}
