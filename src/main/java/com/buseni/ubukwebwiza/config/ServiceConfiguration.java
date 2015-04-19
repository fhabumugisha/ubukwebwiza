package com.buseni.ubukwebwiza.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.buseni.ubukwebwiza.provider.repository.DistrictRepo;
import com.buseni.ubukwebwiza.provider.repository.WeddingServiceRepo;
import com.buseni.ubukwebwiza.provider.service.DistrictService;
import com.buseni.ubukwebwiza.provider.service.WeddingServiceManager;
import com.buseni.ubukwebwiza.provider.service.impl.DistrictServiceImpl;
import com.buseni.ubukwebwiza.provider.service.impl.WeddingServiceManagerImpl;

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
