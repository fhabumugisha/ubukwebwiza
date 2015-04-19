package com.buseni.ubukwebwiza.provider.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.buseni.ubukwebwiza.provider.domain.Provider;

public interface ProviderRepo extends JpaRepository<Provider, Integer> , QueryDslPredicateExecutor<Provider>{
	
	Page<Provider> findByBusinessName(String businessName, Pageable pageable);
	
	Page<Provider> findByEnabled(boolean enabled, Pageable pageable);


}
