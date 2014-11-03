package com.buseni.ubukwebwiza.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.buseni.ubukwebwiza.core.repository.CodeDistrictRepo;
import com.buseni.ubukwebwiza.core.repository.CodeWeddingServiceRepo;
import com.buseni.ubukwebwiza.core.service.CodeDistrictService;
import com.buseni.ubukwebwiza.core.service.CodeWeddingServiceManager;
import com.buseni.ubukwebwiza.core.service.impl.CodeDistrictServiceImpl;
import com.buseni.ubukwebwiza.core.service.impl.CodeWeddingServiceManagerImpl;

@Configuration 
public class ServiceConfiguration {

	
	@Bean
	public CodeDistrictService codeDistrictService(CodeDistrictRepo codeDistrictRepo) {
		return new  CodeDistrictServiceImpl(codeDistrictRepo);
	}

	
	@Bean
	public CodeWeddingServiceManager codeWeddingServiceManager(CodeWeddingServiceRepo codeWeddingServiceRepo){
		return new CodeWeddingServiceManagerImpl(codeWeddingServiceRepo);
	}
}
