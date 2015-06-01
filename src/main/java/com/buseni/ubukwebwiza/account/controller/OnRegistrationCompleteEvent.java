package com.buseni.ubukwebwiza.account.controller;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.buseni.ubukwebwiza.provider.domain.Provider;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final Provider user;

    public OnRegistrationCompleteEvent(Provider user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public Provider getUser() {
        return user;
    }
}