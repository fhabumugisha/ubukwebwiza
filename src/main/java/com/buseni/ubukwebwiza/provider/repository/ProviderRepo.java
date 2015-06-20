package com.buseni.ubukwebwiza.provider.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.buseni.ubukwebwiza.provider.domain.Provider;

public interface ProviderRepo extends JpaRepository<Provider, Integer> , QueryDslPredicateExecutor<Provider>{
	
	Page<Provider> findByBusinessName(String businessName, Pageable pageable);
	
	Page<Provider> findByAccount_Enabled(boolean enabled, Pageable pageable);
	
	List<Provider> findByAccount_Enabled(boolean enabled);

	
	Provider findByAccount_Email(String email);


}
