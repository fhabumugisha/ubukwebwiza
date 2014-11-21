package com.buseni.ubukwebwiza.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.vendor.domain.CodeProvince;

public interface CodeProvinceRepo  extends JpaRepository<CodeProvince, Integer>{

	
	List<CodeProvince> findByActiveFlag(int activeFlag);
}
