package com.buseni.ubukwebwiza.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuditorAwareImpl implements AuditorAware<String> {
	  
    @Override
    public String getCurrentAuditor() {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
          return null;
        }
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();	
		return  userDetail.getUsername();
    }
 
}