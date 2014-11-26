package com.buseni.ubukwebwiza.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.vendor.domain.Province;

public interface ProvinceRepo  extends JpaRepository<Province, Integer>{

	
	List<Province> findByEnabled(boolean enabled);
}
