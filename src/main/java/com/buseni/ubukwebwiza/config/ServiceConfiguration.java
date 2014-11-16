package com.buseni.ubukwebwiza.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.buseni.ubukwebwiza.vendor.repository.CodeDistrictRepo;
import com.buseni.ubukwebwiza.vendor.repository.CodeTypeWeddingServiceRepo;
import com.buseni.ubukwebwiza.vendor.service.CodeDistrictService;
import com.buseni.ubukwebwiza.vendor.service.CodeWeddingServiceManager;
import com.buseni.ubukwebwiza.vendor.service.impl.CodeDistrictServiceImpl;
import com.buseni.ubukwebwiza.vendor.service.impl.CodeTypeWeddingServiceManagerImpl;

@Configuration 
public class ServiceConfiguration {

	
	@Bean
	public CodeDistrictService codeDistrictService(CodeDistrictRepo codeDistrictRepo) {
		return new  CodeDistrictServiceImpl(codeDistrictRepo);
	}

	
	@Bean
	public CodeWeddingServiceManager codeWeddingServiceManager(CodeTypeWeddingServiceRepo codeTypeWeddingServiceRepo){
		return new CodeTypeWeddingServiceManagerImpl(codeTypeWeddingServiceRepo);
	}
}
