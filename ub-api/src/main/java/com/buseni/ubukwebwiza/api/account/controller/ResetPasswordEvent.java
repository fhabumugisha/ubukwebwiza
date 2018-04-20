package com.buseni.ubukwebwiza.api.account.controller;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.buseni.ubukwebwiza.provider.domain.Provider;

@SuppressWarnings("serial")
public class ResetPasswordEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final Provider provider;
    private String token;
    public ResetPasswordEvent(Provider provider, Locale locale, String appUrl, String token) {
        super(provider);
        this.provider = provider;
        this.locale = locale;
        this.appUrl = appUrl;
        this.token =  token;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public Provider getProvider() {
        return provider;
    }
    
    public String getToken(){
    	return token;
    }
}