package com.buseni.ubukwebwiza.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.vendor.domain.District;

public interface DistrictRepo  extends JpaRepository<District, Integer>{
	List<District> findByEnabled(boolean enabled);
}
