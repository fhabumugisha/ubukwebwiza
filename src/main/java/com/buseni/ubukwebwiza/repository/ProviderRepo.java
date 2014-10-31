package com.buseni.ubukwebwiza.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.domain.Provider;
import com.buseni.ubukwebwiza.utils.ProviderSearch;

public interface ProviderRepo extends JpaRepository<Provider, Integer> {
	
	Page<Provider> search(ProviderSearch providerSearch, Pageable pageable);

}
