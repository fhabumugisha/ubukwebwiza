package com.buseni.ubukwebwiza.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.vendor.domain.WeddingService;

public interface WeddingServiceRepo extends JpaRepository<WeddingService, Integer> {
	
	List<WeddingService> findByEnabled(boolean enabled);

}
