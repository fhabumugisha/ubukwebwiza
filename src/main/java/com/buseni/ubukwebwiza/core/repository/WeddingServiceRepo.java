package com.buseni.ubukwebwiza.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.core.domain.WeddingService;

public interface WeddingServiceRepo extends JpaRepository<WeddingService, Integer> {
	
	List<WeddingService> findByProvider_id(Integer idProvider);

}
