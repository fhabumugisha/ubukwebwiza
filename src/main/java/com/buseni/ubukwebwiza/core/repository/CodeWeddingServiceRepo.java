package com.buseni.ubukwebwiza.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.core.domain.CodeWeddingService;

public interface CodeWeddingServiceRepo extends JpaRepository<CodeWeddingService, Integer> {
	
	List<CodeWeddingService> findByActiveFlag(int activeFlag);

}
