package com.buseni.ubukwebwiza.provider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.provider.domain.ProviderWeddingService;

public interface ProviderWeddingServiceRepo extends JpaRepository<ProviderWeddingService, Integer> {
	
	List<ProviderWeddingService> findByProvider_id(Integer idProvider);

	List<ProviderWeddingService> findByWeddingService_id(Integer idWeddingService);

}
