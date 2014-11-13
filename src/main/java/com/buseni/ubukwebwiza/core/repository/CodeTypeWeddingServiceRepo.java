package com.buseni.ubukwebwiza.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.core.domain.CodeTypeWeddingService;

public interface CodeTypeWeddingServiceRepo extends JpaRepository<CodeTypeWeddingService, Integer> {
	
	List<CodeTypeWeddingService> findByActiveFlag(int activeFlag);

}
