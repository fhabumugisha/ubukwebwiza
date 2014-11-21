package com.buseni.ubukwebwiza.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.buseni.ubukwebwiza.vendor.repository.DistrictRepo;
import com.buseni.ubukwebwiza.vendor.repository.WeddingServiceRepo;
import com.buseni.ubukwebwiza.vendor.service.DistrictService;
import com.buseni.ubukwebwiza.vendor.service.WeddingServiceManager;
import com.buseni.ubukwebwiza.vendor.service.impl.DistrictServiceImpl;
import com.buseni.ubukwebwiza.vendor.service.impl.WeddingServiceManagerImpl;

@Configuration 
public class ServiceConfiguration {

	
	@Bean
	public DistrictService districtService(DistrictRepo districtRepo) {
		return new  DistrictServiceImpl(districtRepo);
	}

	
	@Bean
	public WeddingServiceManager weddingServiceManager(WeddingServiceRepo weddingServiceRepo){
		return new WeddingServiceManagerImpl(weddingServiceRepo);
	}
}
