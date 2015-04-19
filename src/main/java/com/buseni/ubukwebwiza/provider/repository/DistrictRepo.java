package com.buseni.ubukwebwiza.provider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.provider.domain.District;

public interface DistrictRepo  extends JpaRepository<District, Integer>{
	List<District> findByEnabled(boolean enabled);
}
