package com.buseni.ubukwebwiza.config;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

import com.buseni.ubukwebwiza.filters.AjaxSessionExpirationFilter;

public class SpringSecurityInitializer
//extends AbstractSecurityWebApplicationInitializer 
{
   //do nothing
	
	//@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
	//	insertFilters(servletContext, new AjaxSessionExpirationFilter(), new MultipartFilter());
	}
}