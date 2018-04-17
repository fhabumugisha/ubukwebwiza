package com.buseni.ubukwebwiza.provider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.provider.domain.WeddingService;

public interface WeddingServiceRepo extends JpaRepository<WeddingService, Integer> {
	
	List<WeddingService> findByEnabled(boolean enabled);

	WeddingService findByUrlName(String urlName);

}
