package com.buseni.ubukwebwiza.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.buseni.ubukwebwiza.home.HomeController;

@Configuration 
public class ControllerConfiguration {

	
	@Bean
	public HomeController homeController() {
		return new HomeController();
	}

}
