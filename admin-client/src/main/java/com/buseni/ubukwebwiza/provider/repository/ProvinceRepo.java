package com.buseni.ubukwebwiza.provider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.provider.domain.Province;

public interface ProvinceRepo  extends JpaRepository<Province, Integer>{

	
	List<Province> findByEnabled(boolean enabled);
}
