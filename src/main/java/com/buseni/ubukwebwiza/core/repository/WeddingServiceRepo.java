package com.buseni.ubukwebwiza.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buseni.ubukwebwiza.core.domain.WeddingService;

public interface WeddingServiceRepo extends JpaRepository<WeddingService, Integer> {
	
	@Query("select ws from WeddingService ws where ws.provider.id =:idProvider")
	List<WeddingService> findAllByProvider(@Param("idProvider") Integer idProvider);

}
