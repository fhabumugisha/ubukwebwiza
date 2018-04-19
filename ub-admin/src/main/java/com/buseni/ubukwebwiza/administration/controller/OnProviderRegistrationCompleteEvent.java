package com.buseni.ubukwebwiza.administration.controller;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class OnProviderRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final String businessName;
    private final String  urlName; 
    private final String password;   
    private final String email;
   

    public OnProviderRegistrationCompleteEvent(String businessName, String urlName, String email, String password,Locale locale, String appUrl) {
       super(businessName);
    	this.businessName = businessName;
        this.urlName = urlName;
        this.email = email;
        this.password = password;
        this.locale = locale;
        this.appUrl = appUrl;
       
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getPassword() {
		return password;
	}

	public String getBusinessName() {
		return businessName;
	}

	public String getEmail() {
		return email;
	}

	public String getUrlName() {
		return urlName;
	}

	
}