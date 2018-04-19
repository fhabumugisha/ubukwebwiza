package com.buseni.ubukwebwiza.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuditorAwareImpl implements AuditorAware<String> {
	  
    @Override
    public Optional<String> getCurrentAuditor() {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
          return null;
        }
        if(authentication.getPrincipal() instanceof UserDetails){
        	UserDetails userDetail = (UserDetails) authentication.getPrincipal();	
        	return Optional.of(userDetail.getUsername());
        }else{
        	return Optional.of("VISITOR");
        }
        
    }
 
}